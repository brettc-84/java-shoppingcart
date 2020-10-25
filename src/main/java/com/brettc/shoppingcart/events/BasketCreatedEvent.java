package com.brettc.shoppingcart.events;

import java.util.Objects;

public class BasketCreatedEvent {
    private final String basketId;

    public BasketCreatedEvent(String basketId) {
        this.basketId = basketId;
    }

    public String getBasketId() {
        return basketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketCreatedEvent that = (BasketCreatedEvent) o;
        return Objects.equals(basketId, that.basketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketId);
    }


}
