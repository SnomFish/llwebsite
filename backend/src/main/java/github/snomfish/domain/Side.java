package github.snomfish.domain;

import java.util.List;

import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.modifiers.Modifiers;
import github.snomfish.domain.modifiers.SideModifierId;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.HookReport;
import github.snomfish.functionality.event.RuleReport;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class Side implements DeepCopyable<Side> {


    private List<Loomian> party;
    private int activeLoomianI;
    private ActiveLoomian activeLoomian;
    private Mechanics mechanics;
    private Modifiers<SideModifierId> modifiers;
    
    private boolean usedSoulBurst;
    private boolean usedSoulMove;


    public Side(
        List<Loomian> party,
        int activeLoomianI
    ) {
        this.party = party;
        this.activeLoomianI = activeLoomianI;
        this.activeLoomian = new ActiveLoomian(party.get(activeLoomianI));
        this.mechanics = new Mechanics();
        this.modifiers = new Modifiers<>();

        this.usedSoulBurst = false;
        this.usedSoulMove = true;
    }


    private Side(Side copy) {
        this.party = deepCopyList(copy.party);
        this.activeLoomianI = copy.activeLoomianI;
        this.activeLoomian = copy.activeLoomian.deepCopy();
        this.mechanics = copy.mechanics.deepCopy();
        this.modifiers = copy.modifiers.deepCopy();

        this.usedSoulBurst = copy.usedSoulBurst;
        this.usedSoulMove = copy.usedSoulMove;
    }
    public Side deepCopy() {
        return new Side(this);
    }


    // getter
    public List<Loomian> party() {return party;}
    public int activeLoomianI() {return activeLoomianI;}
    public ActiveLoomian activeLoomian() {return activeLoomian;}
    public Mechanics mechanics() {return mechanics;}
    public Modifiers<SideModifierId> modifiers() {return modifiers;}

    public boolean usedSoulBurst() {return usedSoulBurst;}
    public boolean usedSoulMove() {return usedSoulMove;}


    // setter
    public void setParty(List<Loomian> party) {this.party = party;}
    public void setLoomian(int index, Loomian loomian) {this.party.set(index, loomian);}
    public void setActiveLoomianI(int activeLoomianI) {this.activeLoomianI = activeLoomianI;}
    public void setActiveLoomian(ActiveLoomian activeLoomian) {this.activeLoomian = activeLoomian;}
    public void setMechanics(Mechanics mechanics) {this.mechanics = mechanics;}
    public void setModifiers(Modifiers<SideModifierId> modifiers) {this.modifiers = modifiers;}

    public void usedSoulBurst(boolean usedSoulBurst) {this.usedSoulBurst = usedSoulBurst;}
    public void usedSoulMove(boolean usedSoulMove) {this.usedSoulMove = usedSoulMove;}
    public boolean canSideSoulBurst() {
        return 
            !usedSoulBurst &&
            activeLoomian.species().canSoulBurst() &&
            activeLoomian.species().soulItemId() != ItemId.NO_ITEM && // can probably be removed as tests should prevent this from happening
            activeLoomian.species().soulSpeciesId() != SpeciesId.NO_SPECIES &&
            activeLoomian.species().soulItemId() == activeLoomian.itemId();
    }




    public void dispatchRule(RuleReport report, IRuleId ruleId) {
        activeLoomian.dispatchRule(report, ruleId);
        mechanics.dispatchRule(report, ruleId);
    }
    public void dispatchHook(HookReport report, IRuleId hookId) {
        activeLoomian.dispatchHook(report, hookId);
        mechanics.dispatchHook(report, hookId);
    }
    public double dispatchGate(BattleContext context, IGateId gateId) {
        double probability = 1;

        probability *= activeLoomian().dispatchGate(context, gateId);
        probability *= mechanics.dispatchGate(context, gateId);

        return probability;
    }


    public void incrementTurnNumber() {
        for (Loomian loomian : party) {
            loomian.incrementTurnNumber();
        }
        activeLoomian.incrementTurnNumber();
    }
}
