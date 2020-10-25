package com.brettc.shoppingcart.domain;

// extends runtime exception due to checked exception axon issue:
// (https://github.com/AxonFramework/AxonFramework/issues/782)
public class ItemDoesNotExistException extends RuntimeException{
    public ItemDoesNotExistException(String basketId, String itemId) {
        super(String.format("ItemId: %s does not exist in Basket: %s", itemId, basketId));
    }
}
