package service;

import domain.Account;
import domain.SavingsAccount;
import exception.DepositException;
import exception.WithdrawException;
import repository.AccountRepository;

import java.math.BigDecimal;

public class TransactionService {
    private final AccountRepository accountRepository;

    public TransactionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void deposit(Integer id, BigDecimal amount) {
        if (id == null || amount == null)
            throw new DepositException("ID ou valor não podem ser nulos.");

        accountRepository.deposit(id, amount);
    }

    public void withdraw(Integer id, BigDecimal amount) {
        if (id == null || amount == null)
            throw new WithdrawException("ID ou valor não podem ser nulos.");

        accountRepository.withdraw(id, amount);
    }

    public void applyInterest(Integer id) {
        Account account = accountRepository.findById(id);

        if (!(account instanceof SavingsAccount savingsAccount))
            throw new IllegalArgumentException(
                    "Apenas conta poupança podem receber rendimentos."
            );

        accountRepository.applyInterest(id);
    }

    public Account findAccountById(Integer id) {
        return accountRepository.findById(id);
    }
}
