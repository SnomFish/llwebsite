package github.snomfish.domain;

import java.util.List;

public class Side {


    private List<Loomian> party;
    private int activeLoomianI;


    public Side() {

    }


    // getter
    public List<Loomian> getParty() {
        return party;
    }
    public int getActiveLoomianI() {
        return activeLoomianI;
    }
    public Loomian getActiveLoomian() {
        return party.get(activeLoomianI);
    }


    // setter
    public void setParty(List<Loomian> party) {
        this.party = party;
    }
    public void setActiveLoomianI(int activeLoomianI) {
        this.activeLoomianI = activeLoomianI;
    }
}
