package io.camunda.connector.model;

import java.util.List;

public record NvidiaAIResponse(
        String status,
        List<Choice> choices,
        Usage usage
) {

    public record Choice(
            Message message
    ) {}

    public record Message(
            String content
    ) {}

    public record Usage(
            int promptTokens,
            int completionTokens,
            int totalTokens,
            String promptTokensDetails) {}
}

