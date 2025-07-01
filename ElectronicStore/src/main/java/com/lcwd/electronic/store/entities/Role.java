package com.lcwd.electronic.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Role {

    @Id
    private String roleId;
    private String name;
    //ADMIN, NORMAL,
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}
