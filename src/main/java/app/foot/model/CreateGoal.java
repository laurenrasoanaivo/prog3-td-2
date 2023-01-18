package app.foot.model;

import app.foot.repository.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateGoal {
    private PlayerEntity player;
    private Integer scoreTime;
    private Boolean isOG;
}
