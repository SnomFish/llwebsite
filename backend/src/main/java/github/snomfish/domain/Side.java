package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Event;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class Side implements DeepCopyable<Side> {


    private List<Loomian> party;
    private int activeLoomianI;
    private ActiveLoomian activeLoomian;


    public Side(
        List<Loomian> party,
        int activeLoomianI
    ) {
        this.party = party;
        this.activeLoomianI = activeLoomianI;
        this.activeLoomian = new ActiveLoomian(party.get(activeLoomianI));
    }
    public Side(
        List<Loomian> party,
        int activeLoomianIndex,
        ActiveLoomian activeLoomian
    ) {
        this.party = party;
        this.activeLoomianI = activeLoomianIndex;
        this.activeLoomian = activeLoomian.deepCopy(); 
    }


    public Side deepCopy() {
        return new Side(
            deepCopyList(party),
            activeLoomianI,
            activeLoomian.deepCopy()
        );
    }


    public List<Branch<BattleContext>> dispatchEvent(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        outcomes = activeLoomian().dispatchEvent(context, event);

        return outcomes;
    }


    // getter
    public List<Loomian> getParty() {return party;}
    public int activeLoomianI() {return activeLoomianI;}
    public ActiveLoomian activeLoomian() {return activeLoomian;}


    // setter
    public void setParty(List<Loomian> party) {
        this.party = party;
    }
    public void setLoomian(int index, Loomian loomian) {
        this.party.set(index, loomian);
    }
    public void setActiveLoomianI(int activeLoomianI) {
        this.activeLoomianI = activeLoomianI;
    }
    public void setActiveLoomian(ActiveLoomian activeLoomian) {
        this.activeLoomian = activeLoomian;
    }
}
