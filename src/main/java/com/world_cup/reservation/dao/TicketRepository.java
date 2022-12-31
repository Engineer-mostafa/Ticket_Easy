package com.world_cup.reservation.dao;

import com.world_cup.reservation.models.Ticket;
import com.world_cup.reservation.models.TicketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket , TicketId> {
    @Query("select t from Ticket t where t.id = ?1")
    Ticket findByIdEquals(Long id);

    @Query("select t from Ticket t where t.ticketId.match.id = ?1")
    List<Ticket> findByTicketId_Match_IdEquals(Long matchId);


}
