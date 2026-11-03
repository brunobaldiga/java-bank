package repository;

import domain.Account;

import java.math.BigDecimal;
import java.util.Map;


public interface AccountRepository {
    Account findById(Integer id);
    void save(Account account);
    Map<Integer, Account> findAll();
    void deposit(Integer id, BigDecimal amount);
    void withdraw(Integer id, BigDecimal amount);
    void applyInterest(Integer id);
    void transfer(Integer originId, Integer destinationId, BigDecimal amount);
}
