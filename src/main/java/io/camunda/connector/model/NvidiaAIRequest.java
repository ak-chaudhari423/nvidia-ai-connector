package io.camunda.connector.model;


import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;

public record NvidiaAIRequest(


        @NotEmpty
        @TemplateProperty(
                group = "authentication",
                label = "API Key",
                description = "NVIDIA API Key"
        )
        String apiKey,

        @NotEmpty
        @TemplateProperty(
                group = "request",
                label = "Model",
                description = "Model to be used for completion"
        )
        String model,

        @NotEmpty
        @TemplateProperty(
                group = "request",
                label = "Prompt",
                description = "Input prompt for the model"
        )
        String prompt,

        @NotEmpty
        @TemplateProperty(
                group = "request",
                label = "API URL",
                description = "NVIDIA API endpoint URL"
        )
        String url

) {}