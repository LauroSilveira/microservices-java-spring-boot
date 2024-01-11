package br.com.alurafood.mspayments.controller;

import br.com.alurafood.mspayments.dto.PaymentDto;
import br.com.alurafood.mspayments.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.net.URI;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("payments")
public class PyamentController {

  @Autowired
  private PaymentService service;

  @GetMapping
  public Page<PaymentDto> getAllPayments(@PageableDefault(size = 10) final Pageable pageable) {
    return service.getAllPayments(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentDto> getPaymentById(@PathVariable @NotNull final Long id) {
    final PaymentDto paymentDto = service.getById(id);
    return Optional.ofNullable(paymentDto).map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<PaymentDto> save(@RequestBody @Valid final PaymentDto dto,
      final UriComponentsBuilder uriComponentsBuilder) {
    final URI uri = uriComponentsBuilder.path("/payment/{id}").buildAndExpand(dto.getPaymentId())
        .toUri();
    final PaymentDto paymentDto = service.save(dto);
    return Optional.ofNullable(paymentDto).map(p -> ResponseEntity.created(uri)
            .body(p))
        .orElse(ResponseEntity.internalServerError().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<PaymentDto> update(@PathVariable @NotNull final Long id,
      @RequestBody @Valid final PaymentDto dto) {
    final PaymentDto paymentDto = service.update(id, dto);
    return Optional.ofNullable(paymentDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.internalServerError().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull final Long id) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/port")
  public String getInstancePort(@Value("${local.server.port}") final String port) {
    return String.format("Request answered by Instance on port %s", port);
  }

  @PatchMapping("/{id}/confirm")
  @CircuitBreaker(name = "updateOrder", fallbackMethod = "updateOrderFallBack")
  public void confirmPayment(@PathVariable @NotNull final Long id) {
    service.confirmPayment(id);
  }

  public void updateOrderFallBack(final Long id, Exception e) {
      service.updateStatus(id);
  }

}
