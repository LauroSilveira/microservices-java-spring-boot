package br.com.alurafood.mspayments.dto;

import br.com.alurafood.mspayments.model.OrderItem;
import br.com.alurafood.mspayments.model.Payment;
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

    public PaymentDto(Payment payment, List<OrderItem> orderItems) {
        this(payment.getId(), payment.getValue(),
                payment.getName(), payment.getNumber(), payment.getExpired(),
                payment.getCode(), payment.getStatus(), payment.getOrderId(), payment.getPaymentId(), orderItems);
    }
}
