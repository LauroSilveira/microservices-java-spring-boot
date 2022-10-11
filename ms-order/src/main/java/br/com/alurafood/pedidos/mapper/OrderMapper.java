package br.com.alurafood.pedidos.mapper;

import br.com.alurafood.pedidos.dto.OrderDto;
import br.com.alurafood.pedidos.dto.OrderItemDto;
import br.com.alurafood.pedidos.model.Order;
import br.com.alurafood.pedidos.model.OrderItem;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {


  OrderDto mapToDto(Order order);

  Order mapToEntiy(OrderDto orderDto);

  List<OrderItem> mapToItemEntity(List<OrderItemDto> itemDtos);

  List<OrderItemDto> mapToItemsDto(List<OrderItem> items);
}
