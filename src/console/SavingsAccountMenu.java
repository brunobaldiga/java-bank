package console;

import domain.Account;
import service.TransactionService;

import java.util.Scanner;

public class SavingsAccountMenu extends AccountMenu {

    public SavingsAccountMenu(Scanner scanner, TransactionService transactionService) {
        super(scanner, transactionService);
    }

    public Integer menu() {
        System.out.println("Selecione uma opção:");
        System.out.println("1. Depositar");
        System.out.println("2. Sacar");
        System.out.println("3. Investir");
        System.out.println("4. Sair");
        System.out.print("> ");

        return Integer.parseInt(scanner.nextLine());
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
