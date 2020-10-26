package com.brettc.shoppingcart.domain;

import com.brettc.shoppingcart.commands.*;
import com.brettc.shoppingcart.events.BasketCreatedEvent;
import com.brettc.shoppingcart.events.BasketItemQuantityChangedEvent;
import com.brettc.shoppingcart.events.BasketItemRemovedEvent;
import com.brettc.shoppingcart.events.NewBasketItemAddedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class BasketAggregateTest {

    private FixtureConfiguration<BasketAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(BasketAggregate.class);
    }

    @Test
    public void testNewBasket() {
        String basketId = UUID.randomUUID().toString();
        fixture.givenNoPriorActivity()
            .when(new CreateBasketCommand(basketId))
            .expectEvents(new BasketCreatedEvent((basketId)));
    }

    @Test
    public void testAddItemToBasket() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId))
            .when(new AddItemToBasketCommand(basketId, "0001"))
            .expectEvents(new NewBasketItemAddedEvent(basketId, "0001"));
    }

    @Test
    public void testAddExistingItemToBasket() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId), new NewBasketItemAddedEvent(basketId, "0001"))
            .when(new AddItemToBasketCommand(basketId, "0001"))
            .expectEvents(new BasketItemQuantityChangedEvent(basketId, "0001", 2));
    }

    @Test
    public void testRemoveItemFromBasket() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId), new NewBasketItemAddedEvent(basketId, "0001"))
            .when(new RemoveItemFromBasketCommand(basketId, "0001"))
            .expectEvents(new BasketItemRemovedEvent(basketId, "0001"));
    }

    @Test
    public void testRemoveNonExistentItemFromBasket() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId))
            .when(new RemoveItemFromBasketCommand(basketId, "0001"))
            .expectException(ItemDoesNotExistException.class);
    }

    @Test
    public void testIncreaseItemQtyInBasket() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId), new NewBasketItemAddedEvent(basketId, "0001"))
            .when(new IncreaseItemQuantityInBasketCommand(basketId, "0001", 1))
            .expectEvents(new BasketItemQuantityChangedEvent(basketId, "0001", 2));
    }

    @Test
    public void testDecreaseItemQtyInBasket() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId), new NewBasketItemAddedEvent(basketId, "0001"), new BasketItemQuantityChangedEvent(basketId, "0001", 2))
            .when(new DecreaseItemQuantityInBasketCommand(basketId, "0001", 1))
            .expectEvents(new BasketItemQuantityChangedEvent(basketId, "0001", 1));
    }

    @Test
    public void testDecreaseItemQtyBelowZeroException() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId), new NewBasketItemAddedEvent(basketId, "0001"))
            .when(new DecreaseItemQuantityInBasketCommand(basketId, "0001", 3))
            .expectException(NegativeItemQuantityException.class);
    }

    @Test
    public void testDecreaseItemQtyToZero() {
        String basketId = UUID.randomUUID().toString();
        fixture.given(new BasketCreatedEvent(basketId), new NewBasketItemAddedEvent(basketId, "0001"))
            .when(new DecreaseItemQuantityInBasketCommand(basketId, "0001", 1))
            .expectEvents(new BasketItemRemovedEvent(basketId, "0001"));
    }
}
