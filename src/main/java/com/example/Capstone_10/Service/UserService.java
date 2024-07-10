package com.example.Capstone_10.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Capstone_10.Entity.UserEntity;
import com.example.Capstone_10.Repository.UserRepository;



@Service
public class UserService {
     @Autowired
     UserRepository userRepository;
     
     
	public UserEntity saveuser(UserEntity user) {
		return userRepository.save(user);
	}


	public  boolean authenticateUser(String mail, String password) {
        UserEntity user = userRepository.findByMail(mail);
        
        if (user != null) {
            
            return user.getPassword().equals(password);
        }
		return false;
	}
	
	

}
