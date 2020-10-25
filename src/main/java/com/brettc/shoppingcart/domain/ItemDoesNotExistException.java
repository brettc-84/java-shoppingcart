package com.brettc.shoppingcart.domain;


public class ItemDoesNotExistException extends Throwable{
    public ItemDoesNotExistException(String basketId, String itemId) {
        super(String.format("ItemId: %s does not exist in Basket: %s", itemId, basketId));
    }
}
