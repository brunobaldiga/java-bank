package domain;

import jakarta.persistence.*;
import security.PasswordHasher;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "account_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    protected Account() {
    }

    protected Account(String fullName, String password) {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(password);

        if (fullName.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Falha ao criar conta: valores inválidos.");
        }

        this.fullName = fullName;
        this.password = PasswordHasher.hash(password);
        this.balance = BigDecimal.ZERO;
    }

    public abstract boolean deposit(BigDecimal amount);
    public abstract boolean canDeposit(BigDecimal amount);
    public abstract boolean withdraw(BigDecimal amount);
    public abstract boolean canWithdraw(BigDecimal amount);

    public boolean checkPassword(String password) {
        return PasswordHasher.verify(password, this.password);
    }

    protected void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    protected void subtractBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id).append("\n");
        sb.append("Nome Completo: ").append(this.fullName).append("\n");
        sb.append("Saldo: R$").append(this.balance).append("\n");

        return sb.toString();
    }

    public abstract AccountType getAccountType();

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return password;
    }
}
