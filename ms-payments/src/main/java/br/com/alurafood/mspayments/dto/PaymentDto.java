package br.com.alurafood.mspayments.dto;

import br.com.alurafood.mspayments.model.OrderItem;
import br.com.alurafood.mspayments.model.Status;

import java.math.BigDecimal;
import java.util.List;


public record PaymentDto(Long id,
                         BigDecimal value,
                         String name,
                         String number,
                         String expired,
                         String code,
                         Status status,
                         Long orderId,
                         Long paymentId,
                         List<OrderItem> orderItems) {
}
