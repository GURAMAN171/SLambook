package com.slam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.slam.model.User;

public interface UserRepository extends JpaRepository<User, Integer>  //type of data need is user and type of id is integer
{  
	//parameterized jpql query
	@Query("select u from User u where u.email= :email") /* dynamic email lana h  */
	public User getUserByUserEmail(@Param("email") String email); /* param use kr k bind kra  dynmc eml */
}
