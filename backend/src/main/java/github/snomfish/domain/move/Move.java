package github.snomfish.domain.move;

import java.util.ArrayList;
import java.util.List;

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

    private double damage;
    private double accuracyModifier;
    private double damageModifier;
    private double typeModifier;
    private TypeChart typeChart;


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
        IMove onMiss
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

        this.accuracyModifier = 1.0;
        this.damage = 0.0;
        this.damageModifier = 1.0;
        this.typeModifier = 1.0;
        this.typeChart = TypeChart.get();
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

        this.accuracyModifier = move.accuracyModifier;
        this.damage = move.damage;
        this.damageModifier = move.damageModifier;
        this.typeModifier = move.typeModifier;
        this.typeChart = typeChart.deepCopy();
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

    public double accuracyModifier() {return accuracyModifier;}
    public double damage() {return damage;}
    public double damageModifier() {return damageModifier;}
    public double typeModifier() {return typeModifier;}
    public TypeChart typeChart() {return typeChart;}


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

    public void setAccuracyModifier(double accuracyModifier) {this.accuracyModifier = accuracyModifier;}
    public void setDamage(double damage) {this.damage = damage;}
    public void setDamageModifier(double damageModifier) {this.damageModifier = damageModifier;}
    public void setTypeModifier(double typeModifier) {this.typeModifier = typeModifier;}
    public void setTypeChart(TypeChart typeChart) {this.typeChart = typeChart;}


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
