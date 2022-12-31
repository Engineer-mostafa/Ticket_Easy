package com.world_cup.reservation.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.world_cup.reservation.dao.StadiumRepository;
import com.world_cup.reservation.dao.UserRepository;
import com.world_cup.reservation.models.CreateStadiumForm;
import com.world_cup.reservation.models.Stadium;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {


    private final StadiumRepository stadiumRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Stadium> getAllStaduims(Pageable pageable) {
        return stadiumRepository.findAll(pageable);
    }

    @Override
    public Stadium getStadiumById(Long stadiumId) {
        Optional<Stadium> stadium = stadiumRepository.findById(stadiumId);
        if(stadium != null) return stadium.get();

        return null;
    }

    @Override
    public Stadium saveNewStadium(CreateStadiumForm stadiumForm) {
        ObjectMapper mapper = new ObjectMapper();
        Stadium stadium = mapper.convertValue(stadiumForm , Stadium.class);
        stadium.setCreated_at(new Date());
        return stadiumRepository.save(stadium);
    }

    @Override
    public Stadium editStadium(Stadium stadium) {
        Optional<Stadium> expectedStadium = stadiumRepository.findById(stadium.getId());
        if(expectedStadium != null){
            stadium.setUpdated_at(new Date());
            return stadiumRepository.save(stadium);
        }
        return null;
    }
}
