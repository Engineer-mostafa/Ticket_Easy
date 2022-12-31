package com.world_cup.reservation.service;

import com.world_cup.reservation.dao.TicketRepository;
import com.world_cup.reservation.models.LocationInStadium;
import com.world_cup.reservation.models.Ticket;
import com.world_cup.reservation.models.TicketId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    @Override
    public List<Ticket> getTicketsByMatchId(Long matchId) {
        List<Ticket> ticketsOfMatchId = ticketRepository.findByTicketId_Match_IdEquals(matchId);

        return ticketsOfMatchId;
    }

    @Override
    public Ticket reserveSeatLocation(LocationInStadium locationInStadium , TicketId ticketId) {
        try{
        Ticket ticket = new Ticket();
        TicketId ticketIdExpected = ticketId;
        ticket.setSeat_row(locationInStadium.getRowNumber());
        ticket.setSeat_number(locationInStadium.getNumberInRow());
        ticket.setCreated_at(new Date());
        ticket.setReservation_date(new Date());
        ticket.setTicketId(ticketIdExpected);

            return ticketRepository.save(ticket);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean deleteTicketById(Long ticketId) {
        try
        {
            Ticket ticket = ticketRepository.findByIdEquals(ticketId);

            if(ticket != null)
                return true;
            else
                return false;

        }
        catch (Exception e){
            return false;
        }

    }
}
