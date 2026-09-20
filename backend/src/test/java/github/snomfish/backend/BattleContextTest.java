package github.snomfish.backend;

import github.snomfish.domain.SideId;
import github.snomfish.functionality.context.BattleContext;

public class BattleContextTest {
    

    public static BattleContext createBattleContext() {
        return new BattleContext(
            GamestateTest.createGamestate(),
            SideId.PLAYER
        );
    }
}
