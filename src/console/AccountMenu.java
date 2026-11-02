package console;

import domain.Account;
import exception.DepositException;
import exception.WithdrawException;
import service.TransactionService;

import java.math.BigDecimal;
import java.util.Scanner;

public abstract class AccountMenu {
    protected final Scanner scanner;
    protected final TransactionService transactionService;

    public AccountMenu(Scanner scanner, TransactionService transactionService) {
        this.scanner = scanner;
        this.transactionService = transactionService;
    }

    public abstract Integer show();
    public abstract void run(Account account);

    public void deposit(Account account) {
        System.out.print("Digite o valor: ");

        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            transactionService.deposit(account.getId(), amount);

            System.out.println("Depósito realizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (DepositException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdraw(Account account) {
        System.out.print("Digite o valor: ");

        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            transactionService.withdraw(account.getId(), amount);

            System.out.println("Saque realizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (WithdrawException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showInformation(Account account) {
        System.out.println("Informações da conta:");
        System.out.println(account);
    }
}
