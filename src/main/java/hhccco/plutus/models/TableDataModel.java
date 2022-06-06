package hhccco.plutus.models;

public class TableDataModel {
    private final String movimento;
    private final String cc;
    private final String versamento;
    private final String prelevamento;

    public TableDataModel(String movimento, String cc, String versamento, String prelevamento){
        this.movimento = movimento;
        this.cc = cc;
        this.versamento = versamento;
        this.prelevamento = prelevamento;
    }

    public String getMovimento() {
        return movimento;
    }

    public String getCc() {
        return cc;
    }

    public String getVersamento() {
        return versamento;
    }

    public String getPrelevamento() {
        return prelevamento;
    }
}
