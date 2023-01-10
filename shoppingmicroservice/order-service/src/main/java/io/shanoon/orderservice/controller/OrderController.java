package io.shanoon.orderservice.controller;

import io.shanoon.orderservice.dto.OrderRequest;
import io.shanoon.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/addOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }
}
