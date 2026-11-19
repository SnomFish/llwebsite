package github.snomfish.functionality._schematics;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.Subtract;
import github.snomfish.functionality.number.ValueConstant;
import github.snomfish.functionality.number.WeightedRange;

import static github.snomfish.functionality.context.ContextSideId.*;

public class Numbers {
	

	private Numbers() {}


	public static INumber WeightedTwoToFiveHits() {
		return WeightedRange.build()
			.add(new Constant(2), 1/3)
			.add(new Constant(3), 1/3)
			.add(new Constant(4), 1/6)
			.add(new Constant(5), 1/6);
	}


	// FRACTIONS OF MAX HEALTH
	private static INumber fractionOfMaxHealth(
		ContextSideId sideId,
		double fraction
	) {
		return new Multiply(
			new ValueConstant(Value.SIDE(sideId).HEALTH()),
			new Constant(fraction)
		);
	}
	public static INumber halfUserMaxHealth() 			{return fractionOfMaxHealth(USER, 1/2);}
	public static INumber thirdUserMaxHealth() 			{return fractionOfMaxHealth(USER, 1/3);}
	public static INumber quarterUserMaxHealth() 		{return fractionOfMaxHealth(USER, 1/4);}
	public static INumber fifthUserMaxHealth() 			{return fractionOfMaxHealth(USER, 1/5);}
	public static INumber sixthUserMaxHealth()			{return fractionOfMaxHealth(USER, 1/6);}
	public static INumber eighthUserMaxHealth() 		{return fractionOfMaxHealth(USER, 1/8);}
	public static INumber tenthUserMaxHealth() 			{return fractionOfMaxHealth(USER, 1/10);}
	public static INumber twelfthUserMaxHealth() 		{return fractionOfMaxHealth(USER, 1/12);}
	public static INumber sixteenthUserMaxHealth() 		{return fractionOfMaxHealth(USER, 1/16);}

	public static INumber halfTargetMaxHealth() 		{return fractionOfMaxHealth(TARGET, 1/2);}
	public static INumber thirdTargetMaxHealth() 		{return fractionOfMaxHealth(TARGET, 1/3);}
	public static INumber quarterTargetMaxHealth() 		{return fractionOfMaxHealth(TARGET, 1/4);}
	public static INumber fifthTargetMaxHealth() 		{return fractionOfMaxHealth(TARGET, 1/5);}
	public static INumber eighthTargetMaxHealth() 		{return fractionOfMaxHealth(TARGET, 1/8);}
	public static INumber tenthTargetMaxHealth() 		{return fractionOfMaxHealth(TARGET, 1/10);}
	public static INumber twelfthTargetMaxHealth() 		{return fractionOfMaxHealth(TARGET, 1/12);}
	public static INumber sixteenthTargetMaxHealth()	{return fractionOfMaxHealth(TARGET, 1/16);}


	// FRACTIONS OF MAX ENERGY
	private static INumber fractionOfMaxEnergy(
		ContextSideId sideId,
		double fraction
	) {
		return new Multiply(
			new ValueConstant(Value.SIDE(sideId).ENERGY()),
			new Constant(fraction)
		);
	}
	public static INumber halfUserMaxEnergy() 			{return fractionOfMaxEnergy(USER, 1/2);}
	public static INumber thirdUserMaxEnergy() 			{return fractionOfMaxEnergy(USER, 1/3);}
	public static INumber twoThirdsUserMaxEnergy()		{return fractionOfMaxEnergy(USER, 2/3);}
	public static INumber quarterUserMaxEnergy() 		{return fractionOfMaxEnergy(USER, 1/4);}
	public static INumber fifthUserMaxEnergy()	 		{return fractionOfMaxEnergy(USER, 1/5);}
	public static INumber eighthUserMaxEnergy() 		{return fractionOfMaxEnergy(USER, 1/8);}
	public static INumber tenthUserMaxEnergy()	 		{return fractionOfMaxEnergy(USER, 1/10);}
	public static INumber twelfthUserMaxEnergy() 		{return fractionOfMaxEnergy(USER, 1/12);}
	public static INumber sixteenthUserMaxEnergy() 		{return fractionOfMaxEnergy(USER, 1/16);}

	public static INumber halfTargetMaxEnergy() 		{return fractionOfMaxEnergy(TARGET, 1/2);}
	public static INumber thirdTargetMaxEnergy() 		{return fractionOfMaxEnergy(TARGET, 1/3);}
	public static INumber twoThirdsTargetMaxEnergy()	{return fractionOfMaxEnergy(TARGET, 2/3);}
	public static INumber quarterTargetMaxEnergy() 		{return fractionOfMaxEnergy(TARGET, 1/4);}
	public static INumber fifthTargetMaxEnergy() 		{return fractionOfMaxEnergy(TARGET, 1/5);}
	public static INumber eighthTargetMaxEnergy() 		{return fractionOfMaxEnergy(TARGET, 1/8);}
	public static INumber tenthTargetMaxEnergy() 		{return fractionOfMaxEnergy(TARGET, 1/10);}
	public static INumber twelfthTargetMaxEnergy() 		{return fractionOfMaxEnergy(TARGET, 1/12);}
	public static INumber sixteenthTargetMaxEnergy() 	{return fractionOfMaxEnergy(TARGET, 1/16);}


	// FRACTIONS OF MISSING HEALTH
	private static INumber fractionOfMissingHealth(
		ContextSideId sideId,
		double fraction
	) {
		return new Multiply(
			new Subtract(
				new ValueConstant(Value.SIDE(sideId).HEALTH()),
				new ValueConstant(Value.SIDE(sideId).CURRENT_HEALTH())
			),
			new Constant(fraction)
		);
	}
	public static INumber thirdOfUserMissingHealth()	{return fractionOfMissingHealth(USER, 1/3);}
}
