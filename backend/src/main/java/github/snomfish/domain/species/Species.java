package github.snomfish.domain.species;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;

public class Species {


    private SpeciesId id;
    private String name;
    private List<TypeId> baseTypes;
    private List<AbilityId> baseAbilities;
    private Stats baseStats;
    private int bst;
    private double height;
    private double weight;
    private List<MoveId> learnset;
    private boolean canEvolve;

    private boolean canSoulBurst;
    private ItemId soulItemId;
    private MoveId soulMoveId;


    public Species() {
        this.id = SpeciesId.NO_SPECIES;
        this.name = "No Species";
        this.baseTypes = new ArrayList<>();
        this.baseAbilities = new ArrayList<>();
        this.baseStats = new Stats();
        this.bst = -1;
        this.height = -1;
        this.weight = -1;
        this.learnset = new ArrayList<>();
        this.canEvolve = false;
        this.canSoulBurst = false;
        this.soulItemId = ItemId.NO_ITEM;
        this.soulMoveId = MoveId.NO_MOVE;
    }


    // getters
    public SpeciesId id() {return id;}
    public String name() {return name;}
    public List<TypeId> baseTypes() {return baseTypes;}
    public List<AbilityId> baseAbilities() {return baseAbilities;}
    public Stats baseStats() {return baseStats;}
    public int bst() {return bst;}
    public double height() {return height;}
    public double weight() {return weight;}
    public List<MoveId> learnset() {return learnset;}
    public boolean canEvolve() {return canEvolve;}
    public boolean canSoulBurst() {return canSoulBurst;}
    public ItemId soulItemId() {return soulItemId;}
    public MoveId soulMoveId() {return soulMoveId;}


    // builder functions
    public static Species build() {
        return new Species();
    }
    public Species id(SpeciesId id) {
        this.id = id;
        return this;
    }
    public Species name(String name) {
        this.name = name;
        return this;
    }
    public Species baseTypes(List<TypeId> baseTypes) {
        this.baseTypes = baseTypes;
        return this;
    }
    public Species baseAbilities(List<AbilityId> baseAbilities) {
        this.baseAbilities = baseAbilities;
        return this;
    }
    public Species baseStats(int health, int energy, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.baseStats = new Stats(health, energy, attack, defense, specialAttack, specialDefense, speed);
        return this;
    }
    public Species bst(int bst) {
        this.bst = bst;
        return this;
    }
    public Species height(double height) {
        this.height = height;
        return this;
    }
    public Species weight(double weight) {
        this.weight = weight;
        return this;
    }
    public Species learnset(List<MoveId> learnset) {
        this.learnset = learnset;
        return this;
    }
    public Species canEvolve(boolean canEvolve) {
        this.canEvolve = canEvolve;
        return this;
    }
    public Species canSoulBurst(boolean canSoulBurst) {
        this.canSoulBurst = canSoulBurst;
        return this;
    }
    public Species soulItem(ItemId itemId) {
        this.soulItemId = itemId;
        return this;
    }
    public Species soulMove(MoveId moveId) {
        this.soulMoveId = moveId;
        return this;
    }
}


