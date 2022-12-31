package com.world_cup.reservation.service;


import com.world_cup.reservation.dao.UserRepository;
import com.world_cup.reservation.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public User login(String email , String password) {

        User user = userRepository.findByEmail(email);
        System.out.println(user.getUsername());
        if(user != null){
//            if(passwordEncoder.matches(password , user.getPassword())){
                return user;
//            }
        }

        return null;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }



    @Override
    public boolean deleteUserById(Long userId) {
        if(userRepository.findById(userId) != null){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public User verifyUser(Long userId) {
        Optional<User> opUser = userRepository.findById(userId);


        if(opUser != null) {
            User user = opUser.get();
            user.setEmail_verified_at(new Date());
            return user;
        }

        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) return user;
        return null;
    }

    @Override
    public User updateMyData(User user) {
        user.setUpdated_at(new Date());
        return userRepository.save(user);
    }



    @Override
    public User getUserById(Long userId) {
        Optional<User> user =  userRepository.findById(userId);
        return user != null ? user.get() : null;
    }
}
