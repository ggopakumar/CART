package com.etailer.cartms.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartProduct {

    @EmbeddedId
    @JsonIgnore
    private CartProductPK pk;

    @Column(nullable = false) private Integer quantity;
    
    @Transient
    private Product product;

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public CartProduct() {
        super();
    }

    public CartProduct(Cart order, Product product, Integer quantity) {
        pk = new CartProductPK();
        pk.setOrderId(order.getId());
        pk.setProductId(product.getId());
        this.quantity = quantity;
    }

    @Transient
    public Long getProductId() {
        return this.pk.getProductId();
    }

    public CartProductPK getPk() {
        return pk;
    }

    public void setPk(CartProductPK pk) {
        this.pk = pk;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CartProduct other = (CartProduct) obj;
        if (pk == null) {
            if (other.pk != null) {
                return false;
            }
        } else if (!pk.equals(other.pk)) {
            return false;
        }

        return true;
    }
}
