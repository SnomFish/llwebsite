package github.snomfish.backend.effects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import github.snomfish.backend.LoomianTest;
import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.move.MoveRegistry;
import github.snomfish.functionality.action.MoveAction;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class DealFormulaDamageTest {
    

    // only gonna check the damage rolls as that is what matters to me
    // realistically a battle context should only be made for a loomian that already has an action
    // but that would take a bit more effort to set up in my tests
    // tee hee
    private void testDamageRoll(
        BattleContext context, 
        MoveId moveId,
        int[] expected
    ) {

        MoveAction action = new MoveAction(MoveRegistry.get(moveId));
        context.user().activeLoomian().setAction(action);

        List<Branch<BattleContext>> outcomes = context.user().activeLoomian().action().move().onHit().execute(context);
        int[] actual = new int[16];

        for (int i = 0; i < 16; i ++) {
            Branch<BattleContext> outcomeBranch = outcomes.get(i);
            BattleContext outcome = outcomeBranch.value();
            int damage = outcome.user().activeLoomian().action().move().damage();

            actual[i] = damage;
        }

        assertArrayEquals(expected, actual);
    }


    @Test // assumes level is 5
    void damageRollRainbowBlast() {
        testDamageRoll(
            new BattleContext(
                new Gamestate(
                    new Side(List.of(LoomianTest.embit()), 0),
                    new Side(List.of(LoomianTest.embit()), 0) 
                ), SideId.PLAYER
            ),
            MoveId.RAINBOW_BLAST,
            new int[]{6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8}
        );
    }


    @Test
    void damageRollBlazeOfGlory() {
        testDamageRoll(
            new BattleContext(
                new Gamestate(
                    new Side(List.of(LoomianTest.embit()), 0),
                    new Side(List.of(LoomianTest.embit()), 0) 
                ), SideId.PLAYER
            ),
            MoveId.BLAZE_OF_GLORY,
            new int[]{6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8}
        );
    } 
}
