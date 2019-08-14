package com.etailer.cartms.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.etailer.cartms.model.Cart;

@Validated
public interface CartService {

    @NotNull Iterable<Cart> getAllOrders();

    Cart create(@NotNull(message = "The order cannot be null.") @Valid Cart order);

    void update(@NotNull(message = "The order cannot be null.") @Valid Cart order);
}
