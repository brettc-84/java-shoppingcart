package com.brettc.shoppingcart.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class IncreaseItemQuantityInBasketCommand {
    @TargetAggregateIdentifier
    private String basketId;
    private String itemId;
    private Integer amount;

    public IncreaseItemQuantityInBasketCommand(String basketId, String itemId, Integer amount) {
        this.basketId = basketId;
        this.itemId = itemId;
        this.amount = amount;
    }

    public IncreaseItemQuantityInBasketCommand() {}

    public String getBasketId() {
        return basketId;
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getAmount() {
        return amount;
    }
}
