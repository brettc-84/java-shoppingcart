package com.brettc.shoppingcart.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AddItemToBasketCommand {
    @TargetAggregateIdentifier
    private String basketId;
    private String itemId;

    public AddItemToBasketCommand(String basketId, String itemId) {
        this.basketId = basketId;
        this.itemId = itemId;
    }

    public AddItemToBasketCommand() { }

    public String getBasketId() {
        return basketId;
    }

    public String getItemId() {
        return itemId;
    }
}
