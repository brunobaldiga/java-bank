package domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("SAVINGS_ACCOUNT")
public class SavingsAccount extends Account {
    public SavingsAccount() {
        super();
    }

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
        BigDecimal interest = getBalance()
                .multiply(interestRate)
                        .setScale(2, RoundingMode.HALF_EVEN);

        addBalance(interest);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS_ACCOUNT;
    }
}
