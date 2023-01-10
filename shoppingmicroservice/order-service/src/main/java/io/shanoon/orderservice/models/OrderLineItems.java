package io.shanoon.orderservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER-LINE-ITEMS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String skuCode;
    private Double price;
    private Integer quantity;
}
