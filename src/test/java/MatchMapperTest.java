import app.foot.model.Match;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.mapper.MatchMapper;
import app.foot.repository.mapper.PlayerMapper;
import app.foot.repository.mapper.TeamMapper;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;

public class MatchMapperTest {
    TeamMapper teamMapper = mock(TeamMapper.class);
    PlayerMapper playerMapper = mock(PlayerMapper.class);
    MatchMapper matchMapper = new MatchMapper(teamMapper, playerMapper);

    @Test
    public void to_domain() {
        Match expected = Match.builder().build();
        Match actual = matchMapper.toDomain(MatchEntity.builder().build());
        assertEquals(expected, actual);
    }
}
