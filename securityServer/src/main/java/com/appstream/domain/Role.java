package com.appstream.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<AppUser> users = new ArrayList<>();

    public Role(int i, String name) {
        this.id = i;
        this.name = name;
    }
}
