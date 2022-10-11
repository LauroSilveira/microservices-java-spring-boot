package br.com.alurafood.mspayments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

  private Long id;
  private Integer quantity;
  private String description;

}
