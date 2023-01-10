package io.shanoon.orderservice.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
