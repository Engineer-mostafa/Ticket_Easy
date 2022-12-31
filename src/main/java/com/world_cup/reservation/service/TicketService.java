package com.world_cup.reservation.service;

import com.world_cup.reservation.models.LocationInStadium;
import com.world_cup.reservation.models.Ticket;
import com.world_cup.reservation.models.TicketId;

import java.util.List;

public interface TicketService {

    List<Ticket> getTicketsByMatchId(Long matchId);


    Ticket reserveSeatLocation(LocationInStadium locationInStadium , TicketId ticketId);

    boolean deleteTicketById(Long ticketId);
}
