package com.brettc.shoppingcart.commands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateBasketCommand {
    @TargetAggregateIdentifier
    private String basketId;

    public CreateBasketCommand(String basketId) {
        this.basketId = basketId;
    }

    public String getBasketId() {
        return basketId;
    }
}
