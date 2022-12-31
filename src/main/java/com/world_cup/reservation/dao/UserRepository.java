package com.world_cup.reservation.dao;

import com.world_cup.reservation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(@RequestParam("email") String email);

    User findByUsername(@RequestParam("username") String username);
}
