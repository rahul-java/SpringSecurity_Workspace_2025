package com.app.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.security.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

	Optional<Role> findByName(String name);
}
