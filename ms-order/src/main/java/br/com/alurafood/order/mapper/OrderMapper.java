package br.com.alurafood.order.mapper;

import br.com.alurafood.order.dto.OrderDto;
import br.com.alurafood.order.dto.OrderItemDto;
import br.com.alurafood.order.model.Order;
import br.com.alurafood.order.model.OrderItem;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {


  OrderDto mapToDto(Order order);

  Order mapToEntity(OrderDto orderDto);

  List<OrderItem> mapToItemEntity(List<OrderItemDto> itemDtos);

  List<OrderItemDto> mapToItemsDto(List<OrderItem> items);
}
