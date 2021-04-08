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
	private UserRepository urepo;
	@Autowired
	private TestEntityManager entityManager;
	@Test
	public void testCreateFriends()
	{
		Friends friends=new Friends();
		friends.setEmail("finltest@gmail.com");
		friends.setName("finltes3");
		friends.setAddress("finltes CRTN ADDRESS");
		friends.setDescr("Tfinltes DESC");
		friends.setImage("default.png");
		friends.setPhone("72494698");
		friends.setWork("Tester");
		friends.setSecondName("finltestng");
		friends.setUser(this.urepo.getOne(36));
		Friends savedFriend =frepo.save(friends);
		Friends existFriend =entityManager.find(Friends.class, savedFriend.getfId());
		assertThat(existFriend.getEmail()).isEqualTo(friends.getEmail());		
	}
	
	@Test
	public void testListFriends() {
	    List<Friends> friends = (List<Friends>) frepo.findAll();
	    assertThat(friends).size().isGreaterThan(0);
	}
	
	
	  @Test
	  @Rollback(false)
    	public void testDeleteFriends() {
	    Optional<Friends> f = frepo.findById(44);
	     
	    frepo.delete(f.get());
	     
	    Optional<Friends> deletedFrnd = frepo.findById(44);
	     
	    assertThat(deletedFrnd).isEmpty();      
	     
	}
	
	  @Test
	  @Rollback(false)
    	public void testupdateFriends() {
	    Friends friends = frepo.getFriendsByFriendEmail("testfrnd@gmail.com");
	    friends.setName("mupdt");
	    friends.setWork("Tester");
	    friends.setAddress("mupdt ADDRESS");
		friends.setDescr("mupdt DESC");
		friends.setImage("default.png");
		friends.setPhone("72494698");
		friends.setSecondName("mupdt testng");
		friends.setUser(this.urepo.getOne(36));
	 
	    frepo.save(friends);
	    Friends updatedfrnds= frepo.getFriendsByFriendEmail("testfrnd@gmail.com");
	      assertThat(updatedfrnds.getName()).isEqualTo("mupdt");
	           
	}
	 
}
