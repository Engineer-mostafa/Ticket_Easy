package com.world_cup.reservation.controllers;

import com.world_cup.reservation.models.*;
import com.world_cup.reservation.service.MatchServiceImpl;
import com.world_cup.reservation.service.TicketServiceImpl;
import com.world_cup.reservation.service.UserServiceImpl;
import com.world_cup.reservation.utils.ExtractJWT;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


// we should add is match tickets is full (we can know it by get all tickets of match and see if the ticket < total seats)

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketServiceImpl ticketService;
    private final ExtractJWT jwt;
    private final MatchServiceImpl matchService;
    private final UserServiceImpl userService;


    @GetMapping("/{matchId}")
    public ResponseEntity<?> getAllReservedSeats(@PathVariable(value = "matchId") Long matchId){
        Map<String, Object> response = new HashMap<>();

        try{
            Match match = matchService.getMatchById(matchId);
            if(match !=null){
                List<Ticket> allTicketsInMatch = ticketService.getTicketsByMatchId(matchId);
                if( allTicketsInMatch != null){
                    Map<Integer, Stack> seats = new HashMap<>();
                    for(int i =0; i < allTicketsInMatch.size();i++){
                        int key = allTicketsInMatch.get(i).getSeat_row();
                        int value = allTicketsInMatch.get(i).getSeat_number();
                        if(seats.get(key) == null){
                            seats.put(key, new Stack<>());
                        }
                        seats.get(key).push(value);
                    }
                    response.put("response" , seats);
                    response.put("message" , "these are all reserved seats");
                    response.put("status" , "200");
                    return new ResponseEntity<>(response, HttpStatus.OK);

                }

                response.put("message" , "all seats are free for now");
                response.put("status" , "200");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }
            response.put("message" , "there is no match with this id");
            response.put("status" , "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);


        }catch (Exception e){
            response.put("message" , e.getMessage());
            response.put("status" , "401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }


    }



    @PostMapping("/secure/reserve/{matchId}")
    public ResponseEntity<?> reserveMatch(@RequestHeader(value = "Authorization") String access_token,
                                          @PathVariable(value = "matchId") Long matchId,
                                          @RequestBody LocationInStadium locationInStadium) {
        Map<String, Object> response = new HashMap<>();

        try {
            User fan = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
            TicketId ticketId = new TicketId();
            if (fan != null && fan.getRole() == 2 && fan.getEmail_verified_at() != null) {
                ticketId.setUser(fan);
                Match match = matchService.getMatchById(matchId);
                if (match != null) {
                    ticketId.setMatch(match);
                    if (match.getStadium().getNumber_of_rows_in_vip() <= locationInStadium.getRowNumber()
                            && match.getStadium().getNumber_seats_per_row() <= locationInStadium.getNumberInRow()) {
                        Ticket ticket = ticketService.reserveSeatLocation(locationInStadium , ticketId);

                        if (ticket == null) {
                            response.put("message", "sorry this place is already reserved");
                            response.put("status", "200");
                            return new ResponseEntity<>(response, HttpStatus.OK);
                        }
                        response.put("message", "reserved successfully");
                        response.put("status", "200");
                        response.put("response", ticket);
                        return new ResponseEntity<>(response, HttpStatus.OK);

                    }
                    response.put("message", "sorry this place isn't in our stadium");
                    response.put("status", "400");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

                }
                response.put("message", "sorry no match with this id");
                response.put("status", "404");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            }

            response.put("message", "you are not one of our fan users or your email wasn't verified yet");
            response.put("status", "404");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", "409");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }




    @DeleteMapping("/secure/delete/{ticketId}")
    public ResponseEntity<?> cancelReservation(@RequestHeader(value = "Authorization") String access_token,
                                        @PathVariable("ticketId") Long ticketId){


        Map<String, String> response = new HashMap<>();
        try{
            if(!jwt.checkIfTokenIsExpired(access_token)) {
                User fan = userService.verifyUser(jwt.userIdJWTExtraction(access_token));
                if(fan != null && fan.getRole() == 2 && fan.getEmail_verified_at() != null){
                    boolean isDeleted = ticketService.deleteTicketById(ticketId);
                    if(isDeleted){
                        response.put("message" , "Deleted Successfully");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                    response.put("message" , "this ticket wasn't found");
                    response.put("status" , "404");

                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

                }
                response.put("message" , "this fan wasn't found or not verified yet");
                response.put("status" , "404");
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


}
