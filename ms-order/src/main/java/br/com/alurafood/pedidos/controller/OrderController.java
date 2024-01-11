package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.OrderDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderDto> getOrders() {
        return service.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable @NotNull Long id) {
        final var dto = service.getOrderById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
        final var order = service.createOrder(dto);
        final var uri = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(order);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto status) {
        final var dto = service.updateStatus(id, status);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}/payment")
    public ResponseEntity<Void> approvePayment(@PathVariable @NotNull Long id) {
        service.approvePayment(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/port")
    public String getInstancePort(@Value("${local.server.port}") final String port) {
        return String.format("Request answered by Instance on port %s", port);
    }
}
