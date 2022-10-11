package br.com.alurafood.mspayments.mapper;

import br.com.alurafood.mspayments.dto.PaymentDto;
import br.com.alurafood.mspayments.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  PaymentDto mapToDto(final Payment payment);

  Payment mapToEntity(final PaymentDto dto);
}
