package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Product;

@RestController
@RequestMapping("/product")
public class TestController {

	private RestClient restClient = RestClient.create();

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/")
	public List<Product> getAllProducts() {

		Product[] body = restClient.get().uri("https://fakestoreapi.com/products").retrieve().body(Product[].class);

		return Arrays.asList(body);
	}

	@GetMapping("/{productId}")
	public Product getProductById(@PathVariable("productId") Integer product) {

		System.out.println("ProductId:" + product);
		Product body = restClient.get().uri("https://fakestoreapi.com/products/{product}", product).retrieve()
				.body(Product.class);

		return body;
	}

	@PutMapping("/{productId}")
	public Product updateProductById(@PathVariable("productId") Integer productId, @RequestBody Product product) {

		System.out.println("ProductId:" + product);
		/*
		 * String body = restClient .put()
		 * .uri("https://fakestoreapi.com/products/{id}",product,productId) .retrieve()
		 * .body(String.class);
		 */
		System.out.println("product" + product);
		// restTemplate.put("https://fakestoreapi.com/products/{id}", product,
		// productId);

		HttpEntity<Product> entity = new HttpEntity<Product>(product);

		ResponseEntity<Product> exchange = restTemplate.exchange("https://fakestoreapi.com/products/{id}",
				HttpMethod.PUT, entity, Product.class, productId);
		Product body = exchange.getBody();

		return body;
	}

	@PostMapping("/")
	public Product addProductById(@RequestBody Product product) {
		System.out.println("product" + product);
//		HttpEntity<Product> entity = new HttpEntity<Product>(product);
//		ResponseEntity<Product> exchange = restTemplate
//				.exchange("https://fakestoreapi.com/products",HttpMethod.POST, entity, Product.class);
//		Product body = exchange.getBody();

		// Product postForObject =
		// restTemplate.postForObject("https://fakestoreapi.com/products", product,
		// Product.class);

		ResponseEntity<Product> postForEntity = restTemplate.postForEntity("https://fakestoreapi.com/products", product,
				Product.class);
		Product body = postForEntity.getBody();

		return body;
	}

	@DeleteMapping("/{productId}")
	public String deleteProductById(@PathVariable("productId") Integer product) {

		System.out.println("ProductId:" + product);

		/*
		 * String body = restClient .delete()
		 * .uri("https://fakestoreapi.com/products/{id}", product) .retrieve()
		 * .body(String.class);
		 */
		restTemplate.delete("https://fakestoreapi.com/products/{id}", product);

		return "Deleted successfully";
	}
}
