package com.brettc.shoppingcart.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RemoveItemFromBasketCommand {
    @TargetAggregateIdentifier
    private String basketId;
    private String itemId;

    public RemoveItemFromBasketCommand(String basketId, String itemId) {
        this.basketId = basketId;
        this.itemId = itemId;
    }

    public RemoveItemFromBasketCommand() { }

    public String getBasketId() {
        return basketId;
    }

    public String getItemId() {
        return itemId;
    }
}
