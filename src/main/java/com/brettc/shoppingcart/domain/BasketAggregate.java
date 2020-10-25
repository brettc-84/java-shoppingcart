package com.brettc.shoppingcart.domain;

import com.brettc.shoppingcart.commands.AddItemToBasketCommand;
import com.brettc.shoppingcart.commands.CreateBasketCommand;
import com.brettc.shoppingcart.events.BasketCreatedEvent;
import com.brettc.shoppingcart.events.BasketItemQuantityChangedEvent;
import com.brettc.shoppingcart.events.NewBasketItemAddedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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
        Integer itemsA = items.get(command.getItemId());
        if (itemsA != null) {
            AggregateLifecycle.apply(new BasketItemQuantityChangedEvent(command.getBasketId(), command.getItemId(), itemsA+1));
        } else {
            AggregateLifecycle.apply(new NewBasketItemAddedEvent(command.getBasketId(), command.getItemId()));
        }
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
}
