package console;

import domain.Account;
import exception.TransferException;
import service.TransactionService;
import service.TransferService;

import java.math.BigDecimal;
import java.util.Scanner;

public class CheckingAccountMenu extends AccountMenu {
    private final TransferService transferService;

    public CheckingAccountMenu(Scanner scanner, TransactionService transactionService, TransferService transferService) {
        super(scanner, transactionService);
        this.transferService = transferService;
    }

    public Integer menu() {
        System.out.println("Selecione uma opção:");
        System.out.println("1. Depositar");
        System.out.println("2. Sacar");
        System.out.println("3. Transferir");
        System.out.println("4. Sair");
        System.out.print("> ");

        return Integer.parseInt(scanner.nextLine());
    }

    public void transfer(Account account) {
        try {
            System.out.print("ID da conta a receber: ");
            Integer targetAccountId = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o valor: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            transferService.transfer(account.getId(),targetAccountId, amount);

            System.out.println("Transferencia realizada com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (TransferException e) {
            System.out.println(e.getMessage());;
        }
    }

}
