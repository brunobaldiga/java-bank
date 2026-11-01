package repository;

import domain.Account;

import java.util.HashMap;


public interface AccountRepository {
    Account findById(Integer id);

    void save(Account account);

    HashMap<Integer, Account> findAll();
}
