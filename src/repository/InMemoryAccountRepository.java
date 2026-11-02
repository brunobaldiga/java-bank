package repository;

import domain.Account;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAccountRepository implements AccountRepository {
    private Map<Integer, Account> accounts = new HashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Account findById(Integer id) {
        return Optional.ofNullable(accounts.get(id))
                .orElseThrow(
                        () -> new NoSuchElementException("Conta não encontrada.")
                );
    }

    @Override
    public void save(Account account) {
        Objects.requireNonNull(account);
        if (account.getId() == null) account.setId(nextId.getAndIncrement());
        accounts.put(account.getId(), account);
    }

    @Override
    public Map<Integer, Account> findAll() {
        return Map.copyOf(accounts);
    }
}
