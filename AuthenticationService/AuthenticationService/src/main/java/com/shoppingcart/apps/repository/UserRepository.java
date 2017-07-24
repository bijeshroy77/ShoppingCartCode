package com.shoppingcart.apps.repository;

import org.springframework.data.jpa.repository.Query;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppingcart.apps.entity.Account;
//import com.shoppingcart.apps.entity.Product;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface UserRepository extends CrudRepository<Account, String> {
	
	@Query(value="select new com.shoppingcart.apps.entity.Account(p.userName, p.password, p.active, p.userRole) from #{#entityName} p "
			+ "where p.userName=:userName and p.password=:password ")
	public Account validateUser(@Param("userName")  String userName, @Param("password") String password);

}
