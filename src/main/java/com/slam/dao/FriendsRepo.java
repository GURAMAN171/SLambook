package com.slam.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.slam.model.Friends;

public interface FriendsRepo extends JpaRepository<Friends, Integer> {

	
	@Query("from Friends as f where f.user.id =:userId")
	//public List<Friends> findFriendsByUser(@Param("userId")int userId);
	
	//Method to implmnt Pagination we will return page not list
	//current page and frns per pg- 2
	public Page<Friends>  findFriendsByUser(@Param("userId")int userId,Pageable pgbl);
	
	
	
}
