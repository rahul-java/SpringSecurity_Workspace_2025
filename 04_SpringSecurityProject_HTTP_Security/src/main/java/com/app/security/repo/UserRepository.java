package com.app.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.security.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUsername(String username);
}
