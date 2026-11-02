package domain;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account {
    private Integer id;
    private String fullName;
    private String password;
    private BigDecimal balance;

    protected Account(String fullName, String password) {
        this.fullName = Objects.requireNonNull(fullName);
        this.password = Objects.requireNonNull(password);

        if (fullName.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Falha ao criar conta: valores inválidos.");
        }

        this.balance = BigDecimal.ZERO;
    }

    public abstract boolean deposit(BigDecimal amount);
    public abstract boolean canDeposit(BigDecimal amount);
    public abstract boolean withdraw(BigDecimal amount);
    public abstract boolean canWithdraw(BigDecimal amount);

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    protected void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    protected void subtractBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id).append("\n");
        sb.append("Nome Completo: ").append(this.fullName).append("\n");
        sb.append("Saldo: R$").append(this.balance).append("\n");

        return sb.toString();
    }

    public abstract AccountType getAccountType();

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
