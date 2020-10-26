package com.brettc.shoppingcart.api.web;

import com.brettc.shoppingcart.api.web.dto.AddNewBasketItemRequestDTO;
import com.brettc.shoppingcart.api.web.dto.IncreaseDecreaseBasketItemQuantityDTO;
import com.brettc.shoppingcart.commands.*;
import com.brettc.shoppingcart.domain.ItemDoesNotExistException;
import io.axoniq.axonserver.grpc.command.Command;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/{basketId}/items/{itemId}")
    public ResponseEntity removeItemFromBasket(@PathVariable(value = "basketId") String basketId,
                                                          @PathVariable(value = "itemId") String itemId) {
        RemoveItemFromBasketCommand removeFromBasketCmd = new RemoveItemFromBasketCommand(basketId, itemId);
        try {
            return this.commandGateway.sendAndWait(removeFromBasketCmd);
        } catch (CommandExecutionException exception) {
            return new ResponseEntity<String>("Something went wrong:"+ exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{basketId}/items/{itemId}/inc-quantity")
    public ResponseEntity increaseItemQtyInBasket(@PathVariable(value = "basketId") String basketId,
                                                @PathVariable(value = "itemId") String itemId,
                                                @RequestBody IncreaseDecreaseBasketItemQuantityDTO incBasketItemQtyRequest) {
        try {
            IncreaseItemQuantityInBasketCommand
                incQtyCommand = new IncreaseItemQuantityInBasketCommand(basketId, itemId, incBasketItemQtyRequest.getAmount());
            return this.commandGateway.sendAndWait(incQtyCommand);
        } catch (CommandExecutionException exception) {
            return new ResponseEntity<String>("Something went wrong:"+ exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{basketId}/items/{itemId}/dec-quantity")
    public ResponseEntity decreaseItemQtyInBasket(@PathVariable(value = "basketId") String basketId,
                                                @PathVariable(value = "itemId") String itemId,
                                                @RequestBody IncreaseDecreaseBasketItemQuantityDTO decBasketItemQtyRequest) {
        try {
            DecreaseItemQuantityInBasketCommand
                incQtyCommand = new DecreaseItemQuantityInBasketCommand(basketId, itemId, decBasketItemQtyRequest.getAmount());
            return this.commandGateway.sendAndWait(incQtyCommand);
        } catch (CommandExecutionException exception) {
            return new ResponseEntity<String>("Something went wrong:"+ exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
