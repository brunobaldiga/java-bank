package domain;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    public SavingsAccount(String fullName, String password) {
        super(fullName, password);
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            return false;

        addBalance(amount);

        return true;
    }

    @Override
    public boolean canDeposit(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            return false;

        BigDecimal balance = getBalance();

        if (amount.compareTo(balance) > 0)
            return false;

        subtractBalance(amount);

        return true;
    }

    @Override
    public boolean canWithdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            return false;

        BigDecimal balance = getBalance();

        if (amount.compareTo(balance) > 0)
            return false;

        return true;
    }

    public void applyInterest() {
        BigDecimal interestRate = new BigDecimal("0.005");
        addBalance(getBalance().multiply(interestRate));
    }
}
