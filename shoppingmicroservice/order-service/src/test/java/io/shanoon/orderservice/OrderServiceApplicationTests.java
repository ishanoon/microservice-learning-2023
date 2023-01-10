package io.shanoon.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.netflix.discovery.shared.Application;
import io.shanoon.orderservice.dto.OrderLineItemsDto;
import io.shanoon.orderservice.dto.OrderRequest;
import io.shanoon.orderservice.models.Order;
import io.shanoon.orderservice.models.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AllArgsConstructor
class OrderServiceApplicationTests {
    private static String ORDER_BASE_URL = "/api/v1/order";

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @Test
    void shouldPlaceOrder() throws Exception {


        mvc.perform(MockMvcRequestBuilders
                .post(ORDER_BASE_URL)
                        .content(objectMapper.writeValueAsString(getOrderRequest()))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated());

    }

    private OrderRequest getOrderRequest(){
        Order order = Order.builder()
                .orderNumber("4567")
                .id(23L)
                .orderLineItemsList(List.of(OrderLineItems.builder()
                                .quantity(10)
                                .skuCode("iPhone")
                                .price(230.00)
                        .build()))
                .build();

       List<OrderLineItemsDto> orderLineItemsDtoList =  order.getOrderLineItemsList()
                .stream().map(
                        orderLineItems -> OrderLineItemsDto
                                .builder()
                                .price(orderLineItems.getPrice())
                                .skuCode(orderLineItems.getSkuCode())
                                .quantity(orderLineItems.getQuantity())
                                .build()
                ).toList();



       return OrderRequest.builder()
                .orderLineItemsDtoList(orderLineItemsDtoList)
                .build();
    }

}
