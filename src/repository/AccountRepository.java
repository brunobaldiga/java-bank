package repository;

import domain.Account;

import java.util.Map;


public interface AccountRepository {
    Account findById(Integer id);

    void save(Account account);

    Map<Integer, Account> findAll();
}
