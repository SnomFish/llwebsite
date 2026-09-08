package github.snomfish.backend;

import github.snomfish.domain.Gamestate;

public class TestGamestate {


    // gamestate used for testing
    public static Gamestate testGamestate() {
        return new Gamestate(
            TestSide.testSide(),
            TestSide.testSide()
        );
    }

}
