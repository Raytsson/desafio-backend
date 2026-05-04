package desafio.backend.infrastructure.persistence.mapper;

import desafio.backend.domain.transfer.Transfer;
import desafio.backend.infrastructure.persistence.entities.TransferEntity;

public class TransferMapper {
    public  static TransferEntity toEntity(Transfer transfer){
        return TransferEntity.builder()
                .id(transfer.getId())
                .originUser(UserMapper.toEntity(transfer.getOriginUser()))
                .destinationUser(UserMapper.toEntity(transfer.getDestinationUser()))
                .value(transfer.getValue())
                .status(transfer.getStatus())
                .transferTime(transfer.getTransferTime())
                .build();
    }

    public static Transfer toDomain(TransferEntity entity){
        return Transfer.builder()
                .id(entity.getId())
                .originUser(UserMapper.toDomain(entity.getOriginUser()))
                .destinationUser(UserMapper.toDomain(entity.getDestinationUser()))
                .value(entity.getValue())
                .status(entity.getStatus())
                .transferTime(entity.getTransferTime())
                .build();
    }
}
