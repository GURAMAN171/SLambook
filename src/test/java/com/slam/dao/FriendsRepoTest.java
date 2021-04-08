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
import com.slam.model.Friends;
import com.slam.model.User;
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class FriendsRepoTest {
	@Autowired
	private FriendsRepo frepo;
	@Autowired
	private TestEntityManager entityManager;
	@Test
	public void testCreateFriends()
	{
		Friends friends=new Friends();
		friends.setEmail("testfrnd122@gmail.com");
		friends.setName("TEstfrnd");
		Friends savedFriend =frepo.save(friends);
		Friends existFriend =entityManager.find(Friends.class, savedFriend.getfId());
		assertThat(existFriend.getEmail()).isEqualTo(friends.getEmail());		
	}
	
	@Test
	public void testListFriends() {
	    List<Friends> friends = (List<Friends>) frepo.findAll();
	    assertThat(friends).size().isGreaterThan(0);
	}
	  
	
	 
}
