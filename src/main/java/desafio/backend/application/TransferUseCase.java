package desafio.backend.application;

import desafio.backend.domain.transfer.Transfer;
import desafio.backend.domain.transfer.TransferRepository;
import desafio.backend.domain.transfer.TransferStatus;
import desafio.backend.domain.user.User;
import desafio.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class TransferUseCase {

    private final UserRepository userRepository;
    private final TransferRepository transferRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    public void execute(UUID payerId, UUID payeeId, BigDecimal value) {
        User payer = userRepository.findById(payerId);
        User payee = userRepository.findById(payeeId);

        payer.transferValidation();

        Transfer transfer = Transfer.builder()
                .originUser(payer)
                .destinationUser(payee)
                .value(value)
                .status(TransferStatus.PENDING)
                .transferTime(LocalDateTime.now())
                .build();
        transferRepository.save(transfer);

        boolean validation = authorizerService.authorize(payer, value);
        if (!validation) {
            transfer.setStatus(TransferStatus.FAILED);
            transferRepository.update(transfer);
            throw new RuntimeException("Transfer authorization failed");        } else {
        }
        payer.getWallet().debit(value);
        payee.getWallet().credit(value);
        transfer.setStatus(TransferStatus.COMPLETED);
        transferRepository.update(transfer);
        try {
            notificationService.notify(payer);
            notificationService.notify(payee);
        }
        catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
        userRepository.save(payee);
        userRepository.save(payer);
    }
}
