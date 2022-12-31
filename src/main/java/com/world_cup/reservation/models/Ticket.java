package com.world_cup.reservation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {


    // primary key is composite of user_id and match_id

    @EmbeddedId
    private TicketId ticketId;

    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "seat_row")
    private int seat_row;
    @Column(name = "seat_number")
    private int seat_number;
    @Column(name = "reservation_date")
    @Temporal(TemporalType.DATE)
    private Date reservation_date;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;
}
