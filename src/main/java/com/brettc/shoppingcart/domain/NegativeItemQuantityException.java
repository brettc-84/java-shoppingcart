package com.brettc.shoppingcart.domain;

// extends runtime exception due to checked exception axon issue:
// (https://github.com/AxonFramework/AxonFramework/issues/782)
public class NegativeItemQuantityException extends RuntimeException{
    public NegativeItemQuantityException(String basketId, String itemId) {
        super(String.format("Quantity for ItemId %s in Basket %s cannot be smaller than zero", itemId, basketId));
    }
}
