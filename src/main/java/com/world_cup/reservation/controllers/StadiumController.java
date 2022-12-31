package com.world_cup.reservation.controllers;


import com.world_cup.reservation.models.CreateStadiumForm;
import com.world_cup.reservation.models.Stadium;
import com.world_cup.reservation.models.User;
import com.world_cup.reservation.service.StadiumServiceImpl;
import com.world_cup.reservation.service.UserServiceImpl;
import com.world_cup.reservation.utils.ExtractJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/stadium")
@RequiredArgsConstructor
@Slf4j
public class StadiumController {

    private final StadiumServiceImpl stadiumService;
    private final UserServiceImpl userService;
    private final ExtractJWT jwt;

    @GetMapping("/all")
    public ResponseEntity<?> getAllStadiums(@RequestHeader(value = "Authorization") String access_token,
                                            @RequestParam(required = false, defaultValue = "20") int size,
                                            @RequestParam(required = false, defaultValue = "0") int page) {

        Map<String, Object> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(access_token)) {
            User manager = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
            if (manager != null && manager.getRole() == 1 && manager.getEmail_verified_at() != null) {
                List<Stadium> stadiums = IterableUtils.toList(stadiumService.getAllStaduims(PageRequest.of(page, size)));

                response.put("message", "successful");
                response.put("status", "200");
                response.put("response", stadiums);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.put("message", "you are not manager user or no user with this id or you not verified yet");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/{stadiumId}")
    public ResponseEntity<?> getStadiumById(@RequestHeader(value = "Authorization") String access_token,
                                            @PathVariable("stadiumId") Long stadiumId) {
        Map<String, Object> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(access_token)) {
            User manager = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
            if (manager != null && manager.getRole() == 1 && manager.getEmail_verified_at() != null) {
                Stadium stadium = stadiumService.getStadiumById(stadiumId);
                response.put("message", "successful");
                response.put("status", "200");
                response.put("response", stadium);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message", "you are not manager user or no user with this id or you not verified yet");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }


    @PostMapping("/create")
    public ResponseEntity<?> addNewStadium(@RequestHeader(value = "Authorization") String access_token,
                                           @RequestBody CreateStadiumForm stadiumForm) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(access_token);
        if(!jwt.checkIfTokenIsExpired(access_token)) {
            User manager = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
            if (manager != null && manager.getRole() == 1 && manager.getEmail_verified_at() != null) {
                Stadium stadium = stadiumService.saveNewStadium(stadiumForm);
                response.put("message", "successful");
                response.put("status", "200");
                response.put("response", stadium);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message", "you are not manager user or no user with this id or you not verified yet");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);


    }

    @PutMapping("/edit")
    public ResponseEntity<?> editStadium(@RequestHeader(value = "Authorization") String access_token,
                                         @RequestBody Stadium stadium) {
        Map<String, Object> response = new HashMap<>();
        if(!jwt.checkIfTokenIsExpired(access_token)) {
            User manager = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
            if (manager != null && manager.getRole() == 1 && manager.getEmail_verified_at() != null) {
                Stadium editedStadium = stadiumService.editStadium(stadium);
                if (editedStadium != null) {
                    response.put("message", "successful");
                    response.put("status", "200");
                    response.put("response", editedStadium);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                response.put("message", "this stadium wasn't found");
                response.put("status", "404");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            }
            response.put("message", "you are not manager user or no user with this id or you not verified yet");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        response.put("message" , "access token is expired please refresh it");
        response.put("status" , "401");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}