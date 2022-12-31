package com.world_cup.reservation.dao;

import com.world_cup.reservation.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
