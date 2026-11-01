package service;

import domain.Account;
import exception.TransferException;
import repository.AccountRepository;

import java.math.BigDecimal;

public class TransferService {
    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(Integer originId, Integer destinationId, BigDecimal amount) {
        if (originId == null || destinationId == null)
            throw new TransferException("IDs das contas são obrigatórios.");

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new TransferException("Valor de transferência deve ser maior que zero.");

        if (originId.equals(destinationId))
            throw new TransferException("Usuário não pode transferir para a mesma conta.");

        Account origin = accountRepository.findById(originId);
        Account destination = accountRepository.findById(destinationId);

        if (!origin.canWithdraw(amount))
            throw new TransferException("Saldo insuficiente.");

        if (!destination.canDeposit(amount))
            throw new TransferException("Falha ao creditar conta de destino.");

        if (!origin.withdraw(amount))
            throw new TransferException("Saldo insuficiente.");

        if (!destination.deposit(amount))
            throw new TransferException("Falha ao creditar conta de destino.");
    }
}
