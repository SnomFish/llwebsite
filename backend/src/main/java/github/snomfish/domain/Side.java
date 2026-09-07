package github.snomfish.domain;

import java.util.List;

public class Side {


    private List<Loomian> party;
    private ActiveLoomian activeLoomian;


    public Side() {
        setActiveLoomian(0);
    }


    // getter
    public List<Loomian> getParty() {return party;}
    public ActiveLoomian activeLoomian() {return activeLoomian;}


    // setter
    public void setParty(List<Loomian> party) {
        this.party = party;
    }
    public void setActiveLoomian(int activeLoomianI) {
        this.activeLoomian = new ActiveLoomian(party.get(activeLoomianI));
    }
}
