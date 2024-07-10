package com.example.Capstone_10.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Capstone_10.Entity.UrlEntity;


@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

	public UrlEntity findByLongUrl(String longUrl);

	public UrlEntity findByHashCode(String hashCode);

}

