package com.slam.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.slam.dao.FriendsRepo;
import com.slam.dao.UserRepository;
import com.slam.helper.Message;
import com.slam.model.Friends;
import com.slam.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendsRepo friendsRepo;
	
	
	//adding common data to response for all pages
	@ModelAttribute
	public void addData(Model m,Principal p)
	{

		String email=p.getName();
		System.out.println("Email : "+email);
		//get user using email
		User user=userRepository.getUserByUserEmail(email);
		System.out.println("USER"+user);
		m.addAttribute("user",user);
	
		
	}
	//profie home dashboard 
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{
		String email=principal.getName();
		System.out.println("Email : "+email);
		//get user using email
		User user=userRepository.getUserByUserEmail(email);
		System.out.println("USER"+user);
		model.addAttribute("user",user);
		model.addAttribute("title", "My Profile");
	
		
		return "users/user_dashboard";
	}
	
	//add about friends
	@GetMapping("/addfrnds")
	public String addFriends(Model model)
	{
		model.addAttribute("title","Add Friends");
		model.addAttribute("friends",new Friends()); // new objct crtd
		return "users/addfriends";
	}
	
	//processing addfrns form 
	@PostMapping("/processfrns")  //mtched url with form action
	public String processFriends(@ModelAttribute Friends friends,
							   	 @RequestParam("fImage") MultipartFile f,
								 Principal p,		//The principal is the currently logged in user
								 HttpSession session)
	{
		try
		{
			
		//testing System.out.println("DATA " +friends);
		String name=p.getName();
		User user=this.userRepository.getUserByUserEmail(name);
		
		
		//----------------------Processing and uploading file
		if(f.isEmpty())
		{
			//if file is empty thn giv this mesg
			System.out.println("Error in uploading image");
			friends.setImage("friends.png");
			
		}
		else {
			// move file to folder and updt name to user table
			friends.setImage(f.getOriginalFilename());
			File saveF=new ClassPathResource("static/img").getFile();
			Path path=Paths.get(saveF.getAbsolutePath()+File.separator+f.getOriginalFilename());
			Files.copy(f.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); //static mthd
			System.out.println("Image Uploaded");
		}
		
		//--------------------
		
		
		//user ki frns ki list me we will add tht user
		friends.setUser(user); 			//frns ko user dia
		user.getFriends().add(friends); //user se frns nikale or uski list me frns add kr dia
		this.userRepository.save(user);
		System.out.println("Added to database");
		session.setAttribute("message", new Message("Your Friend is added","success"));
		
		}
		catch(Exception e)
		{
			System.out.println("ERROR"+e.getMessage());
			session.setAttribute("message", new Message("Something went wrong","danger"));
			
		}
		return "users/addfriends";
	}
	
	//show friends
	@GetMapping("/showfriends/{page}")
	public String showFriends(@PathVariable("page")Integer page,Model m,Principal p)
	{
		m.addAttribute("title","Show Friends");
		//frnds list to b send
		// need to use this for update also So using this-->
		String eml=p.getName();
		User u = this.userRepository.getUserByUserEmail(eml);

		//pageable will store these 2 info -> frns per page = 2[n]  and current page is 0
		Pageable pageable =PageRequest.of(page, 5);
		Page<Friends> friends = this.friendsRepo.findFriendsByUser(u.getId(),pageable);
		//List<Friends> friends = this.friendsRepo.findFriendsByUser(u.getId());
		m.addAttribute("friends",friends);
		m.addAttribute("currentPage",page);
		m.addAttribute("totalPages",friends.getTotalPages());
		
		
		
	
		
		return "users/showfriends";
	}
	//showing specific frn on click
	@RequestMapping("/{fId}/friends")
	public String showdetails(@PathVariable("fId") Integer fId,Model model,Principal p)
	{
		System.out.println("fid :" +fId);
		Optional<Friends> frnd = this.friendsRepo.findById(fId);
		Friends friends =frnd.get();
		
		//for security
		String un=p.getName();
		User u=this.userRepository.getUserByUserEmail(un);
		if(u.getId()==friends.getUser().getId()); //usr k tbl ki id eql frns k user ki id thn it will run
		{
			model.addAttribute("friends",friends);
			model.addAttribute("title",friends.getName());
		}
		
		return "users/showfrndetails";
	}
	
	//deletefrn
	@GetMapping("/delete/{fId}")
	public String delfrn(@PathVariable("fId")Integer fId,Model model,Principal p,HttpSession s)
	{
		/*
		 * Optional<Friends> frnd = this.friendsRepo.findById(fId); Friends friends
		 * =frnd.get();
		 * //for security String un=p.getName(); User
		 * u=this.userRepository.getUserByUserEmail(un);
		 * if(u.getId()==friends.getUser().getId()); //usr k tbl ki id eql frns k user
		 * ki id thn it will run { model.addAttribute("friends",friends);
		 * model.addAttribute("title",friends.getName()); }
		 */
		 
		Friends frnd = this.friendsRepo.findById(fId).get();
		System.out.println("frnd id : "+frnd.getfId());
		frnd.setUser(null);    //unlink it and thn delete it
		
		this.friendsRepo.delete(frnd);
		System.out.println("deleted");
		s.setAttribute("message", new Message("Friend deleted successfully..","success"));
		
		//security
		/*
		 * String un=p.getName(); User u=this.userRepository.getUserByUserEmail(un);
		 * 
		 * if(u.getId()==frnd.getUser().getId()); //usr k tbl ki id eql frns k user ki
		 * id thn it will run { model.addAttribute("friends",frnd);
		 * model.addAttribute("title",frnd.getName()); }
		 */
		return "redirect:/user/showfriends/0";
	}
	
	//update frn
	@PostMapping("/opnupdtform/{fId}")
	public String Updtfrn(@PathVariable("fId") Integer fId,Model m)
	{
		m.addAttribute("title", "Update Friend");

		Friends frnd = this.friendsRepo.findById(fId).get();
		m.addAttribute("friends", frnd);
		return "users/updtform";
	}
	//processupdt
	
	@RequestMapping(value="/processupdt",method=RequestMethod.POST)
	public String processupdate(@ModelAttribute Friends friends,@RequestParam("fImage") MultipartFile updtfile,Model m,HttpSession ses,Principal p)
	{
		//System.out.println("Details"+friends.getName());
		try {
			
			//old frns detl to be fetch
		Friends oldfrn=	this.friendsRepo.findById(friends.getfId()).get();
			
			//img
			if(!updtfile.isEmpty())
			{
				//old photo to be delete
				File deleteFile=new ClassPathResource("static/img").getFile();
				File file1=new File(deleteFile,oldfrn.getImage());
				file1.delete();
				
				//updt new photo
				File saveF=new ClassPathResource("static/img").getFile();
				Path path=Paths.get(saveF.getAbsolutePath()+File.separator+updtfile.getOriginalFilename());
				Files.copy(updtfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); //static mthd
				friends.setImage(updtfile.getOriginalFilename());
				
			}
			else
			{
				friends.setImage(oldfrn.getImage());
			}
			
			
			
			User u=this.userRepository.getUserByUserEmail(p.getName());
			friends.setUser(u);
			this.friendsRepo.save(friends);
			ses.setAttribute("message", new Message("UPDATED SUCCESSFULLY","success"));
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		return "redirect:/user/"+friends.getfId()+"/friends";
	}

	//profie home dashboard 
	@RequestMapping("/setting")
	public String settng(Model model,Principal principal)
	{
		String email=principal.getName();
		System.out.println("Email : "+email);
		//get user using email
		User user=userRepository.getUserByUserEmail(email);
		System.out.println("USER"+user);
		model.addAttribute("user",user);
		model.addAttribute("title", "My  Settings");
	
		
		return "users/settings";
	}
	
	
}
