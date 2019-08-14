package com.etailer.cartms.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.etailer.cartms.model.CartProduct;

@Validated
public interface CartProductService {

    CartProduct create(@NotNull(message = "The products for order cannot be null.") @Valid CartProduct orderProduct);
}
