package com.world_cup.reservation.models;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;


/**
 * @author mostafa magdy
 * @version 1.0 snapshot
 * @since 27/12/2022
 */

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "gender")
    private String gender;
    @Column(name = "role")
    private int role;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birth_date;
    @Column(name = "email_verified_at")
    @Temporal(TemporalType.DATE)
    private Date email_verified_at;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;


    @OneToMany(mappedBy = "ticketId.user")
    private List<Ticket> ticketList;







}
