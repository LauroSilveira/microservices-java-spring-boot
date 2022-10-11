package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.OrderDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

        @Autowired
        private OrderService service;

        @GetMapping
        public List<OrderDto> getOrders() {
            return service.getOrders();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDto> getOrderById(@PathVariable @NotNull Long id) {
            OrderDto dto = service.getOrderById(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping
        public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
            OrderDto pedidoRealizado = service.createOrder(dto);

            URI endereco = uriBuilder.path("/orders/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(pedidoRealizado);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto status){
           OrderDto dto = service.updateStatus(id, status);

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
