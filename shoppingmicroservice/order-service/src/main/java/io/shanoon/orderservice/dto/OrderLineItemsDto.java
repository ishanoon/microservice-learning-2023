package io.shanoon.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemsDto {
    private String skuCode;
    private Double price;
    private Integer quantity;
}
