package github.snomfish.domain.side;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.Loomian;
import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.HookReport;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class Side implements DeepCopyable<Side> {


    private List<Loomian> party;
    private int activeLoomianI;
    private ActiveLoomian activeLoomian;
    private Mechanics mechanics;


    public Side(
        List<Loomian> party,
        int activeLoomianI
    ) {
        this.party = party;
        this.activeLoomianI = activeLoomianI;
        this.activeLoomian = new ActiveLoomian(party.get(activeLoomianI));
        this.mechanics = new Mechanics();
    }


    private Side(Side copy) {
        this.party = deepCopyList(copy.party);
        this.activeLoomianI = copy.activeLoomianI;
        this.activeLoomian = copy.activeLoomian.deepCopy();
        this.mechanics = copy.mechanics.deepCopy(); 
    }
    public Side deepCopy() {
        return new Side(this);
    }


    // getter
    public List<Loomian> getParty() {return party;}
    public int activeLoomianI() {return activeLoomianI;}
    public ActiveLoomian activeLoomian() {return activeLoomian;}
    public Mechanics mechanics() {return mechanics;}


    // setter
    public void setParty(List<Loomian> party) {this.party = party;}
    public void setLoomian(int index, Loomian loomian) {this.party.set(index, loomian);}
    public void setActiveLoomianI(int activeLoomianI) {this.activeLoomianI = activeLoomianI;}
    public void setActiveLoomian(ActiveLoomian activeLoomian) {this.activeLoomian = activeLoomian;}
    public void setMechanics(Mechanics mechanics) {this.mechanics = mechanics;}




    public List<Branch<BattleContext>> dispatchRule(BattleContext context, IRuleId event) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        outcomes = activeLoomian().dispatchRule(context, event);

        return outcomes;
    }
    public double dispatchGate(BattleContext context, IGateId gateId, double probability) {

        probability = activeLoomian().dispatchGate(context, gateId, probability);

        return probability;
    }
    public HookReport dispatchHook(HookReport report, BattleContext context, IRuleId event) {
        
        report = activeLoomian().dispatchHook(report, context, event);

        return report;
    }


    public void incrementTurnNumber() {
        for (Loomian loomian : party) {
            loomian.incrementTurnNumber();
        }
        activeLoomian.incrementTurnNumber();
    }
}
