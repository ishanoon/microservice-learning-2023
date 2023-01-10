package io.shanoon.orderservice.service;

import io.shanoon.orderservice.dto.InventoryResponse;
import io.shanoon.orderservice.dto.OrderRequest;
import io.shanoon.orderservice.models.Order;
import io.shanoon.orderservice.models.OrderLineItems;
import io.shanoon.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final String INVENTORY_SERVICE_BASE_URL = "http://INVENTORY-SERVICE/api/v1/inventory";


    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderLineItems(orderRequest));


        List<String> skuCodes = skuCodes(order);
        InventoryResponse[] inventoryResponses = inventoryResponses(skuCodes);

        boolean allProductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalAccessException("Product is not in stock");
        }

    }

    private List<OrderLineItems> orderLineItems(OrderRequest orderRequest){
        return orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> OrderLineItems.builder()
                        .price(orderLineItemsDto.getPrice())
                        .skuCode(orderLineItemsDto.getSkuCode())
                        .quantity(orderLineItemsDto.getQuantity())
                        .build())
                .toList();
    }

    private List<String> skuCodes(Order order){
        return  order.getOrderLineItemsList()
                .stream().map(OrderLineItems::getSkuCode)
                .toList();
    }

    private InventoryResponse[] inventoryResponses( List<String> skuCodes ){
        return webClientBuilder
                .build()
                .get()
                .uri(INVENTORY_SERVICE_BASE_URL,
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
    }
}
