package br.com.alurafood.order.controller;

import br.com.alurafood.order.dto.OrderDto;
import br.com.alurafood.order.dto.StatusDto;
import br.com.alurafood.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrderDto> getOrders() {
        log.info("[OrderController] - Getting orders");
        return service.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable @NotNull final Long id) {
        log.info("[OrderController] - Getting order by id: {}", id);
        final var dto = service.getOrderById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid final OrderDto orderDto,
                                                final UriComponentsBuilder uriBuilder) {
        log.info("[OrderController] - Creating new order: {}", orderDto);
        final var order = service.createOrder(orderDto);
        final var uri = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(order);

    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable final Long orderId, @RequestBody final StatusDto status) {
        log.info("[OrderController] - Updating order: {}, with status {}", orderId, status);
        final var dto = service.updateStatus(orderId, status);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{orderId}/payment")
    public ResponseEntity<Void> approvePayment(@PathVariable @NotNull final Long orderId) {
        log.info("[OrderController] - Approving payment with id {}", orderId);
        service.approvePayment(orderId);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/port")
    public String getInstancePort(@Value("${local.server.port}") final String port) {
        log.info("[OrderController] - Getting instance port");
        return String.format("Request answered by Instance on port %s", port);
    }
}
