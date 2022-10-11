package br.com.alurafood.mspayments.feignclient;

import br.com.alurafood.mspayments.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("ms-order")
public interface OrderFeignClient {

  @PutMapping(value = "/order/{id}/payment")
  void updatePayment(@PathVariable final Long paymentId);

  @GetMapping(value = "/order/{id}")
  Order getOrderItems(@PathVariable final Long orderId);
}
