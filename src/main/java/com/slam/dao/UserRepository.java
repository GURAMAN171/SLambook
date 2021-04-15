package com.slam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.slam.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	// parameterized jpql query
	@Query("select u from User u where u.email= :email")
	public User getUserByUserEmail(@Param("email") String email);
}
