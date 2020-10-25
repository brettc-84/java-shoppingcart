package com.brettc.shoppingcart.api.web;

import com.brettc.shoppingcart.api.web.dto.AddNewBasketItemRequestDTO;
import com.brettc.shoppingcart.commands.AddItemToBasketCommand;
import com.brettc.shoppingcart.commands.CreateBasketCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class BasketRestEndpoint {

    private final CommandGateway commandGateway;

    public BasketRestEndpoint(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create-basket")
    @ResponseStatus(value = CREATED)
    public CompletableFuture<String> createBasket() {
        String basketId = UUID.randomUUID().toString();
        return this.commandGateway.send(new CreateBasketCommand(basketId));
    }

    @PostMapping("/{basketId}/add-item")
    public CompletableFuture<String> addItemToBasket(@PathVariable(value = "basketId") String basketId,
                                  @RequestBody AddNewBasketItemRequestDTO addNewBasketItemRequest) {
        AddItemToBasketCommand addToBasketCmd = new AddItemToBasketCommand(basketId, addNewBasketItemRequest.getItemId());
        return this.commandGateway.send(addToBasketCmd);
    }

}
