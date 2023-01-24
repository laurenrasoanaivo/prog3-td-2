import app.foot.controller.rest.Player;
import app.foot.controller.rest.PlayerScorer;
import app.foot.controller.validator.GoalValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//TODO-1: complete these tests
public class GoalValidatorTest {
    GoalValidator subject = new GoalValidator();

    @Test
    void accept_ok() {
        Player player = Player.builder()
                .id(1)
                .name("Paul")
                .isGuardian(false)
                .build();
        PlayerScorer playerScorer = PlayerScorer.builder()
                .player(player)
                .scoreTime(40)
                .isOG(false)
                .build();
        assertDoesNotThrow(() -> subject.accept(playerScorer));
    }

    //Mandatory attributes not provided : scoreTime
    @Test
    void accept_ko() {
        Player player = Player.builder()
                .id(1)
                .name("Paul")
                .isGuardian(false)
                .build();
        PlayerScorer playerScorer = PlayerScorer.builder()
                .player(player)
                .scoreTime(-40)
                .isOG(false)
                .build();
        assertThrows(Exception.class, () -> subject.accept(playerScorer));
    }

    @Test
    void when_guardian_throws_exception() {
        Player player = Player.builder()
                .id(1)
                .name("Paul")
                .isGuardian(true)
                .build();
        PlayerScorer playerScorer = PlayerScorer.builder()
                .player(player)
                .scoreTime(15)
                .isOG(false)
                .build();
        Exception exception = assertThrows(Exception.class, () -> subject.accept(playerScorer));
        assertEquals("Player#"+player.getId()+" is a guardian so they cannot score.", exception.getMessage());
    }

    @Test
    void when_score_time_greater_than_90_throws_exception() {
        Player player = Player.builder()
                .id(1)
                .name("Paul")
                .isGuardian(false)
                .build();
        PlayerScorer playerScorer = PlayerScorer.builder()
                .player(player)
                .scoreTime(91)
                .isOG(false)
                .build();
        Exception exception = assertThrows(Exception.class, () -> subject.accept(playerScorer));
        assertEquals("Player#"+player.getName()+" cannot score before after minute 90.", exception.getMessage());
    }

    @Test
    void when_score_time_less_than_0_throws_exception() {
        Player player = Player.builder()
                .id(1)
                .name("Paul")
                .isGuardian(false)
                .build();
        PlayerScorer playerScorer = PlayerScorer.builder()
                .player(player)
                .scoreTime(-1)
                .isOG(false)
                .build();
        Exception exception = assertThrows(Exception.class, () -> subject.accept(playerScorer));
        assertEquals("Player#"+player.getId()+" cannot score before before minute 0.", exception.getMessage());
    }
}
