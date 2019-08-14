package com.etailer.cartms.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.etailer.cartms.model.Cart;
import com.etailer.cartms.repository.CartRepository;
import com.etailer.cartms.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private CartRepository orderRepository;

    public CartServiceImpl(CartRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Iterable<Cart> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Cart create(Cart order) {
        order.setDateCreated(LocalDate.now());

        return this.orderRepository.save(order);
    }

    @Override
    public void update(Cart order) {
        this.orderRepository.save(order);
    }
}
