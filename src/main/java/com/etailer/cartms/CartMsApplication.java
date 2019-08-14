package com.etailer.cartms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.etailer.cartms.model.Product; 

@SpringBootApplication
@EnableDiscoveryClient
public class CartMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartMsApplication.class, args);
	}

	@Bean
    public CorsFilter corsFilter() {
    	System.out.println("CORS filter executing");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	
//	@Bean
//	CommandLineRunner runner(ProductService productService) {
//		return args -> {
//			productService.save(new Product(1L, "TV Set", 300.00, "http://placehold.it/200x100"));
//			productService.save(new Product(2L, "Game Console", 200.00, "http://placehold.it/200x100"));
//			productService.save(new Product(3L, "Sofa", 100.00, "http://placehold.it/200x100"));
//			productService.save(new Product(4L, "Icecream", 5.00, "http://placehold.it/200x100"));
//			productService.save(new Product(5L, "Beer", 3.00, "http://placehold.it/200x100"));
//			productService.save(new Product(6L, "Phone", 500.00, "http://placehold.it/200x100"));
//			productService.save(new Product(7L, "Watch", 30.00, "http://placehold.it/200x100"));
//		};
//	}
}
