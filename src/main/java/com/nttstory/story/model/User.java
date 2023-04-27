package com.nttstory.story.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    @NotNull
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    private String address;
    private String phone;
    private Date birthDay;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private Media avatar;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    private UserStatusEnum status = UserStatusEnum.ACTIVE;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdDate;
    @Column(nullable = false)
    @CreationTimestamp
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
