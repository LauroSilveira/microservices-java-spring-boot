package br.com.alurafood.order.service;

import br.com.alurafood.order.dto.OrderDto;
import br.com.alurafood.order.dto.StatusDto;
import br.com.alurafood.order.mapper.OrderMapper;
import br.com.alurafood.order.model.Order;
import br.com.alurafood.order.model.Status;
import br.com.alurafood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public List<OrderDto> getOrders() {
        return repository.findAll().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(final Long id) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(EntityNotFoundException::new);

    }

    @Override
    public OrderDto createOrder(final OrderDto orderDto) {
        final Order order = mapper.mapToEntity(orderDto);
        order.setPurchaseDate(LocalDateTime.now());
        order.setStatusOrder(Status.ORDERED);
        order.getItems().forEach(item -> item.setOrder(order));
        repository.save(order);

        return mapper.mapToDto(order);
    }

    @Override
    public OrderDto updateStatus(final Long id, final StatusDto status) {
        final var order = repository.findOrderWithItems(id)
                .orElseThrow(EntityNotFoundException::new);
        order.setStatusOrder(status.getStatus());
        repository.updateStatus(status.getStatus(), order);
        return mapper.mapToDto(order);
    }

    @Override
    public void approvePayment(final Long orderId) {
        final var order = repository.findOrderWithItems(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.setStatusOrder(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
