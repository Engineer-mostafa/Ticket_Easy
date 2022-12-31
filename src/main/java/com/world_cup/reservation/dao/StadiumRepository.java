package com.world_cup.reservation.dao;

import com.world_cup.reservation.models.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium,Long> {
}
