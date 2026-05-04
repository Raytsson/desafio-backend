package desafio.backend;

import desafio.backend.adapters.exception.HasEnoughBalanceException;
import desafio.backend.adapters.exception.TransferValidationForbidenException;
import desafio.backend.adapters.exception.UserNotFoundException;
import desafio.backend.application.AuthorizerService;
import desafio.backend.application.NotificationService;
import desafio.backend.application.TransferUseCase;
import desafio.backend.domain.transfer.Transfer;
import desafio.backend.domain.transfer.TransferRepository;
import desafio.backend.domain.transfer.TransferStatus;
import desafio.backend.domain.user.User;
import desafio.backend.domain.user.UserRepository;
import desafio.backend.domain.user.UserType;
import desafio.backend.domain.wallet.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class TransferUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private AuthorizerService authorizerService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransferUseCase transferUseCase;

    @Test
    void shouldTransferSuccessfully() {
        // Arrange
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();
        BigDecimal value = new BigDecimal("100.0");

        Wallet payerWallet = Wallet.builder().id(UUID.randomUUID()).balance(new BigDecimal("500.0")).build();
        Wallet payeeWallet = Wallet.builder().id(UUID.randomUUID()).balance(new BigDecimal("100.0")).build();

        User payer = User.builder().id(payerId).type(UserType.COMMON).wallet(payerWallet).build();
        User payee = User.builder().id(payeeId).type(UserType.COMMON).wallet(payeeWallet).build();

        when(userRepository.findById(payerId)).thenReturn(payer);
        when(userRepository.findById(payeeId)).thenReturn(payee);
        when(authorizerService.authorize(payer, value)).thenReturn(true);

        Transfer savedTransfer = Transfer.builder()
                .id(UUID.randomUUID())
                .originUser(payer)
                .destinationUser(payee)
                .value(value)
                .status(TransferStatus.PENDING)
                .transferTime(LocalDateTime.now())
                .build();

        when(transferRepository.save(any())).thenReturn(savedTransfer);

        // Act
        transferUseCase.execute(payerId, payeeId, value);

        // Assert
        verify(transferRepository, times(2)).save(any());
        verify(userRepository).save(payer);
        verify(userRepository).save(payee);
    }

    @Test
    void shouldThrowWhenShopkeeperTransfers() {
        // Arrange
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();
        BigDecimal value = new BigDecimal("100.0");

        Wallet payerWallet = Wallet.builder().id(UUID.randomUUID()).balance(new BigDecimal("500.0")).build();

        User payer = User.builder().id(payerId).type(UserType.SHOPKEEPER).wallet(payerWallet).build();
        User payee = User.builder().id(payeeId).type(UserType.COMMON).wallet(Wallet.builder().build()).build();

        when(userRepository.findById(payerId)).thenReturn(payer);
        when(userRepository.findById(payeeId)).thenReturn(payee);

        // Act & Assert
        assertThrows(TransferValidationForbidenException.class, () ->
                transferUseCase.execute(payerId, payeeId, value)
        );
    }

    @Test
    void pasoudolimite() {
        // Arrange
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();
        BigDecimal value = new BigDecimal("9999.0");

        Wallet payerWallet = Wallet.builder().id(UUID.randomUUID()).balance(new BigDecimal("500.0")).build();

        User payer = User.builder().id(payerId).type(UserType.COMMON).wallet(payerWallet).build();
        User payee = User.builder().id(payeeId).type(UserType.SHOPKEEPER).wallet(Wallet.builder().build()).build();

        when(authorizerService.authorize(payer, value)).thenReturn(true);
        when(transferRepository.save(any())).thenReturn(Transfer.builder().build());
        when(userRepository.findById(payerId)).thenReturn(payer);
        when(userRepository.findById(payeeId)).thenReturn(payee);

        // Act & Assert
        assertThrows(HasEnoughBalanceException.class, () ->
                transferUseCase.execute(payerId, payeeId, value)
        );
    }

    @Test
    void autorizadornegou() {
        // Arrange
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();
        BigDecimal value = new BigDecimal("25.0");

        Wallet payerWallet = Wallet.builder().id(UUID.randomUUID()).balance(new BigDecimal("500.0")).build();

        User payer = User.builder().id(payerId).type(UserType.COMMON).wallet(payerWallet).build();
        User payee = User.builder().id(payeeId).type(UserType.SHOPKEEPER).wallet(Wallet.builder().build()).build();

        when(userRepository.findById(payerId)).thenReturn(payer);
        when(userRepository.findById(payeeId)).thenReturn(payee);
        when(authorizerService.authorize(payer, value)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                transferUseCase.execute(payerId, payeeId, value)
        );
    }


}