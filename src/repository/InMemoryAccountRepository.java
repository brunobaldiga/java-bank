package repository;

import domain.Account;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    private HashMap<Integer, Account> accounts = new HashMap<>();

    @Override
    public Account findById(Integer id) {
        return Optional.ofNullable(accounts.get(id))
                .orElseThrow(
                        () -> new NoSuchElementException("Conta não encontrada.")
                );
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getId(), account);
    }

    @Override
    public HashMap<Integer, Account> findAll() {
        return accounts;
    }
}
