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
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public List<OrderDto> getOrders() {
        return repository.findAll().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(EntityNotFoundException::new);

    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        final Order order = mapper.mapToEntiy(orderDto);

        order.setPurchaseDate(LocalDateTime.now());
        order.setStatusOrder(Status.ORDERED);
        order.getItems().forEach(item -> item.setOrder(order));
        repository.save(order);

        return mapper.mapToDto(order);
    }

    @Override
    public OrderDto updateStatus(Long id, StatusDto dto) {

        final var order = repository.porIdComItens(id);

        if (Objects.isNull(order)) {
            throw new EntityNotFoundException();
        }

        order.setStatusOrder(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return mapper.mapToDto(order);
    }

    @Override
    public void approvePayment(Long id) {

        final var order = repository.porIdComItens(id);

        if (Objects.isNull(order)) {
            throw new EntityNotFoundException();
        }

        order.setStatusOrder(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
