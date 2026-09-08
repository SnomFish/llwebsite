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
    public ActiveLoomian activeLoomian() {return new ActiveLoomian(party.get(activeLoomianIndex), activeLoomianData);}


    // setter
    public Builder builder() {
        return new Builder(this);
    }
    public class Builder {

        private final Side copy;

        private Builder(Side original) {
            copy = original.deepCopy();
        }

        public Builder loomian(Loomian loomian, int index) {
            copy.party.set(index, loomian);
            return this;
        }
        public Builder activeLoomian(Loomian loomian) {
            copy.party.set(activeLoomianIndex, loomian);
            return this;
        }
        public Builder activeLoomianIndex(int activeLoomianIndex) {
            copy.activeLoomianIndex = activeLoomianIndex;
            return this;
        }
        public Builder activeLoomianData(ActiveLoomianData activeLoomianData) {
            copy.activeLoomianData = activeLoomianData;
            return this;
        }

        public Side build() {
            return copy;
        }
    }
}
