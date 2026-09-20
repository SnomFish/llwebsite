package github.snomfish.backend;

import github.snomfish.domain.Gamestate;

public class GamestateTest {
    

    public static Gamestate createGamestate() {
        return new Gamestate(
            SideTest.createSide(),
            SideTest.createSide()
        );
    }
}
