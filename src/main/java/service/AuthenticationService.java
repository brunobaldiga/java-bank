package service;

import domain.Account;
import exception.AuthenticationException;
import repository.AccountRepository;

public class AuthenticationService {
    private final AccountRepository accountRepository;

    public AuthenticationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account authenticate(Integer id, String password) {
        Account account = accountRepository.findById(id);

        if (account == null)
            throw new AuthenticationException("Invalid account ID");

        if (!account.checkPassword(password))
            throw new AuthenticationException("ID ou senha inválidos.");

        return account;
    }
}
