package io.camunda.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.camunda.connector.model.NvidiaAIRequest;
import io.camunda.connector.model.NvidiaAIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OutboundConnector(
        name = "Nvidia AI Connector",
        inputVariables = {"apiKey", "model", "prompt", "url"},
        type = "io.camunda:nvidia-ai-connector:1"
)
@ElementTemplate(
        id = "io.camunda.connector.nvidia.ai.template.v1",
        name = "Nvidia AI Connector",
        version = 1,
        description = "Nvidia AI Connector",
        inputDataClass = NvidiaAIRequest.class
)
public class NvidiaAIOutboundConnectorFunction implements OutboundConnectorFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(NvidiaAIOutboundConnectorFunction.class);

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    private static final String SUCCESS = "success";
    private static final String STATUS = "status";


    @Override
    public Object execute(OutboundConnectorContext context) throws Exception {

        NvidiaAIRequest request = context.bindVariables(NvidiaAIRequest.class);
        Map<String, String> headers = context.getJobContext().getCustomHeaders();
        String connectionTimeoutInSeconds = headers.get("connectionTimeoutInSeconds");
        LOGGER.info("Received request: {}", request);

        return callNvidiaAPI(request,connectionTimeoutInSeconds);
    }

    private Object callNvidiaAPI(NvidiaAIRequest req,String connectionTimeoutInSeconds) {

        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", req.model());

            payload.put("messages", List.of(
                    Map.of("role", "user", "content", req.prompt())
            ));

            String requestBody = objectMapper.writeValueAsString(payload);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(req.url()))
                    .timeout(Duration.ofSeconds(Integer.parseInt(connectionTimeoutInSeconds)))
                    .header("Authorization", "Bearer " + req.apiKey())
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            LOGGER.info("Response Status: {}", response.statusCode());
            LOGGER.debug("Response Body: {}", response.body());

            if (response.statusCode() == 403 || response.statusCode() == 401) {
                return Map.of(
                        STATUS, "error",
                        "message", response.body()
                );
            }
            var rootNode = objectMapper.readTree(response.body());


            List<NvidiaAIResponse.Choice> choices = List.of(
                    new NvidiaAIResponse.Choice(
                            new NvidiaAIResponse.Message(
                                    rootNode.path("choices")
                                            .get(0)
                                            .path("message")
                                            .path("content")
                                            .asText()
                            )
                    )
            );

            var usageNode = rootNode.path("usage");

            NvidiaAIResponse.Usage usage = new NvidiaAIResponse.Usage(
                    usageNode.path("prompt_tokens").asInt(),
                    usageNode.path("completion_tokens").asInt(),
                    usageNode.path("total_tokens").asInt(),
                    usageNode.path("prompt_tokens_details").asText()
            );

            return new NvidiaAIResponse(SUCCESS, choices, usage);

        } catch (Exception e) {
            LOGGER.error("Error calling Nvidia API", e);

            return Map.of(
                    STATUS, "error",
                    "message", e.getMessage()
            );
        }
    }
}

