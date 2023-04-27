package com.nttstory.story.repository;

import com.nttstory.story.model.User;
import com.nttstory.story.projection.UsersProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE 1=1")
    List<UsersProjection> findAllUsers();

    Optional<User> findByEmail(String email);

}
