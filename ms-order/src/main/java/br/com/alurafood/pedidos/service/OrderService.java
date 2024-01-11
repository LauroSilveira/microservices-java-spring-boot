
package br.com.alurafood.pedidos.service;

import br.com.alurafood.pedidos.dto.OrderDto;
import br.com.alurafood.pedidos.dto.StatusDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrders();

    OrderDto getOrderById(Long id);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateStatus(Long id, StatusDto dto);

    void approvePayment(Long id);

}
