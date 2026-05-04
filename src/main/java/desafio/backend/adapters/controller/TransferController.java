package desafio.backend.adapters.controller;

import desafio.backend.adapters.dto.TransferRequest;
import desafio.backend.adapters.dto.TransferResponse;
import desafio.backend.application.TransferUseCase;
import desafio.backend.domain.transfer.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferUseCase transferUseCase;

    @PostMapping
    public ResponseEntity<TransferResponse> createTransfer(@RequestBody TransferRequest request) {

        Transfer transfer = transferUseCase.execute(request.payer(), request.payee(), request.value());

        TransferResponse response = new TransferResponse(
                transfer.getId(),
                transfer.getValue(),
                transfer.getStatus().name(),
                transfer.getTransferTime()
        );

        return ResponseEntity.ok(response);
    }
}
