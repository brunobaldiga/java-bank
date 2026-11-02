package application;

import console.AccountMenu;
import console.CheckingAccountMenu;
import console.ConsoleMenu;
import console.SavingsAccountMenu;
import domain.Account;
import domain.AccountType;
import repository.AccountRepository;
import repository.InMemoryAccountRepository;
import service.AccountService;
import service.AuthenticationService;
import service.TransactionService;
import service.TransferService;

import java.util.Map;
import java.util.Scanner;

public class BankApplication {
    private final ConsoleMenu mainMenu;
    private final Map<AccountType, AccountMenu> accountMenus;

    public BankApplication() {
        Scanner scanner = new Scanner(System.in);
        AccountRepository accountRepository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        AuthenticationService authenticationService = new AuthenticationService(accountRepository);
        TransactionService transactionService = new TransactionService(accountRepository);
        TransferService transferService = new TransferService(accountRepository);

        this.mainMenu = new ConsoleMenu(scanner, accountService, authenticationService);
        this.accountMenus = Map.of(
                AccountType.CHECKING_ACCOUNT, new CheckingAccountMenu(scanner, transactionService, transferService),
                AccountType.SAVINGS_ACCOUNT, new SavingsAccountMenu(scanner, transactionService)
        );
    }

    public void run() {
        boolean running = true;

        while (running) {
            Integer option = mainMenu.show();

            switch (option) {
                case 1 -> mainMenu.createAccount();
                case 2 -> login();
                case 3 -> {
                    running = false;
                    System.out.println("Saindo...");
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void login() {
        Account account = mainMenu.login();

        if (account == null)
            return;

        AccountMenu menu = accountMenus.get(account.getAccountType());

        if (menu == null)
            throw new IllegalStateException("Menu não encontrado.");

        menu.run(account);
    }
}
