package com.etailer.cartms.controller;


import com.etailer.cartms.dto.CartProductDto;
import com.etailer.cartms.exception.ResourceNotFoundException;
import com.etailer.cartms.model.Cart;
import com.etailer.cartms.model.CartProduct;
import com.etailer.cartms.model.CartStatus;
import com.etailer.cartms.model.Product;
import com.etailer.cartms.service.CartProductService;
import com.etailer.cartms.service.CartService;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    CartService orderService;
    CartProductService orderProductService;
    DiscoveryClient discoveryClient;

    public CartController(CartService orderService, CartProductService orderProductService, DiscoveryClient discoveryClient) {
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<Cart> list() {
    	Iterable<Cart> carts = this.orderService.getAllOrders();
    	for (Cart cart : carts) {
    		Double totalAmount = getTotalOrderPrice(cart);
    		cart.setTotalOrderPrice(totalAmount);
    	}
    	return carts;    	
    }
        
    @CrossOrigin
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
    	System.out.println("Cross origin support");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path="/")
    public ResponseEntity<Cart> create(@RequestBody OrderForm form) {
        List<CartProductDto> formDtos = form.getProductOrders();
        validateProductsExistence(formDtos);
        Cart order = new Cart();
        order.setStatus(CartStatus.PAID.name());
        order = this.orderService.create(order);

        List<CartProduct> orderProducts = new ArrayList<>();
        for (CartProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new CartProduct(order, getProduct(dto
              .getProduct()
              .getId()), dto.getQuantity())));
        }

        order.setOrderProducts(orderProducts);

        this.orderService.update(order);

        String uri = ServletUriComponentsBuilder
          .fromCurrentServletMapping()
          .path("/cart/{id}")
          .buildAndExpand(order.getId())
          .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);
    	System.out.println("Cart post method");
        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }


	private Product getProduct(Long id) {
		List<ServiceInstance> instances = discoveryClient.getInstances("product-ms");
		if (instances != null && instances.size() > 0) {// todo: replace with a load balancing mechanism
			ServiceInstance serviceInstance = instances.get(0);
			String url = serviceInstance.getUri().toString();
			url = url + "/api/products/"+id;
			RestTemplate restTemplate = new RestTemplate();
			Product product = restTemplate.getForObject(url, Product.class);
			return product;
		}
		return null;
	}
	
	public Double getTotalOrderPrice(Cart cart) {
        double sum = 0D;
        List<CartProduct> orderProducts = cart.getOrderProducts();
        for (CartProduct op : orderProducts) {
        	Product prod = getProduct(op.getProductId());
        	op.setProduct(prod);
            sum += getTotalPrice(prod.getPrice(), op.getQuantity());
        }

        return sum;
    }
	
	public Double getTotalPrice(Double price, Integer quantity) {
        return price * quantity;
    }

	private void validateProductsExistence(List<CartProductDto> orderProducts) {
        List<CartProductDto> list = orderProducts
          .stream()
          .filter(op -> Objects.isNull(getProduct(op
            .getProduct()
            .getId())))
          .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("Product not found");
        }
    }

    public static class OrderForm {

        private List<CartProductDto> productOrders;

        public List<CartProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<CartProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }
}