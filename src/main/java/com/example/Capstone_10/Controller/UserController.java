package com.example.Capstone_10.Controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Capstone_10.Entity.UserEntity;
import com.example.Capstone_10.Repository.UserRepository;
import com.example.Capstone_10.Service.UserService;


@RestController
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@PostMapping("/register")
	public String saveUser(@RequestBody UserEntity user)
	{
		if(userRepository.existsByMail(user.getMail())==true)
		{
			return "User Already exists";
		}
		else {
			 userService.saveuser(user);
			 return "Created your account successfully";
		}
				
	}
	@PostMapping("/login")
    public String loginUser(@RequestBody UserEntity user) {
        
        boolean authenticated = userService.authenticateUser(user.getMail(), user.getPassword());
        
        if (authenticated) {
            return "Login successful";
        } else {
            return "Invalid email or password";
        }
    }
}
