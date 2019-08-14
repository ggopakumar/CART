package com.etailer.cartms.dto;

import java.io.Serializable;

import com.etailer.cartms.model.Product;

public class CartProductDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Product product;
    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
