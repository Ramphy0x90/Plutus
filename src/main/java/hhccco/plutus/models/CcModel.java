package hhccco.plutus.models;

public class CcModel {
    private final String cc;
    private final String description;

    public CcModel(String cc, String description) {
        this.cc = cc;
        this.description = description;
    }

    public String getCc() {
        return cc;
    }

    public String getDescription() {
        return description;
    }
}
