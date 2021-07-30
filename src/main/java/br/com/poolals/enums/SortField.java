package br.com.poolals.enums;

public enum SortField {

    ID("id"),
    FIRSTNAME("firstName"),
    LASTNAME("lastName"),
    EMAIL("email");

    private final String value;

    SortField(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
