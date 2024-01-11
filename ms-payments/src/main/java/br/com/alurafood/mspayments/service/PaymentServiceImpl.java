package br.com.alurafood.mspayments.service;

import br.com.alurafood.mspayments.dto.PaymentDto;
import br.com.alurafood.mspayments.feignclient.OrderFeignClient;
import br.com.alurafood.mspayments.mapper.PaymentMapper;
import br.com.alurafood.mspayments.model.Payment;
import br.com.alurafood.mspayments.model.Status;
import br.com.alurafood.mspayments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final OrderFeignClient orderFeignClient;

    public PaymentService(PaymentRepository repository, PaymentMapper mapper, OrderFeignClient orderFeignClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderFeignClient = orderFeignClient;
    }


    public PaymentDto save(final PaymentDto dto) {
        final var payment = mapper.mapToEntity(dto);
        payment.setStatus(Status.CREATED);
        repository.save(payment);
        return mapper.mapToDto(payment);
    }

    public PaymentDto update(@NotNull final Long id, final PaymentDto dto) {
        final var paymentUpdated = mapper.mapToEntity(dto);
        paymentUpdated.setId(id);
        repository.save(paymentUpdated);
        return mapper.mapToDto(paymentUpdated);
    }

    public void delete(final Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }


    public Page<PaymentDto> getAllPayments(final Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::mapToDto);
    }

    public PaymentDto getById(final Long id) {
        final var model = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        final var dto = mapper.mapToDto(model);
        final var orderItems = orderFeignClient.getOrderItems(model.getOrderId()).getItems();
        return new PaymentDto(dto.id(), dto.value(), dto.name(), dto.number(), dto.expired(), dto.code(), dto.status(),
                dto.orderId(), dto.paymentId(), orderItems);
    }

    public void confirmPayment(final Long id) {
        final var paymentUpdated = repository.findById(id);

        if (paymentUpdated.isEmpty()) {
            throw new EntityNotFoundException();
        }

        paymentUpdated.get()
                .setStatus(Status.CONFIRMED);
        repository.save(paymentUpdated.get());

        // Calls FeignClient to MS-ORDER
        orderFeignClient.updatePayment(paymentUpdated.get().getPaymentId());
    }

    //Fallback method
    public void updateStatus(Long id) {
        final Optional<Payment> statusUpdated = repository.findById(id);

        if (statusUpdated.isEmpty()) {
            throw new EntityNotFoundException();
        }

        statusUpdated.get()
                .setStatus(Status.ORDER_NOT_CONFIRMED);
        repository.save(statusUpdated.get());
    }
}
