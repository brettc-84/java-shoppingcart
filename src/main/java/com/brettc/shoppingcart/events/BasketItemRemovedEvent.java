package com.brettc.shoppingcart.events;

import java.util.Objects;

public class BasketItemRemovedEvent {
    private final String basketId;
    private final String itemId;

    public BasketItemRemovedEvent(String basketId, String itemId) {
        this.basketId = basketId;
        this.itemId = itemId;
    }

    public String getBasketId() {
        return basketId;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketItemRemovedEvent that = (BasketItemRemovedEvent) o;
        return Objects.equals(basketId, that.basketId) &&
            Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketId, itemId);
    }
}
