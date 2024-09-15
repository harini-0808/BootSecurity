package com.security.demo.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.demo.models.Product;
import com.security.demo.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;
	
	public Product save(Product product) {
	
		return repository.save(product);
		
	}
	
	public List<Product> getProducts(){
		return repository.findAll();
	}
	
	public Product getProductByID(long id) {
		Optional<Product> product = repository.findById(id);
		return product.isPresent()? product.get() : null;
	}
	
	public boolean deleteProductByID(long id) {
		if(repository.findById(id).isPresent()) {
			repository.deleteById(id);
			return true;
		}
		return false;	
		  
	}
	
	public Product updateProduct(long id,Product product) {
		if(repository.findById(id).isPresent()) {
			return repository.save(product);
		}
		return null;
	}
	
	
}

