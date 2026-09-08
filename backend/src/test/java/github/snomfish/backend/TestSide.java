package github.snomfish.backend;

import java.util.List;

import github.snomfish.domain.Side;

public class TestSide {
    

    // side for tests
    public static Side testSide() {
        return new Side(
            List.of(TestLoomian.testLoomian1(), TestLoomian.testLoomian2()),
            0
        );
    }
}
