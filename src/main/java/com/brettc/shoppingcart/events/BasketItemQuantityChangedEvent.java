package com.brettc.shoppingcart.events;

import java.util.Objects;

public class BasketItemQuantityChangedEvent {
    private final String basketId;
    private final String itemId;
    private final Integer newQuantity;

    public BasketItemQuantityChangedEvent(String basketId, String itemId, Integer newQuantity) {
        this.basketId = basketId;
        this.itemId = itemId;
        this.newQuantity = newQuantity;
    }

    public String getBasketId() {
        return basketId;
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketItemQuantityChangedEvent that = (BasketItemQuantityChangedEvent) o;
        return Objects.equals(basketId, that.basketId) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(newQuantity, that.newQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketId, itemId, newQuantity);
    }
}
