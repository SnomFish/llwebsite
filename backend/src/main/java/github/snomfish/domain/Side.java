package github.snomfish.domain;

import java.util.List;

import github.snomfish.functionality.copy.DeepCopyable;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class Side implements DeepCopyable<Side> {


    private List<Loomian> party;
    private int activeLoomianIndex;
    private ActiveLoomianData activeLoomianData;


    public Side(
        List<Loomian> party,
        int activeLoomianIndex
    ) {
        this.party = party;
        this.activeLoomianIndex = activeLoomianIndex;
        this.activeLoomianData = new ActiveLoomianData(party.get(activeLoomianIndex));
    }
    public Side(
        List<Loomian> party,
        int activeLoomianIndex,
        ActiveLoomianData activeLoomianData
    ) {
        this.party = party;
        this.activeLoomianIndex = activeLoomianIndex;
        this.activeLoomianData = activeLoomianData.deepCopy(); 
    }


    public Side deepCopy() {
        return new Side(
            deepCopyList(party),
            activeLoomianIndex,
            activeLoomianData.deepCopy()
        );
    }


    // getter
    public List<Loomian> getParty() {return party;}
    public Loomian activeLoomian() {return party.get(activeLoomianIndex);}
    public int activeLoomianIndex() {return activeLoomianIndex;}
    public ActiveLoomianData activeLoomianData() {return activeLoomianData;}


    // setter
    public void setParty(List<Loomian> party) {
        this.party = party;
    }
    public void setLoomian(int index, Loomian loomian) {
        this.party.set(index, loomian);
    }
    public void setActiveLoomian(Loomian loomian) {
        this.party.set(activeLoomianIndex, loomian);
    }
    public void setActiveLoomianIndex(int activeLoomianIndex) {
        this.activeLoomianIndex = activeLoomianIndex;
    }
    public void setActiveLoomianData(ActiveLoomianData activeLoomianData) {
        this.activeLoomianData = activeLoomianData;
    }
}
