package com.world_cup.reservation.models;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CreateStadiumForm {

    private String name;
    private String shape;
    private int total_number_of_seats;
    private int number_of_rows_in_vip;
    private int number_seats_per_row;
}
