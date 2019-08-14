package com.etailer.cartms.repository;

import org.springframework.data.repository.CrudRepository;

import com.etailer.cartms.model.CartProduct;
import com.etailer.cartms.model.CartProductPK;

public interface CartProductRepository extends CrudRepository<CartProduct, CartProductPK> {
}
