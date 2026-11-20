package github.snomfish.backend.conditions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import github.snomfish.functionality.condition.variable.And;
import github.snomfish.functionality.condition.variable.RandomCondition;
import github.snomfish.functionality.number.Constant;

public class TestAnd {
	

	private static void test(
		And and,
		double expectedProbability
	) {
		double probability = and.execute(null);
		Assertions.assertEquals(probability, expectedProbability);
	}


	private static void randomConditions(double a, double b, double c) {
		test(new And(
			new RandomCondition(new Constant(a)),
			new RandomCondition(new Constant(b))
		), c
		);
	}


	@Test 
	void test1() {
		randomConditions(1, 1, 1);
		randomConditions(1, 0, 0);
		randomConditions(0, 1, 0);
		randomConditions(0, 0, 0);
		randomConditions(0.5, 0.5, 		0.5*0.5);
		randomConditions(0.2, 0.5, 		0.2*0.5);
		randomConditions(0.8, 0.5, 		0.8*0.5);
		randomConditions(0.25, 0.4, 	0.25*0.4);
		randomConditions(0.1, 0.1, 		0.1*0.1);
		randomConditions(0.9, 0.9, 		0.9*0.9);
		randomConditions(0.0, 0.5, 		0.0*0.5);
		randomConditions(1.0, 0.5, 		1.0*0.5);
		randomConditions(0.75, 0.25, 	0.75*0.25);
		randomConditions(0.33, 0.66, 	0.33*0.66);
		randomConditions(0.6, 0.7, 		0.6*0.7);
		randomConditions(0.01, 0.99, 	0.01*0.99);
	}
}
