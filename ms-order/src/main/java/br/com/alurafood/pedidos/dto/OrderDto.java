package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDateTime purchaseDate;
    private Status statusOrder;
    private List<OrderItemDto> items = new ArrayList<>();

}
