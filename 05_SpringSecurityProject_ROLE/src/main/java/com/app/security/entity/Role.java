package com.app.security.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Role {

	@Id
	String roleId;
	String name;
	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	private List<User> users =new ArrayList<User>();
	
}
