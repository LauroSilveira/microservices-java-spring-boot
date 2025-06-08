package br.com.alurafood.mspayments.controller;

import br.com.alurafood.mspayments.dto.PaymentDto;
import br.com.alurafood.mspayments.mapper.PaymentMapper;
import br.com.alurafood.mspayments.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    private final PaymentMapper paymentMapper;

    @GetMapping
    public ResponseEntity<PagedModel<PaymentDto>> getAllPayments(@PageableDefault final Pageable pageable) {
        final var payments = service.getAllPayments(pageable);
        return ResponseEntity.ok(new PagedModel<>(payments.map(paymentMapper::mapToDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable @NotNull final Long id) {
        final var paymentDto = service.getById(id);
        return Optional.ofNullable(paymentDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PaymentDto> save(@RequestBody @Valid final PaymentDto dto, final UriComponentsBuilder uriComponentsBuilder) {
        final var uri = uriComponentsBuilder.path("/payment/{id}").buildAndExpand(dto.paymentId())
                .toUri();
        final var paymentDto = service.save(dto);
        return ResponseEntity.created(uri).body(paymentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull final Long id,
                                             @RequestBody @Valid final PaymentDto dto) {
        final var paymentDto = service.update(id, dto);
        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull final Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Return the port of the instance, because we can have more than one instance of this microservice
     *
     * @param port of this instance
     * @return the port of this Instance
     */
    @GetMapping("/port")
    public String getInstancePort(@Value("${local.server.port}") final String port) {
        return String.format("Request answered by Instance on port %s", port);
    }

    @PatchMapping("/{id}/confirm")
    public void confirmPayment(@PathVariable @NotNull final Long id) {
        service.confirmPayment(id);
    }

}
