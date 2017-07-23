package com.shoppingcart.apps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shoppingcart.apps.entity.Product;





// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	
	@Query(value="select new com.shoppingcart.apps.entity.Product(p.code, p.name, p.price) from #{#entityName} p order by p.createDate desc")
	List <Product> findAllProducts();
	
	


}
