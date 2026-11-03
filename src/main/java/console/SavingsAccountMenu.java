package console;

import domain.Account;
import service.TransactionService;

import java.util.Scanner;

public class SavingsAccountMenu extends AccountMenu {

    public SavingsAccountMenu(Scanner scanner, TransactionService transactionService) {
        super(scanner, transactionService);
    }

    public Integer show() {
        System.out.println("Selecione uma opção:");
        System.out.println("1. Depositar");
        System.out.println("2. Sacar");
        System.out.println("3. Investir");
        System.out.println("4. Mostrar informações");
        System.out.println("5. Sair");
        System.out.print("> ");

        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Escolha uma opção válida.");
                System.out.print("> ");
            }
        }
    }

    @Override
    public void run(Account account) {
        boolean running = true;

        while (running) {
            Integer option = show();

            switch (option) {
                case 1 -> deposit(account);
                case 2 -> withdraw(account);
                case 3 -> invest(account);
                case 4 -> showInformation(account);
                case 5 -> running = false;
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void invest(Account account) {
        try {
            transactionService.applyInterest(account.getId());

            System.out.println("Dinheiro aplicado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());;
        }
    }


}
