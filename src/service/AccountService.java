package service;

import domain.Account;
import exception.DepositException;
import exception.TransferException;
import exception.WithdrawException;
import repository.AccountRepository;

import java.math.BigDecimal;
import java.util.HashMap;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public Account findAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    public HashMap<Integer, Account> findAllAccounts() {
        return accountRepository.findAll();
    }


}
