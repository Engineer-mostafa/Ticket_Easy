package com.world_cup.reservation.models;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class EditModelUser {


    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String gender;
    private int role;
    private String nationality;
    @Temporal(TemporalType.DATE)
    private Date birth_date;
}
