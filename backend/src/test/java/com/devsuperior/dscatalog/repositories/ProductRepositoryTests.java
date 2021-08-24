package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;


@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long exintingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		
		exintingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull () {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		Optional<Product> result = repository.findById(product.getId());
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts +1, product.getId());
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(result.get(), product);
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists () {
		
		repository.deleteById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist () {
		
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()-> {
			repository.deleteById(nonExistingId);
		});
	}
	

	@Test
	public void findByIdShouldReturnNomEmptyOptinalWhenIdExists() {
		Optional<Product> result = repository.findById(exintingId);
		
		Assertions.assertTrue(result.isPresent());
	}
	
	
	@Test
	public void findByIdShouldReturnEmptyOptinalWhenIdDoesNotExists() {
		Optional<Product> result = repository.findById(nonExistingId);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
