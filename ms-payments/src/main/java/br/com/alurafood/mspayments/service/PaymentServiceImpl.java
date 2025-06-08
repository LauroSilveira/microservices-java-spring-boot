package br.com.alurafood.mspayments.service;

import br.com.alurafood.mspayments.dto.PaymentDto;
import br.com.alurafood.mspayments.feignclient.OrderFeignClient;
import br.com.alurafood.mspayments.mapper.PaymentMapper;
import br.com.alurafood.mspayments.model.Payment;
import br.com.alurafood.mspayments.model.Status;
import br.com.alurafood.mspayments.repository.PaymentRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final OrderFeignClient orderFeignClient;
    private final PaymentMapper paymentMapper;


    @Override
    public PaymentDto save(final PaymentDto dto) {
        final var payment = mapper.mapToEntity(dto);
        payment.setStatus(Status.CREATED);
        repository.save(payment);
        return mapper.mapToDto(payment);
    }


    @Override
    public PaymentDto update(@NotNull final Long id, final PaymentDto dto) {
        final var paymentUpdated = mapper.mapToEntity(dto);
        paymentUpdated.setId(id);
        repository.save(paymentUpdated);
        return mapper.mapToDto(paymentUpdated);
    }


    @Override
    public void delete(final Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }


    @Override
    public Page<Payment> getAllPayments(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @CircuitBreaker(name = "paymentInfo", fallbackMethod = "getPaymentInfoFallBack")
    @Override
    public PaymentDto getById(final Long id) {
        final var payment = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        final var orderItems = orderFeignClient.getOrderItems(payment.getOrderId()).getItems();
        return new PaymentDto(payment, orderItems);
    }

    //FallBack getById
    public PaymentDto getPaymentInfoFallBack(final Long id, FeignException e) {
        return this.repository.findById(id)
                .map(paymentMapper::mapToDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @CircuitBreaker(name = "updateOrder", fallbackMethod = "updateOrderFallBack")
    @Override
    public void confirmPayment(final Long id) {
        final var paymentUpdated = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        paymentUpdated.setStatus(Status.CONFIRMED);
        repository.save(paymentUpdated);

        // Calls FeignClient to MS-ORDER
        orderFeignClient.updatePayment(paymentUpdated.getPaymentId());
    }

    public void updateOrderFallBack(final Long id, FeignException e) {
        this.updateStatus(id);
    }

    //Fallback method
    @Override
    public void updateStatus(Long id) {
        final var statusUpdated = repository.findById(id);


        if (statusUpdated.isEmpty()) {
            throw new EntityNotFoundException();
        }

        statusUpdated.get()
                .setStatus(Status.ORDER_NOT_CONFIRMED);
        repository.save(statusUpdated.get());
    }
}
