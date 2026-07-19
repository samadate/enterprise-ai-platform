package com.sam.enterpriseai.constants;

public final class LogMessages {

    private LogMessages() {
    }

    public static final String AI_REQUEST_RECEIVED =
            "AI generation request received.";

    public static final String AI_REQUEST_COMPLETED =
            "AI generation completed in {} ms.";

    public static final String AI_REQUEST_FAILED =
            "AI generation failed after {} ms.";

    public static final String PROVIDER_SELECTED =
            "Using provider '{}' with model '{}'.";

    public static final String PROVIDER_SUCCESS =
            "Provider '{}' completed successfully.";

    public static final String PROVIDER_EMPTY_RESPONSE =
            "Provider '{}' empty response.";

    public static final String PROVIDER_FAILURE =
            "Provider '{}' communication failed.";

}