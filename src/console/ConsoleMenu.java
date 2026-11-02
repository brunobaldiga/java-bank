package console;

import domain.Account;
import domain.AccountType;
import exception.AuthenticationException;
import service.AccountService;
import service.AuthenticationService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;
    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    public ConsoleMenu(Scanner scanner, AccountService accountService, AuthenticationService authenticationService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.authenticationService = authenticationService;
    }

    public Integer menu() {
        System.out.println("Bem-vindo ao Java Bank");
        System.out.println("\nSelecione uma opção:");
        System.out.println("1. Criar conta");
        System.out.println("2. Entrar em conta");
        System.out.println("4. Sair");
        System.out.print("> ");

        return scanner.nextInt();
    }

    public void createAccount() {
        System.out.print("Nome Completo: ");
        scanner.nextLine();
        String fullName = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        System.out.println("Tipo da conta:");
        AccountType[] accountTypes = AccountType.values();

        for (int i = 0; i < accountTypes.length; i++) {
            System.out.println((i + 1) + ". " + accountTypes[i].name());
        }

        System.out.print("> ");
        int accountOption = Integer.parseInt(scanner.nextLine());

        while (accountOption < 1 || accountOption > accountTypes.length) {
            accountOption = Integer.parseInt(scanner.nextLine());

            System.out.println("Tipo inválido, tente novamente: ");
        }

        accountService.save(
                accountTypes[accountOption - 1].create(fullName, password)
        );
    }

    public Account login() {
        System.out.print("Número da conta: ");
        Integer id = Integer.parseInt(scanner.nextLine());

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        try {
            Account account = authenticationService.authenticate(id, password);

            System.out.printf("Bem vindo %s%n", account.getFullName());

            return account;
        } catch (AuthenticationException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
