package com.world_cup.reservation.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.world_cup.reservation.models.LoginFormUser;
import com.world_cup.reservation.models.SignupFormUser;
import com.world_cup.reservation.models.User;
import com.world_cup.reservation.service.UserServiceImpl;
import com.world_cup.reservation.utils.ExtractJWT;
import com.world_cup.reservation.utils.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowedHeaders = {"Access-Control-Allow-Headers," +
        "Access-Control-Allow-Origin," +
        "                Access-Control-Allow-Methods," +
        "                Authorization, Origin,Accept," +
        "                X-Requested-With," +
        "                Content-Type," +
        "                Access-Control-Request-Method," +
        "                Access-Control-Request-Headers"})
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl userService;
    private final ExtractJWT jwt;
    private final SecurityProperties secProperties;


    @GetMapping("/secure/users")
    public ResponseEntity<?> getUsers(@RequestHeader(value = "Authorization") String access_token,
                                      @RequestParam(required = false, defaultValue = "20") int size,
                                      @RequestParam(required = false, defaultValue = "0") int page ){
        Map<String, Object> response = new HashMap<>();

        try{
            User admin = userService.verifyUser(jwt.userIdJWTExtraction(access_token));


            if(!jwt.checkIfTokenIsExpired(access_token)) {
                if(admin != null && admin.getRole() == 0 && admin.getEmail_verified_at() != null){
                    List<User> users = IterableUtils.toList(userService.getAllUsers(PageRequest.of(page, size)));

                    response.put("message" , "successful");
                    response.put("status" , "200");
                    response.put("response", users);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                response.put("message" , "you are not admin user");
                response.put("status" , "404");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("message" , "access token is expired please refresh it");
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            response.put("message" , e.getMessage());
            response.put("status" , "401 UNAUTHORIZED");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }


    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignupFormUser signupFormUser , HttpServletRequest request){

        Map<String, String> response = new HashMap<>();
        signupFormUser.setNationality(signupFormUser.getNationality() == null? null : signupFormUser.getNationality());



        User user;
        ObjectMapper mapper = new ObjectMapper();
        user = mapper.convertValue(signupFormUser , User.class);
        user = userService.saveNewUser(user);

        String access_token = jwt.creatAccessToken(String.valueOf(user.getId()) , user.getUsername(),user.getRole() , false , request);
        String refresh_token = jwt.createRefreshToken(String.valueOf(user.getId()) , user.getUsername() , request);

        if(user != null ){
            response = jwt.getTokensAsJson(access_token , refresh_token);
            response.put("message", "successful");
            response.put("Bad Request" , "200");
            return new ResponseEntity<>(response, HttpStatus.OK);


        }

        response.put("message", "this user values are invalid check them again");
        response.put("Bad Request" , "400");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginFormUser lfUser , HttpServletRequest request){

        User user = userService.login(lfUser.getEmail() , lfUser.getPassword());
        Map<String, String> response = new HashMap<>();

        if(user != null){
            String access_token = jwt.creatAccessToken(String.valueOf(user.getId()) , user.getUsername() , user.getRole(), user.getEmail_verified_at()!=null ? true : false , request);
            String refresh_token = jwt.createRefreshToken(String.valueOf(user.getId()) , user.getUsername() , request);

            response = jwt.getTokensAsJson(access_token , refresh_token);
            response.put("message" , "successful");
            response.put("status" , "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("message" , "this user wasn't found");
        response.put("status" , "404");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



    @PutMapping("/secure/verify/user/{userId}")
    public ResponseEntity<?> verifyUser(
            @RequestHeader(value = "Authorization") String access_token,
            @PathVariable("userId") Long userId){

        Map<String, Object> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(access_token)) {

            User admin = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
            if (admin != null && admin.getRole() == 0 && admin.getEmail_verified_at() != null) {
                User user = userService.verifyUser(userId);

                if (user != null) {
                    response.put("message" , "successful");
                    response.put("status" , "200");
                    response.put("response" , user);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }

            response.put("message" , "you are not admin");
            response.put("status" , "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/secure/refreshAccessToken")
    public ResponseEntity<?> refreshAccessToken(
            @RequestHeader(value = "Authorization") String refreshToken ,
            HttpServletRequest request
            ){

        Map<String, String> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(refreshToken)) {
            String username = jwt.refreshAccessToken(refreshToken);
            User user = userService.getUserByUsername(username);
            String access_token = jwt.creatAccessToken(user.getId().toString() , user.getUsername() , user.getRole() , user.getEmail_verified_at() != null ? true : false , request);
            String rtoken = refreshToken.substring((secProperties.getBearer() + " ").length());

            response.put("access_token" , access_token);
            response.put("refresh_token" , rtoken);
            response.put("message" , "successful");
            response.put("status" , "200");

            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        response.put("message" , "refresh token also expired please login again");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);


    }

    @DeleteMapping("/secure/delete/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader(value = "Authorization") String access_token,
                                        @PathVariable("userId") Long userId){


        Map<String, String> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(access_token)) {
            User admin = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
            if(admin != null && admin.getRole() == 0 && admin.getEmail_verified_at() != null){
                boolean isDeleted = userService.deleteUserById(userId);
                if(isDeleted){
                    response.put("message" , "Deleted Successfully");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                response.put("message" , "this user wasn't found");
                response.put("status" , "404");

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            }
            response.put("message" , "this admin wasn't found");
            response.put("status" , "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }


    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getUserByUserName(@RequestHeader(value = "Authorization") String access_token,
                                               @PathVariable("username") String username){
        Map<String, Object> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(access_token)) {
            User profile = userService.getUserByUsername(username);
            if(profile != null){
                response.put("message" , "fetched  Successfully");
                response.put("response", profile);
                response.put("status" , "200");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("status" , "404");
            response.put("message" , "Not found this user");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<?> editMydata(@RequestHeader(value = "Authorization") String access_token,
                                        @RequestBody User user){
        Map<String, Object> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(access_token)) {
          User expectedUser = userService.verifyUser(user.getId());
          if(expectedUser != null){
              expectedUser = userService.updateMyData(user);
              response.put("message" , "fetched  Successfully");
              response.put("response", expectedUser);
              response.put("status" , "200");
              return new ResponseEntity<>(response, HttpStatus.OK);
          }
            response.put("status" , "404");
            response.put("message" , "Not found this user");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


}
