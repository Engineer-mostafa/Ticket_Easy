package com.world_cup.reservation.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@Table(name = "stadium")
@NoArgsConstructor
@AllArgsConstructor
public class Stadium {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "shape")
    private String shape;
    @Column(name = "total_number_of_seats")
    private int total_number_of_seats;
    @Column(name = "number_of_rows_in_vip")
    private int number_of_rows_in_vip;
    @Column(name = "number_seats_per_row")
    private int number_seats_per_row;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

}
