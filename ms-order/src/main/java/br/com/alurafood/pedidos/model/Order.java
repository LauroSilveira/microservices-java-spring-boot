package br.com.alurafood.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime purchaseDate;

    @NotNull @Enumerated(EnumType.STRING)
    private Status statusOrder;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();
}
