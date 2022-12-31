package com.world_cup.reservation.service;

import com.world_cup.reservation.models.CreateStadiumForm;
import com.world_cup.reservation.models.Stadium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StadiumService {


    Page<Stadium> getAllStaduims(Pageable pageable);
    Stadium getStadiumById(Long stadiumId);

    Stadium saveNewStadium(CreateStadiumForm stadiumForm);

    Stadium editStadium(Stadium stadium);
}
