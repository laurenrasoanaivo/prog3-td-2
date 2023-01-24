import app.foot.model.Player;
import app.foot.model.PlayerScorer;
import app.foot.repository.MatchRepository;
import app.foot.repository.PlayerRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.entity.TeamEntity;
import app.foot.repository.mapper.PlayerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//TODO-2: complete these tests
@Slf4j
public class PlayerMapperTest {
    MatchRepository matchRepository = mock(MatchRepository.class);
    PlayerRepository playerRepository = mock(PlayerRepository.class);
    PlayerMapper subject = new PlayerMapper(matchRepository, playerRepository);

    @Test
    void player_to_domain_ok() {
        TeamEntity teamEntity = TeamEntity.builder()
                .id(1)
                .name("Ghana")
                .build();
        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(1)
                .name("Paul")
                .guardian(false)
                .team(teamEntity)
                .build();
        Player expected = Player.builder()
                .id(1)
                .name("Paul")
                .isGuardian(false)
                .teamName(teamEntity.getName())
                .build();
        Player actual = subject.toDomain(playerEntity);
        assertEquals(expected, actual);
    }

    @Test
    void player_scorer_to_domain_ok() {
        PlayerScorer expected = PlayerScorer.builder()
                .isOwnGoal(false)
                .minute(13)
                .player(subject.toDomain(playerEntity))
                .build();
        PlayerScorer actual = subject.toDomain(playerScoreEntity);
        assertEquals(expected, actual);
    }

    @Test
    void player_scorer_to_entity_ok() {
        when(matchRepository.findById(1)).thenReturn(Optional.of(matchEntity));
        when(playerRepository.findById(1)).thenReturn(Optional.of(playerEntity));
        PlayerScorer playerScorer = PlayerScorer.builder()
                .player(subject.toDomain(playerEntity))
                .minute(13)
                .isOwnGoal(false)
                .build();
        PlayerScoreEntity actual = subject.toEntity(matchEntity.getId(), playerScorer);
        assertEquals(playerScoreEntity, actual);
    }

    TeamEntity teamBarea = TeamEntity.builder()
            .id(1)
            .name("Barea")
            .build();
    TeamEntity teamGhana = TeamEntity.builder()
            .id(1)
            .name("Ghana")
            .build();
    MatchEntity matchEntity = MatchEntity.builder()
            .id(1)
            .teamA(teamBarea)
            .teamB(teamGhana)
            .scorers(List.of())
            .stadium("stad")
            .build();
    PlayerEntity playerEntity = PlayerEntity.builder()
            .id(1)
            .name("Paul")
            .guardian(false)
            .team(teamBarea)
            .build();

    PlayerScoreEntity playerScoreEntity = PlayerScoreEntity.builder()
            .match(matchEntity)
            .player(playerEntity)
            .minute(13)
            .ownGoal(false)
            .build();

}
