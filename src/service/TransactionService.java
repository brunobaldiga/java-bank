package service;

import domain.Account;
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


        Account account = accountRepository.findById(id);

        if(!account.deposit(amount))
            throw new DepositException("Depósito inválido");
    }

    public void withdraw(Integer id, BigDecimal amount) {
        if (id == null || amount == null)
            throw new WithdrawException("ID ou valor não podem ser nulos.");

        Account account = accountRepository.findById(id);

        if (!account.withdraw(amount))
            throw new WithdrawException("Saque inválido.");
    }
}
