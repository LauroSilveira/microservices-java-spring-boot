package br.com.alurafood.pedidos.service;

import br.com.alurafood.pedidos.dto.OrderDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.mapper.OrderMapper;
import br.com.alurafood.pedidos.model.Order;
import br.com.alurafood.pedidos.model.Status;
import br.com.alurafood.pedidos.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {


  private final OrderRepository repository;


  private final OrderMapper mapper;

  public OrderService(OrderRepository repository, OrderMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }


  public List<OrderDto> getOrders() {
    return repository.findAll().stream()
        .map(mapper::mapToDto)
        .toList();
  }

  public OrderDto getOrderById(Long id) {
    return repository.findById(id)
        .map(mapper::mapToDto)
        .orElseThrow(EntityNotFoundException::new);

  }

  public OrderDto createOrder(OrderDto orderDto) {
    final Order order = mapper.mapToEntiy(orderDto);

    order.setPurchaseDate(LocalDateTime.now());
    order.setStatusOrder(Status.ORDERED);
    order.getItems().forEach(item -> item.setOrder(order));
    repository.save(order);

    return mapper.mapToDto(order);
  }

  public OrderDto updateStatus(Long id, StatusDto dto) {

    Order order = repository.porIdComItens(id);

    if (order == null) {
      throw new EntityNotFoundException();
    }

    order.setStatusOrder(dto.getStatus());
    repository.updateStatus(dto.getStatus(), order);
    return mapper.mapToDto(order);
  }

  public void approvePayment(Long id) {

    Order order = repository.porIdComItens(id);

    if (order == null) {
      throw new EntityNotFoundException();
    }

    order.setStatusOrder(Status.PAID);
    repository.updateStatus(Status.PAID, order);
  }
}
