package com.world_cup.reservation.service;

import com.world_cup.reservation.models.EditModelUser;
import com.world_cup.reservation.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public User login(String email , String passord);
    public Page<User> getAllUsers(Pageable pageable);
    public User saveNewUser(User user);
    public boolean deleteUserById(Long userId);
    public User verifyUser(Long userId);
    public User getUserByUsername(String username);
    public User updateMyData(User user , EditModelUser updated);
    public User getUserById(Long userId);

}
