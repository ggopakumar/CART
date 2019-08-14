package com.etailer.cartms.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.etailer.cartms.model.CartProduct;
import com.etailer.cartms.repository.CartProductRepository;
import com.etailer.cartms.service.CartProductService;

@Service
@Transactional
public class CartProductServiceImpl implements CartProductService {

    private CartProductRepository orderProductRepository;

    public CartProductServiceImpl(CartProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public CartProduct create(CartProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }
}
