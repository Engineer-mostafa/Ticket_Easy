package com.world_cup.reservation.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class CreateMatchForm {
    private int team_1;
    private int team_2;
    @Temporal(TemporalType.TIMESTAMP)
    private Date match_date;
    private String main_ref;
    private String lineman_1;
    private String lineman_2;

    private Stadium stadium;


}
