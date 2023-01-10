package com.example.inventoryservice;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);

    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository repository){
        return args -> {
            Inventory inventory1 =  new Inventory();
            Inventory inventory2 =  new Inventory();
            inventory1.setQuantity(12);
            inventory2.setQuantity(0);
            inventory1.setSkuCode("sumsung");
            inventory2.setSkuCode("iPhone");

            repository.saveAll(List.of(inventory1,inventory2));
        };
    }
}
