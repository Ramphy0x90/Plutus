package hhccco.plutus.models;

import java.time.LocalDate;

public class TableDataModel {
    private final String date;
    private final String movimento;
    private final String cc;
    private final Double versamento;
    private final Double prelevamento;
    private final String bankId;

    public TableDataModel(LocalDate movementDate, String movimento, String cc, Double versamento, Double prelevamento, String bankId){
        this.date = String.valueOf(movementDate);
        this.movimento = movimento;
        this.cc = cc;
        this.versamento = versamento;
        this.prelevamento = prelevamento;
        this.bankId = bankId;
    }

    public String getDate() {
        return date;
    }

    public String getMovimento() {
        return movimento;
    }

    public String getCc() {
        return cc;
    }

    public double getVersamento() {
        return versamento;
    }

    public double getPrelevamento() {
        return prelevamento;
    }

    public String getBankId() { return bankId ;}
}
