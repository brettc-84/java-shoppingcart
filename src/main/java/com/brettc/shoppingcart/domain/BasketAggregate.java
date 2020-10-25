package com.brettc.shoppingcart.domain;

import com.brettc.shoppingcart.commands.AddItemToBasketCommand;
import com.brettc.shoppingcart.commands.CreateBasketCommand;
import com.brettc.shoppingcart.commands.RemoveItemFromBasketCommand;
import com.brettc.shoppingcart.events.BasketCreatedEvent;
import com.brettc.shoppingcart.events.BasketItemQuantityChangedEvent;
import com.brettc.shoppingcart.events.BasketItemRemovedEvent;
import com.brettc.shoppingcart.events.NewBasketItemAddedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@Aggregate
public class BasketAggregate {
    @AggregateIdentifier
    private String basketId;
    private HashMap<String, Integer> items;

    @CommandHandler
    public BasketAggregate(CreateBasketCommand command) {
        AggregateLifecycle.apply(new BasketCreatedEvent(command.getBasketId()));
    }

    public BasketAggregate() {}

    @CommandHandler
    public void handle(AddItemToBasketCommand command) {
        if (items.containsKey(command.getItemId())) {
            Integer newQty = items.get(command.getItemId())+1;
            AggregateLifecycle.apply(new BasketItemQuantityChangedEvent(command.getBasketId(), command.getItemId(), newQty));
        } else {
            AggregateLifecycle.apply(new NewBasketItemAddedEvent(command.getBasketId(), command.getItemId()));
        }
    }

    @CommandHandler
    public void handle(RemoveItemFromBasketCommand command) throws ItemDoesNotExistException{
        if (!items.containsKey(command.getItemId())) {
            throw new ItemDoesNotExistException(command.getBasketId(), command.getItemId());
        }
        AggregateLifecycle.apply(new BasketItemRemovedEvent(command.getBasketId(), command.getItemId()));
    }

    @EventSourcingHandler
    public void on(BasketCreatedEvent event) {
        this.basketId = event.getBasketId();
        this.items = new HashMap<String, Integer>();
    }

    @EventSourcingHandler
    public void on(NewBasketItemAddedEvent event) {
        this.items.put(event.getItemId(), 1);
    }

    @EventSourcingHandler
    public void on(BasketItemQuantityChangedEvent event) {
        this.items.put(event.getItemId(), event.getNewQuantity());
    }

    @EventSourcingHandler
    public void on(BasketItemRemovedEvent event) { this.items.remove(event.getItemId()); }
}
