package com.world_cup.reservation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.world_cup.reservation.dao.MatchRepository;
import com.world_cup.reservation.models.CreateMatchForm;
import com.world_cup.reservation.models.Match;
import jdk.jfr.DataAmount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    @Override
    public Page<Match> getAllMatches(Pageable pageable) {
        return matchRepository.findAll(pageable);
    }

    @Override
    public Match getMatchById(Long matchId) {
        Optional<Match> match = matchRepository.findById(matchId);
        return match.isPresent() ? match.get() : null;

    }

    @Override
    public Match saveNewMatch(CreateMatchForm createMatchForm) {
        ObjectMapper mapper = new ObjectMapper();
        Match match = mapper.convertValue(createMatchForm , Match.class);
        match.setCreated_at(new Date());
        return matchRepository.save(match);
    }

    @Override
    public Match editMatch(Match match) {
        Optional<Match> expectedMatch = matchRepository.findById(match.getId());
        if(expectedMatch.isPresent()){
            match.setUpdated_at(new Date());
            return matchRepository.save(match);
        }
        return null;
    }
}
