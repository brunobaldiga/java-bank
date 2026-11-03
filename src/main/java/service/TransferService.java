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

        accountRepository.transfer(
                originId,
                destinationId,
                amount
        );
    }
}
