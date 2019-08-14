package com.etailer.cartms.repository;

import org.springframework.data.repository.CrudRepository;

import com.etailer.cartms.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
