package io.shanoon.orderservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.shanoon.orderservice.dto.OrderRequest;
import io.shanoon.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/addOrder")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBack")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return orderService.placeOrder(orderRequest);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public CompletableFuture<String> fallBack(OrderRequest orderRequest, RuntimeException exception){
        return CompletableFuture.supplyAsync(
                ()-> "Something went wrong, please retry after some time");
    }
}
