package com.slam.dao;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.slam.model.User;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	@Autowired
	private UserRepository repo;
	@Autowired
	private TestEntityManager entityManager;
	
	  @Test
	  @Order(1)
	  public void testCreateUser() 
	  {
		  User user=new User();
	  user.setEmail("Junittest@gmail.com");
	  user.setPassword("Jtesttt20");
	  user.setName("JTEstttst");
	  user.setEnabled(true); 
	  User savedUser =repo.save(user); 
	  User existUser =entityManager.find(User.class, savedUser.getId());
	  assertThat(existUser.getEmail()).isEqualTo(user.getEmail()); }
	 
	
	
	  @Test
	  @Order(2)
	  public void testFindByEmailId() {
	      User u = repo.getUserByUserEmail("Shehnaz123@gmail.com"); 
	      assertThat(u.getEmail()).isEqualTo("Shehnaz123@gmail.com");
	  }
	  
	  
	 
	  @Test
	  @Order(3)
	  public void testListUsers()
	  { 
		  List<User> users = (List<User>)
	  repo.findAll(); assertThat(users).size().isGreaterThan(0);
	  }
	 
	  @Test 
	  @Order(4)
	  @Rollback(false)
	  public void testUpdateUser() {
	      User u = repo.getUserByUserEmail("Shehnaz123@gmail.com");
	      u.setName("Shehnaaz Gill");
	       
	      repo.save(u);
	       
	     User updatedUser = repo.getUserByUserEmail("Shehnaz123@gmail.com");
	       
	      assertThat(updatedUser.getName()).isEqualTo("Shehnaaz Gill");
	  }
	
	  @Test
	  @Order(5)
	  @Rollback(false)
    	public void testDeleteUser() {
	    Optional<User> u = repo.findById(35);
	     
	    repo.delete(u.get());
	     
	    Optional<User> deletedUser = repo.findById(35);
	     
	    assertThat(deletedUser).isEmpty();      
	     
	}

}
