package github.snomfish.domain.move;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.move.modifiers.Modifiers;
import github.snomfish.domain.move.structures.IMove;
import github.snomfish.domain.move.structures.NoMove;
import github.snomfish.domain.stats.StatId;
import github.snomfish.domain.type.TypeChart;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.tags.Tags;

import static github.snomfish.functionality.branch.BranchUtil.*;

public class Move implements DeepCopyable<Move> {
    

    private MoveId id;
    private String name;
    private ICondition condition;
    private TypeId type;
    private CategoryId category;
    private INumber energyCost;
    private INumber priority;

    private IMove onTurnStart;
    private IMove beforeMove;
    private IMove onHit;
    private IMove afterMove;
    private IMove onMiss;

    private INumber basePower;
    private StatId attackingStatId;
    private StatId defendingStatId;
    private Tags<MoveTagId> tags;
    private Modifiers modifiers;
    private TypeChart typeChart;

    private int critChance;
    private int damage;

    private boolean hasCrit; // could be added to tags


    public Move() {
        this.id = 			MoveId.NO_MOVE;
        this.name = 		"No Move";
        this.condition = 	new NoCondition();
        this.type = 		TypeId.NULL;
        this.category = 	CategoryId.MELEE;
        this.energyCost = 	new Constant(0);
        this.priority = 	new Constant(0);

        this.onTurnStart =  new NoMove();
        this.beforeMove = 	new NoMove();
        this.onHit =        new NoMove();
        this.afterMove =    new NoMove();
        this.onMiss = 		new NoMove();

        this.basePower = 	new Constant(0);
        this.attackingStatId = StatId.MATTACK;
        this.defendingStatId = StatId.MDEFENSE;
        this.tags = 		new Tags<MoveTagId>();
        this.modifiers = 	new Modifiers();
        this.typeChart = 	TypeChart.get();

        this.critChance = 0;
        this.damage = 0;

        this.hasCrit = false;
    }

    public Move(Move move) {
        this.id = move.id;
        this.name = move.name;
        this.condition = move.condition.deepCopy();
        this.type = move.type;
        this.category = move.category;
        this.energyCost = move.energyCost.deepCopy();
        this.priority = move.priority.deepCopy();

        this.onTurnStart = move.onTurnStart.deepCopy();
        this.beforeMove = move.beforeMove.deepCopy();
        this.onHit = move.onHit.deepCopy();
        this.afterMove = move.afterMove.deepCopy();
        this.onMiss = move.onMiss.deepCopy();

        this.basePower = move.basePower.deepCopy();
        this.attackingStatId = move.attackingStatId;
        this.defendingStatId = move.defendingStatId;
        this.tags = move.tags.deepCopy();
        this.modifiers = move.modifiers.deepCopy();
        this.typeChart = move.typeChart.deepCopy();

        this.critChance = move.critChance;
        this.damage = move.damage;

        this.hasCrit = move.hasCrit;
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

    public IMove onTurnStart() {return onTurnStart;}
    public IMove beforeMove() {return beforeMove;}
    public IMove onHit() {return onHit;}
    public IMove afterMove() {return afterMove;}
    public IMove onMiss() {return onMiss;}

    public INumber basePower() {return basePower;}
    public StatId attackingStatId() {return attackingStatId;}
    public StatId defendingStatId() {return defendingStatId;}
    public Tags<MoveTagId> tags() {return tags;}
    public Modifiers modifiers() {return modifiers;}
    public TypeChart typeChart() {return typeChart;}

    public int critChance() {return critChance;}
    public int damage() {return damage;}

    public boolean hasCrit() {return hasCrit;}


    // setter
	public static Move build() {
		return new Move();
	}
	public Move id(MoveId id) {
		this.id = id;
		return this;
	}
    public Move moveId(MoveId moveId) {
		this.id = moveId;
		return this;
	}
    public Move name(String name) {
		this.name = name;
		return this;
	}
    public Move condition(ICondition condition) {
		this.condition = condition;
		return this;
	}
    public Move type(TypeId type) {
		this.type = type;
		return this;
	}
    public Move category(CategoryId category) {
		this.category = category;
		return this;
	}
    public Move energyCost(INumber energyCost) {
		this.energyCost = energyCost;
		return this;
	}
    public Move priority(INumber priority) {
		this.priority = priority;
		return this;
	}
	public Move onTurnStart(IMove onTurnStart) {
		this.onTurnStart = onTurnStart;
		return this;
	}
    public Move beforeMove(IMove beforeMove) {
		this.beforeMove = beforeMove;
		return this;
	}
    public Move onHit(IMove onHit) {
		this.onHit = onHit;
		return this;
	}
    public Move afterMove(IMove afterMove) {
		this.afterMove = afterMove;
		return this;
	}
    public Move onMiss(IMove onMiss) {
		this.onMiss = onMiss;
		return this;
	}
    public Move basePower(INumber basePower) {
		this.basePower = basePower;
		return this;
	}
    public Move attackingStat(StatId attackingStatId) {
		this.attackingStatId = attackingStatId;
		return this;
	}
    public Move defendingStat(StatId defendingStatId) {
		this.defendingStatId = defendingStatId;
		return this;
	}
    public Move tag(MoveTagId tag) {
		this.tags.set(tag, true);
		return this;
	}
    public Move modifiers(Modifiers modifiers) {
		this.modifiers = modifiers;
		return this;
	}
    public Move typeChart(TypeChart typeChart) {
		this.typeChart = typeChart;
		return this;
	}
    public Move critChance(int critChance) {
		this.critChance = critChance;
		return this;
	}
    public Move damage(int damage) {
		this.damage = damage;
		return this;
	}
    public Move hasCrit(boolean hasCrit) {
		this.hasCrit = hasCrit;
		return this;
	}




    // this bich does not think about energy cost
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> beforeMoveOutcomes = beforeMove.execute(context);
        List<Branch<BattleContext>> moveOutcomes = new ArrayList<>();

        Double conditionProbability = condition.execute(context);

        if (conditionProbability > 0) {
            moveOutcomes = applyProbability(
                flatMap(beforeMoveOutcomes, c -> onHit.execute(c)), 
                conditionProbability
            );
        } 
        if (conditionProbability < 1) {
            moveOutcomes = applyProbability(
                flatMap(beforeMoveOutcomes, c -> onMiss.execute(c)), 
                1-conditionProbability
            );
        }

        return flatMap(moveOutcomes, c -> afterMove.execute(c));
    }
}
