package github.snomfish.backend;

import java.util.List;

import github.snomfish.domain.Side;

public class SideTest {
    

    public static Side createSide() {
        return new Side(
            List.of(
                LoomianTest.embit(),
                LoomianTest.embit(),
                LoomianTest.embit(),
                LoomianTest.embit(),
                LoomianTest.embit(),
                LoomianTest.embit(),
                LoomianTest.embit()
            ),
            0
        );
    }
}
