package br.com.alurafood.pedidos.repository;

import br.com.alurafood.pedidos.model.Order;
import br.com.alurafood.pedidos.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order p set p.statusOrder = :status where p = :order")
    void updateStatus(Status status, Order order);

    @Query(value = "SELECT p from Order p LEFT JOIN FETCH p.items where p.id = :id")
    Order porIdComItens(Long id);

}
