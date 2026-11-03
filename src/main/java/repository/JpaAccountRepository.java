package repository;

import domain.Account;
import domain.SavingsAccount;
import exception.DepositException;
import exception.TransferException;
import exception.WithdrawException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class JpaAccountRepository implements AccountRepository {
    private final EntityManagerFactory factory;

    public JpaAccountRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Account findById(Integer id) {
        Objects.requireNonNull(id);

        try (EntityManager em = factory.createEntityManager()) {
            return em.find(Account.class, id);
        }
    }

    @Override
    public void save(Account account) {
        Objects.requireNonNull(account);

        executeInTransaction(em -> {
            if (account.getId() == null) {
                em.persist(account);
            } else {
                em.merge(account);
            }
        });
    }

    @Override
    public Map<Integer, Account> findAll() {
        try (EntityManager em = factory.createEntityManager()) {
            var accounts = em.createQuery(
                    "select acc from Account acc order by acc.id",
                    Account.class
            ).getResultList();

            Map<Integer, Account> result = new LinkedHashMap<>();

            for (Account account : accounts)
                result.put(account.getId(), account);

            return result;
        }
    }

    @Override
    public void deposit(Integer id, BigDecimal amount) {
        validateAmount(amount);

        executeInTransaction(em -> {
            Account account = findLocked(em, id);

            if(!account.deposit(amount))
                throw new DepositException("Depósito inválido");
        });
    }

    @Override
    public void withdraw(Integer id, BigDecimal amount) {
        validateAmount(amount);

        executeInTransaction(em -> {
            Account account = findLocked(em, id);

            if (!account.withdraw(amount))
                throw new WithdrawException("Saque inválido.");
        });
    }

    @Override
    public void applyInterest(Integer id) {
        executeInTransaction(em -> {
            Account account = findLocked(em, id);

            if (!(account instanceof SavingsAccount savings))
                throw new IllegalStateException(
                        "Apenas contas poupança recebem rendimentos."
                );

            savings.applyInterest();
        });
    }

    @Override
    public void transfer(Integer originId, Integer destinationId, BigDecimal amount) {
        if (originId == null || destinationId == null)
            throw new TransferException("IDs obrigatórios.");

        if (originId.equals(destinationId))
            throw new TransferException(
                    "Não é possível transferir para a mesma conta."
            );

        if (amount == null || amount.signum() <= 0)
            throw new TransferException("Valor inválido");

        executeInTransaction(em -> {
            Account first = findLocked(em, Math.min(originId, destinationId));
            Account second = findLocked(em, Math.max(originId, destinationId));
            Account origin = first.getId().equals(originId) ? first : second;
            Account destination = first.getId().equals(destinationId) ? first : second;

            if (!origin.canWithdraw(amount))
                throw new TransferException("Saldo insuficiente.");

            if (!destination.canDeposit(amount))
                throw new TransferException(
                        "Destino não aceita depósito."
                );

            if (!origin.withdraw(amount))
                throw new TransferException("Falha no saque.");

            if (!destination.deposit(amount))
                throw new TransferException("Falha no depósito.");
        });
    }

    private Account findLocked(EntityManager em, Integer id) {
        Objects.requireNonNull(id);

        Account account = em.find(Account.class, id, LockModeType.PESSIMISTIC_WRITE);

        if (account == null)
            throw new NoSuchElementException(
                    "Conta não encontrada: " + id
            );

        return account;
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException(
                    "O valor deve ser maior que zero."
            );
    }

    private void executeInTransaction(Consumer<EntityManager> action) {
        try (EntityManager em = factory.createEntityManager()) {

            EntityTransaction transaction = em.getTransaction();

            try {
                transaction.begin();

                action.accept(em);

                transaction.commit();

            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    try {
                        transaction.rollback();
                    } catch (RuntimeException rollbackError) {
                        e.addSuppressed(rollbackError);
                    }
                }

                throw e;
            }
        }
    }
}