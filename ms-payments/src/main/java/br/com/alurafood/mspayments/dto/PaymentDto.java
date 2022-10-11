package br.com.alurafood.mspayments.dto;

import br.com.alurafood.mspayments.model.OrderItem;
import br.com.alurafood.mspayments.model.Status;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

  private Long id;

  private BigDecimal value;

  private String name;

  private String number;

  private String expirate;

  private String code;

  private Status status;

  private Long orderId;

  private Long paymentId;

  private List<OrderItem> orderitems = new ArrayList<>();
}
