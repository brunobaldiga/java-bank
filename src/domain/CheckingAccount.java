package domain;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    private BigDecimal creditLimit;
    private BigDecimal creditUsage;

    public CheckingAccount(Integer id, String fullName, String password) {
        super(id, fullName, password);
        this.creditLimit = BigDecimal.valueOf(150);
        this.creditUsage = BigDecimal.ZERO;
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        BigDecimal currentCreditUsage = getCreditUsage();

        if (amount.compareTo(currentCreditUsage) >= 0) {
            BigDecimal depositLeft = amount.subtract(currentCreditUsage);
            subtractCreditUsage(currentCreditUsage);
            addBalance(depositLeft);

        } else {
            subtractCreditUsage(amount);
        }

        return true;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        BigDecimal balance = getBalance();
        BigDecimal availableFunds = balance.add(getCreditLimit().subtract(getCreditUsage()));

        if (amount.compareTo(availableFunds) > 0) {
            return false;
        }

        if (amount.compareTo(balance) <= 0) {
            subtractBalance(amount);
        } else {
            BigDecimal newCreditUsage = amount.subtract(balance);

            subtractBalance(balance);
            addCreditUsage(newCreditUsage);
        }

        return true;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public BigDecimal getCreditUsage() {
        return creditUsage;
    }

    private void addCreditUsage(BigDecimal amount) {
        this.creditUsage = this.creditUsage.add(amount);
    }

    private void subtractCreditUsage(BigDecimal amount) {
        this.creditUsage = creditUsage.subtract(amount);
    }

}
