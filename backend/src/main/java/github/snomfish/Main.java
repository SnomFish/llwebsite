package github.snomfish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		// tests if enums can be parsed into the new Value system (they can)
		/*Loomian embit = new Loomian(
			SpeciesId.EMBIT,
			List.of(
				MoveId.PARASITIZE
			),
			new AbilityInstance(AbilityId.AWAKENING),
			ItemId.NO_ITEM,
			StatusId.NO_STATUS,
			new Stats(),
			new Stats(),
			new Stats()
		);
		Side side = new Side(List.of(embit), 0);
		Gamestate gamestate = new Gamestate(side, side);
		BattleContext context = new BattleContext(gamestate, SideId.PLAYER);
		context.user().activeLoomian().setAction(new MoveAction(0, MoveRegistry.get(MoveId.PARASITIZE)));

		Boolean test = false;
		System.out.println("control: " + test.toString());
		Value<Boolean> value = Value.USER().MOVE().MOVE_TAG(MoveTagId.EXPLOSIVE);
		test = value.get(context);
		System.out.println("test: " + test.toString());
		*/

		SpringApplication.run(Main.class, args);
	}

}
