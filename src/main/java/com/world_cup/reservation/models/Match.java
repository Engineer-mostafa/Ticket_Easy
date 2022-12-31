package com.world_cup.reservation.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@Table(name = "match_tables")
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "team_1")
    private int team_1;
    @Column(name = "team_2")
    private int team_2;
    @Column(name = "match_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date match_date;
    @Column(name = "main_ref")
    private String main_ref;
    @Column(name = "lineman_1")
    private String lineman_1;
    @Column(name = "lineman_2")
    private String lineman_2;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;
}
