package br.com.alurafood.mspayments.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Order {

  private List<OrderItem> items = new ArrayList<>();

}
