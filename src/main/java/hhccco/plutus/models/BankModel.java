package hhccco.plutus.models;

public class BankModel {
    private final String name;
    private final String accountNumber;
    private final String accountType;

    public BankModel(String name, String accountNumber, String accountType) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }
}
