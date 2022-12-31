package com.world_cup.reservation.service;

import com.world_cup.reservation.models.CreateMatchForm;
import com.world_cup.reservation.models.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchService {

    Page<Match> getAllMatches(Pageable pageable);

    Match getMatchById(Long matchId);

    Match saveNewMatch(CreateMatchForm createMatchForm);

    Match editMatch(Match match);
}
