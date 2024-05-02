package com.world_cup.reservation.controllers;


import com.world_cup.reservation.models.*;
import com.world_cup.reservation.service.MatchServiceImpl;
import com.world_cup.reservation.service.TicketService;
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
@RequestMapping("/api/match")
@RequiredArgsConstructor
@Slf4j
public class MatchTableController {
    private final UserServiceImpl userService;
    private final ExtractJWT jwt;
    private final MatchServiceImpl matchService;
    private final TicketService ticketService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMatches(@RequestParam(required = false, defaultValue = "20") int size,
                                           @RequestParam(required = false, defaultValue = "0") int page) {

        Map<String, Object> response = new HashMap<>();
        try{
            List<Match> matches = IterableUtils.toList(matchService.getAllMatches(PageRequest.of(page, size)));

            if (matches != null) {
                response.put("message", "successful");
                response.put("status", "200");
                response.put("response", matches);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message", "you are not admin user");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            response.put("message" , e.getMessage());
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }


    @GetMapping("/{matchId}")
    public ResponseEntity<?> getMatchById( @PathVariable("matchId") Long matchId) {
        Map<String, Object> response = new HashMap<>();
        try{
            Match match = matchService.getMatchById(matchId);

            if(match != null){
                response.put("message", "successful");
                response.put("status", "200");
                response.put("response", match);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message", "Not found");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }catch (Exception e){
            response.put("message" , e.getMessage());
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }


    }



    @PostMapping("/create")
    public ResponseEntity<?> createNewMatch(@RequestHeader(value = "Authorization") String access_token,
                                           @RequestBody CreateMatchForm matchForm) {
        Map<String, Object> response = new HashMap<>();
        try{
            if(!jwt.checkIfTokenIsExpired(access_token)) {
                User manager = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
                if (manager != null && manager.getRole() == 1 && manager.getEmail_verified_at() != null) {
                    Match match = matchService.saveNewMatch(matchForm);
                    response.put("message", "successful");
                    response.put("status", "200");
                    response.put("response", match);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                response.put("message", "you are not manager user or no user with this id or you not verified yet");
                response.put("status", "404");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("message" , "access token is expired please refresh it");
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            response.put("message" , e.getMessage());
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }


    }


    @PutMapping("{matchId}/edit")
    public ResponseEntity<?> editStadium(@RequestHeader(value = "Authorization") String access_token,
                                         @RequestBody Match match) {
        Map<String, Object> response = new HashMap<>();
        try{
            if(!jwt.checkIfTokenIsExpired(access_token)) {
                User manager = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
                if (manager != null && manager.getRole() == 1 && manager.getEmail_verified_at() != null) {
                    Match editedmatch = matchService.editMatch(match);
                    if (editedmatch != null) {
                        response.put("message", "successful");
                        response.put("status", "200");
                        response.put("response", editedmatch);
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
        }catch (Exception e){
            response.put("message" , e.getMessage());
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }


    @GetMapping("{matchId}/isFull")
    public ResponseEntity<?> isFullReservations(@PathVariable("matchId") Long matchId){

        Map<String, Object> response = new HashMap<>();
        try{
            Match match = matchService.getMatchById(matchId);
            if(match != null){
                int numberOfTicketsInMatch = ticketService.getTicketsByMatchId(matchId).size();
                int numberOfChairsInStadium = match.getStadium().getNumber_of_rows_in_vip() *
                        match.getStadium().getNumber_seats_per_row();
                if(numberOfTicketsInMatch < numberOfChairsInStadium){

                    response.put("message", "successful");
                    response.put("status", "200");
                    response.put("response", false);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                response.put("message", "successful");
                response.put("status", "200");
                response.put("response", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message", "there is no match with this id");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            response.put("message" , e.getMessage());
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }

}

