package br.com.alurafood.mspayments.service;

import br.com.alurafood.mspayments.dto.PaymentDto;
import br.com.alurafood.mspayments.feignclient.OrderFeignClient;
import br.com.alurafood.mspayments.mapper.PaymentMapper;
import br.com.alurafood.mspayments.model.Payment;
import br.com.alurafood.mspayments.model.Status;
import br.com.alurafood.mspayments.repository.PaymentRepository;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  @Autowired
  private PaymentRepository repository;

  @Autowired
  private PaymentMapper mapper;

  @Autowired
  private OrderFeignClient orderFeignClient;


  public PaymentDto save(final PaymentDto dto) {

    final Payment payment = mapper.mapToEntity(dto);
    payment.setStatus(Status.CREATED);
    repository.save(payment);
    return mapper.mapToDto(payment);
  }

  public PaymentDto update(@NotNull final Long id, final PaymentDto dto) {
    final Payment paymentToUpadate = mapper.mapToEntity(dto);
    paymentToUpadate.setId(id);
    repository.save(paymentToUpadate);
    return mapper.mapToDto(paymentToUpadate);
  }

  public void delete(final Long id) {
    repository.findById(id)
        .ifPresent(payment -> repository.delete(payment));
  }


  public Page<PaymentDto> getAllPayments(final Pageable pageable) {
    return repository.findAll(pageable)
        .map(entity -> mapper.mapToDto(entity));
  }

  public PaymentDto getById(final Long id) {
    final Payment model = repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
    final PaymentDto dto = mapper.mapToDto(model);

    dto.setOrderitems(orderFeignClient.getOrderItems(model.getOrderId()).getItems());
    return dto;
  }


  public void confirmPayment(final Long id) {
    final Optional<Payment> paymentToUpadte = repository.findById(id);

    if(paymentToUpadte.isEmpty()) {
      throw new EntityNotFoundException();
    }

    paymentToUpadte.get()
        .setStatus(Status.CONFIRMED);
    repository.save(paymentToUpadte.get());

    // Calls FeignClient to MS-ORDER
    orderFeignClient.updatePayment(paymentToUpadte.get().getPaymentId());
  }

  //Fallback method
  public void updateStatus(Long id) {
    final Optional<Payment> paymentToUpadte = repository.findById(id);

    if(paymentToUpadte.isEmpty()) {
      throw new EntityNotFoundException();
    }

    paymentToUpadte.get()
        .setStatus(Status.ORDER_NOT_CONFIRMED);
    repository.save(paymentToUpadte.get());
  }
}
