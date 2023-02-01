package app.foot.controller.rest.mapper;

import app.foot.controller.rest.Player;
import app.foot.controller.rest.UpdatePlayer;
import app.foot.repository.PlayerRepository;
import app.foot.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerRestMapper {
    private final PlayerRepository playerRepository;
    public Player toRest(app.foot.model.Player domain) {
        return Player.builder()
                .id(domain.getId())
                .name(domain.getName())
                .isGuardian(domain.getIsGuardian())
                .teamName(domain.getTeamName())
                .build();
    }

    public app.foot.model.Player toRest(UpdatePlayer toUpdate){
        return app.foot.model.Player.builder()
                .id(toUpdate.getId())
                .name(toUpdate.getName())
                .isGuardian(toUpdate.getIsGuardian())
                .teamName(playerRepository.findById(toUpdate.getId()).get().getTeam().getName())
                .build();
    }

    public app.foot.model.Player toDomain(Player rest) {
        return app.foot.model.Player.builder()
                .id(rest.getId())
                .isGuardian(rest.getIsGuardian())
                .name(rest.getName())
                .teamName(rest.getTeamName())
                .build();
    }

}
