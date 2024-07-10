package com.example.Capstone_10.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Capstone_10.Entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	public boolean existsByMail(String mail);

	public UserEntity findByMail(String mail); 

	
}
