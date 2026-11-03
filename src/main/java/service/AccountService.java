package service;

import domain.Account;
import repository.AccountRepository;

import java.util.Map;

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

    public Map<Integer, Account> findAllAccounts() {
        return accountRepository.findAll();
    }
}
