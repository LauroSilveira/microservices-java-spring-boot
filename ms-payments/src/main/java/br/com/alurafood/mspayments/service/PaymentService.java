
package br.com.alurafood.mspayments.service;

import br.com.alurafood.mspayments.dto.PaymentDto;
import br.com.alurafood.mspayments.model.Payment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;

public interface PaymentService {
    PaymentDto save(PaymentDto dto);

    PaymentDto update(@NotNull Long id, PaymentDto dto);

    void delete(Long id);

    Page<Payment> getAllPayments(Pageable pageable);

    PaymentDto getById(Long id);

    void confirmPayment(Long id);

    //Fallback method
    void updateStatus(Long id);
}
