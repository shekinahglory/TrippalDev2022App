package com.appstream.domain;


import com.appstream.domain.generators.UserIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser {

    @Id
    @GeneratedValue(generator = "user_id_gen", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "user_id_gen",
            strategy = "com.appstream.domain.generators.UserIdGenerator",
            parameters = {
                    @Parameter(name = UserIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = UserIdGenerator.VALUE_PREFIX_PARAMETER, value = "US_2022_"),
                    @Parameter(name = UserIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
            }
    )
    private String id;
    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

}
