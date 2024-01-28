package org.example.scaner;

public enum ConsoleParams {
    GREEN_LIGHT("\u001B[32m"),

    WHITE_LIGHT("\u001B[0m"),

    START_LINE("[INFO] Scanning is started"),

    END_LINE("[INFO] Scanning is finished");

    private final String code;

    ConsoleParams(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getCode();
    }
}
