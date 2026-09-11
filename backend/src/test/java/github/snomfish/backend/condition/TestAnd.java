package github.snomfish.backend.condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.number.Constant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnd {
    

    
    private void testAnd(
        List<Double> probabilities,
        int optimisedBranchNum,
        List<Boolean> keys,
        List<Double> values
    ) {

        List<ICondition> conditionList = new ArrayList<>();
        for (Double probability : probabilities) {
            conditionList.add(new RandomCondition(new Constant(probability)));
        }
        ICondition condition = new And(conditionList);

        List<Branch<Boolean>> outcomes = condition.execute(null);

        assertEquals(outcomes.size(), optimisedBranchNum); // confirms the correct number of branches are created

        Map<Boolean, Double> branchMap = new HashMap<>();
        for (Branch<Boolean> branch : outcomes) {
            branchMap.put(branch.value(), branch.probability());
        }

        for (int i = 0; i < keys.size(); i ++) {
            assertEquals(branchMap.get(keys.get(i)), values.get(i));
        }
    } 

    //@Test
    void testAllCertainTrue() {
        testAnd(
            List.of(1.0, 1.0, 1.0),
            2,
            List.of(true, false),
            List.of(1.0, 0.0)
        );
    }

    //@Test
    void testOneCertainFalse() {
        testAnd(
            List.of(1.0, 1.0, 0.0),
            2,
            List.of(true, false),
            List.of(0.0, 1.0)
        );
    }

    //@Test
    void testAllCertainFalse() {
        testAnd(
            List.of(0.0, 0.0, 0.0),
            2,
            List.of(true, false),
            List.of(0.0, 1.0)
        );
    }

    //@Test
    void testTwoConditions() {
        testAnd(
            List.of(0.5, 0.5),
            2,
            List.of(true, false),
            List.of(
                0.5 * 0.5,
                1.0 - (0.5 * 0.5)
            )
        );
    }

    //@Test
    void testFourConditions() {
        testAnd(
            List.of(0.5, 0.5, 0.5, 0.5),
            2,
            List.of(true, false),
            List.of(
                0.5 * 0.5 * 0.5 * 0.5,
                1.0 - (0.5 * 0.5 * 0.5 * 0.5)
            )
        );
    }

    //@Test
    void testDifferentProbabilities() {
        testAnd(
            List.of(0.2, 0.3, 0.4),
            2,
            List.of(true, false),
            List.of(
                0.2 * 0.3 * 0.4,
                1.0 - (0.2 * 0.3 * 0.4)
            )
        );
    }

    //@Test
    void testVeryLowProbabilities() {
        testAnd(
            List.of(0.1, 0.1, 0.1),
            2,
            List.of(true, false),
            List.of(
                0.001,
                0.999
            )
        );
    }

    //@Test
    void testOneCondition() {
        testAnd(
            List.of(0.75),
            2,
            List.of(true, false),
            List.of(0.75, 0.25)
        );
    }

    //@Test
    void testOneAlwaysTrueCondition() {
        testAnd(
            List.of(1.0, 0.25),
            2,
            List.of(true, false),
            List.of(0.25, 0.75)
        );
    }

    //@Test
    void testOneAlwaysFalseCondition() {
        testAnd(
            List.of(0.0, 0.25),
            2,
            List.of(true, false),
            List.of(0.0, 1.0)
        );
    }

    //@Test
    void testProbabilitySumsToOne() {
        List<ICondition> conditionList = List.of(
            new RandomCondition(new Constant(0.25)),
            new RandomCondition(new Constant(0.5)),
            new RandomCondition(new Constant(0.75))
        );

        ICondition condition = new And(conditionList);

        List<Branch<Boolean>> outcomes = condition.execute(null);

        double probabilitySum = outcomes.stream()
            .mapToDouble(Branch::probability)
            .sum();

        assertEquals(1.0, probabilitySum);
    }
}
