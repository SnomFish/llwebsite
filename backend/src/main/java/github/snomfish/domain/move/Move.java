package github.snomfish.domain.move;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.move.tags.Tags;
import github.snomfish.domain.type.TypeChart;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.branch.BranchUtil.*;

public class Move implements DeepCopyable<Move> {
    

    private MoveId id;
    private String name;
    private ICondition condition;
    private TypeId type;
    private CategoryId category;
    private INumber energyCost;
    private INumber priority;

    private IMove beforeHit;
    private IMove onHit;
    private IMove afterHit;
    private IMove onMiss;

    private Tags tags;
    private TypeChart typeChart;

    private double damage;
    private double accuracyModifier;
    private double damageModifier;
    private double typeModifier;
    private double healthDrainModifier;
    private double energyDrainModifier;


    public Move(
        MoveId id,
        String name,
        ICondition condition,
        TypeId type,
        CategoryId category,
        INumber energyCost,
        INumber priority,

        IMove beforeHit,
        IMove onHit,
        IMove afterHit,
        IMove onMiss,

        Tags tags
    ) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.type = type;
        this.category = category;
        this.energyCost = energyCost;
        this.priority = priority;

        this.beforeHit = beforeHit;
        this.onHit = onHit;
        this.afterHit = afterHit;
        this.onMiss = onMiss;

        this.tags = tags;
        this.typeChart = TypeChart.get();

        this.accuracyModifier = 1.0;
        this.damage = 0.0;
        this.damageModifier = 1.0;
        this.typeModifier = 1.0;
        this.healthDrainModifier = 1.0;
        this.energyDrainModifier = 1.0;
    }

    public Move(Move move) {
        this.id = move.id;
        this.name = move.name;
        this.condition = move.condition.deepCopy();
        this.type = move.type;
        this.category = move.category;
        this.energyCost = move.energyCost.deepCopy();
        this.priority = move.priority.deepCopy();

        this.beforeHit = move.beforeHit.deepCopy();
        this.onHit = move.onHit.deepCopy();
        this.afterHit = move.afterHit.deepCopy();
        this.onMiss = move.onMiss.deepCopy();

        this.tags = move.tags.deepCopy();
        this.typeChart = typeChart.deepCopy();

        this.accuracyModifier = move.accuracyModifier;
        this.damage = move.damage;
        this.damageModifier = move.damageModifier;
        this.typeModifier = move.typeModifier;
        this.healthDrainModifier = move.healthDrainModifier;
        this.energyDrainModifier = move.energyDrainModifier;
    }
    @Override 
    public Move deepCopy() {
        return new Move(this);
    }


    // getter
    public MoveId id() {return id;}
    public String name() {return name;}
    public ICondition condition() {return condition;}
    public TypeId type() {return type;}
    public CategoryId category() {return category;}
    public INumber energyCost() {return energyCost;}
    public INumber priority() {return priority;}

    public IMove beforeHit() {return beforeHit;}
    public IMove onHit() {return onHit;}
    public IMove afterHit() {return afterHit;}
    public IMove onMiss() {return onMiss;}

    public Tags tags() {return tags;}
    public TypeChart typeChart() {return typeChart;}

    public double accuracyModifier() {return accuracyModifier;}
    public double damage() {return damage;}
    public double damageModifier() {return damageModifier;}
    public double typeModifier() {return typeModifier;}
    public double healthDrainModifier() {return healthDrainModifier;}
    public double energyDrainModifier() {return energyDrainModifier;} 


    // setter
    public void setMoveId(MoveId moveId) {this.id = moveId;}
    public void setName(String name) {this.name = name;}
    public void setCondition(ICondition condition) {this.condition = condition;}
    public void setType(TypeId type) {this.type = type;}
    public void setCategory(CategoryId category) {this.category = category;}
    public void setEnergyCost(INumber energyCost) {this.energyCost = energyCost;}
    public void setPriority(INumber priority) {this.priority = priority;}

    public void setBeforeHit(IMove beforeHit) {this.beforeHit = beforeHit;}
    public void setOnHit(IMove onHit) {this.onHit = onHit;}
    public void setAfterHit(IMove afterHit) {this.afterHit = afterHit;}
    public void setOnMiss(IMove onMiss) {this.onMiss = onMiss;}

    public void setTags(Tags tags) {this.tags = tags;}
    public void setTypeChart(TypeChart typeChart) {this.typeChart = typeChart;}

    public void setAccuracyModifier(double accuracyModifier) {this.accuracyModifier = accuracyModifier;}
    public void setDamage(double damage) {this.damage = damage;}
    public void setDamageModifier(double damageModifier) {this.damageModifier = damageModifier;}
    public void setTypeModifier(double typeModifier) {this.typeModifier = typeModifier;}
    public void setHealthDrainModifier(double healthDrainModifier) {this.healthDrainModifier = healthDrainModifier;}
    public void setEnergyDrainModifier(double energyDrainModifier) {this.energyDrainModifier = energyDrainModifier;}


    // this bich does not think about energy cost
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> beforeHitOutcomes = beforeHit.execute(context);
        List<Branch<BattleContext>> moveOutcomes = new ArrayList<>();

        for (Branch<Boolean> conditionBranch : condition.execute(context)) {
            if (conditionBranch.value()) {
                moveOutcomes = applyProbability(
                    flatMap(beforeHitOutcomes, c -> onHit.execute(c)), conditionBranch);
            } else {
                moveOutcomes = applyProbability(
                    flatMap(beforeHitOutcomes, c -> onMiss.execute(c)), conditionBranch);
            }
        }

        return flatMap(moveOutcomes, c -> afterHit.execute(c));
    }
}
