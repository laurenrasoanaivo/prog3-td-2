package app.foot.service;

import app.foot.model.CreateGoal;
import app.foot.model.Match;
import app.foot.repository.MatchRepository;
import app.foot.repository.PlayerRespository;
import app.foot.repository.PlayerScoreRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.mapper.MatchMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchService {
    private final MatchRepository repository;
    private final MatchMapper mapper;
    private final PlayerScoreRepository playerScoreRepository;
    private final PlayerRespository playerRespository;

    public List<Match> getMatches() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    public MatchEntity getById(Integer matchId){
        Optional<MatchEntity> match = repository.findById(matchId);
        if(match.isPresent())
            return match.get();
        else throw new RuntimeException("Resource match not found");
    }

    public Match createGoals(Integer matchId, List<CreateGoal> scorers){
        MatchEntity match = getById(matchId);
        List<PlayerScoreEntity> goals = scorers.stream().map(mapper::toDomain).toList();
        int err = 0;
        for(PlayerScoreEntity goal: goals){
            goal.setMatch(getById(matchId));
            goal.setPlayer(playerRespository.findById(goal.getPlayer().getId()).get());
            if(goal.getPlayer().getTeam() != match.getTeamA() && goal.getPlayer().getTeam() != match.getTeamB())
                err++;
            else if(goal.getPlayer().isGuardian())
                err++;
            else if(goal.getMinute()>90 || goal.getMinute()<0)
                err++;
        }
        if(err==0) {
            playerScoreRepository.saveAll(goals);
            return mapper.toDomain(match);
        }
        else throw new RuntimeException("create goals failed with "+err+" error" );
    }
}
