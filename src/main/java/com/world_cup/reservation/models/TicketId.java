package com.world_cup.reservation.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class TicketId implements Serializable {


    private static final long serialVersionUID = -2539560811370624850L;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

}
