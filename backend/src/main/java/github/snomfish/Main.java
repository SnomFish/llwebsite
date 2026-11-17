package github.snomfish;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import github.snomfish.domain.*;
import github.snomfish.domain.ability.*;
import github.snomfish.domain.item.*;
import github.snomfish.domain.move.*;
import github.snomfish.domain.species.*;
import github.snomfish.domain.stats.*;
import github.snomfish.domain.status.*;
import github.snomfish.functionality.action.MoveAction;
import github.snomfish.functionality.context.BattleContext;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		Loomian embit = new Loomian(
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

		
		

		SpringApplication.run(Main.class, args);
	}

}
