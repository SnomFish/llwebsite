package github.snomfish.domain.move;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.modifiers.Modifiers;
import github.snomfish.domain.modifiers.MoveModifierId;
import github.snomfish.domain.move.structures.IMove;
import github.snomfish.domain.move.structures.NoMove;
import github.snomfish.domain.stats.StatId;
import github.snomfish.domain.tags.MoveTagId;
import github.snomfish.domain.tags.Tags;
import github.snomfish.domain.type.TypeChart;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality._schematics.Effects;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.Comparator;
import github.snomfish.functionality.condition.GateCondition;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.ResetModifier;
import github.snomfish.functionality.effect.effects.SetValueIEffect;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.INumberValueConstant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.functionality.branch.BranchUtil.*;

public class Move implements DeepCopyable<Move> {
    

    private MoveId id;
    private String name;
    private TypeId type;
    private CategoryId category;
    private INumber energyCost;
    private INumber priority;

    private IMove onTurnStart;
    private IMove preMove;
    private IMove onMove;
    private IMove postMove;

    private INumber basePower;
    private StatId attackingStatId;
    private StatId defendingStatId;
    private Tags<MoveTagId> tags;
    private Modifiers<MoveModifierId> modifiers;
    private TypeChart typeChart;

    private int calcedEnergyCost;
    private int critChance;
    private int damage;

    private boolean canMoveFail;
    private boolean hasMoveFailed;
    private boolean hasCrit; // could be added to tags


    public Move() {
        this.id = 			MoveId.NO_MOVE;
        this.name = 		"No Move";
        this.type = 		TypeId.NULL;
        this.category = 	CategoryId.MELEE;
        this.energyCost = 	new Constant(0);
        this.priority = 	new Constant(0);

        this.onTurnStart =  new NoMove();
        this.preMove = 		new NoMove();
        this.onMove = 		new NoMove();
        this.postMove =    	new NoMove();

        this.basePower = 	new Constant(0);
        this.attackingStatId = StatId.MATTACK;
        this.defendingStatId = StatId.MDEFENSE;
        this.tags = 		new Tags<MoveTagId>();
        this.modifiers = 	new Modifiers<>();
        this.typeChart = 	TypeChart.get();

        this.calcedEnergyCost = 0;
        this.critChance = 0;
        this.damage = 0;

        this.canMoveFail = false;
        this.hasMoveFailed = false;
        this.hasCrit = false;
    }

    public Move(Move copy) {
        this.id = 				copy.id;
        this.name = 			copy.name;
        this.type = 			copy.type;
        this.category = 		copy.category;
        this.energyCost = 		copy.energyCost.deepCopy();
        this.priority = 		copy.priority.deepCopy();

        this.onTurnStart =      copy.onTurnStart.deepCopy();
        this.preMove =          copy.preMove.deepCopy();
        this.onMove =           copy.onMove.deepCopy();
        this.postMove =         copy.postMove.deepCopy();

        this.basePower = 		copy.basePower.deepCopy();
        this.attackingStatId =  copy.attackingStatId;
        this.defendingStatId =  copy.defendingStatId;
        this.tags = 			copy.tags.deepCopy();
        this.modifiers = 		copy.modifiers.deepCopy();
        this.typeChart = 		copy.typeChart.deepCopy();

        this.calcedEnergyCost = copy.calcedEnergyCost;
        this.critChance = 		copy.critChance;
        this.damage = 			copy.damage;

        this.canMoveFail = 		copy.canMoveFail;
        this.hasMoveFailed = 	copy.hasMoveFailed;
        this.hasCrit = 			copy.hasCrit;
    }
    @Override 
    public Move deepCopy() {
        return new Move(this);
    }


    // getter
    public MoveId id() {return id;}
    public String name() {return name;}
    public TypeId type() {return type;}
    public CategoryId category() {return category;}
    public INumber energyCost() {return energyCost;}
    public INumber priority() {return priority;}

    public IMove onTurnStart() {return onTurnStart;}
    public IMove preMove() {return preMove;}
    public IMove onMove() {return onMove;}
    public IMove postMove() {return postMove;}

    public INumber basePower() {return basePower;}
    public StatId attackingStatId() {return attackingStatId;}
    public StatId defendingStatId() {return defendingStatId;}
    public Tags<MoveTagId> tags() {return tags;}
    public Modifiers<MoveModifierId> modifiers() {return modifiers;}
    public TypeChart typeChart() {return typeChart;}

    public int calcedEnergyCost() {return calcedEnergyCost;}
    public int critChance() {return critChance;}
    public int damage() {return damage;}

    public boolean canMoveFail() {return canMoveFail;}
    public boolean hasMoveFailed() {return hasMoveFailed;}
    public boolean hasCrit() {return hasCrit;}


    // setter
	public static Move build() {
		return new Move();
	}
	public Move id(MoveId id) {this.id = id; return this;}
    public Move moveId(MoveId moveId) {this.id = moveId; return this;}
    public Move name(String name) {this.name = name; return this;}
    public Move type(TypeId type) {this.type = type; return this;}
    public Move category(CategoryId category) {this.category = category; return this;}
    public Move energyCost(INumber energyCost) {this.energyCost = energyCost; return this;}
    public Move priority(INumber priority) {this.priority = priority; return this;}

	public Move onTurnStart(IMove onTurnStart) {this.onTurnStart = onTurnStart; return this;}
    public Move preMove(IMove preMove) {this.preMove = preMove; return this;}
    public Move onMove(IMove onHit) {this.onMove = onHit; return this;}
    public Move postMove(IMove postMove) {this.postMove = postMove; return this;}

    public Move basePower(INumber basePower) {this.basePower = basePower; return this;}
    public Move attackingStat(StatId attackingStatId) {this.attackingStatId = attackingStatId; return this;}
    public Move defendingStat(StatId defendingStatId) {this.defendingStatId = defendingStatId; return this;}
    public Move tag(MoveTagId tag) {this.tags.set(tag, true); return this;}
    public Move modifiers(Modifiers<MoveModifierId> modifiers) {this.modifiers = modifiers; return this;}
    public Move typeChart(TypeChart typeChart) {this.typeChart = typeChart; return this;}

    public Move calcedEnergyCost(int calcedEnergyCost) {this.calcedEnergyCost = calcedEnergyCost; return this;}
    public Move critChance(int critChance) {this.critChance = critChance; return this;}
    public Move damage(int damage) {this.damage = damage; return this;}

    public void setCanMoveFail(boolean canMoveFail) {this.canMoveFail = canMoveFail;}
    public void setHasMoveFailed(boolean hasMoveFailed) {this.hasMoveFailed = hasMoveFailed;}
    public void hasCrit(boolean hasCrit) {this.hasCrit = hasCrit;}




    // this bich does not think about energy cost
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        List<ICondition> conditions = List.of(new GateCondition(GateForAllId.CAN_USE_MOVE, ContextSideId.USER));

        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.PRE_MOVE, ContextSideId.USER));
        outcomes = resolveConditions(context, conditions, this::executeEnergyCost); // can use the move

		return outcomes;
    }


    public List<Branch<BattleContext>> executeEnergyCost(BattleContext context) {
        List<Branch<BattleContext>> outcomes = wrap(context);
        IEffect calcEnergyCost = new SetValueIEffect<>(
            Value.USER().MOVE().CALCED_ENERGY_COST(),
            new Multiply(
                new INumberValueConstant(Value.USER().MOVE().ENERGY_COST()),
                new ValueConstant(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST))
            )
        );
        List<ICondition> conditions = List.of( // current energy calc is just the energy cost * energy cost modifier
            Comparator.greaterThanEqualTo()
                .left(new ValueConstant(Value.USER().CURRENT_ENERGY()))
                .right(new ValueConstant(Value.USER().CALCED_ENERGY_COST()))
        );

        // resets the energy cost right before the pre energy calc effect
        outcomes = flatMap(outcomes, c -> ResetModifier.userMove(MoveModifierId.ENERGY_COST).execute(c));
        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.PRE_ENERGY_CALC, ContextSideId.USER));
        outcomes = flatMap(outcomes, c -> calcEnergyCost.execute(c));
        outcomes = resolveConditions(context, conditions, this::execute);
        

        return outcomes;
    }


    public List<Branch<BattleContext>> executeMove(BattleContext context) {
        List<Branch<BattleContext>> outcomes = wrap(context);

        // update flatmaps so I can just give it outcomes and an IEffect
        // removes energy from the user according to the final energy cost of the move
        outcomes = flatMap(outcomes, Effects.subtractValue(Value.USER().CURRENT_ENERGY(), Value.USER().MOVE().CALCED_ENERGY_COST()));
        outcomes = flatMap(outcomes, c -> preMove.execute(context));
		outcomes = flatMap(outcomes, c -> onMove.execute(context));
		outcomes = flatMap(outcomes, c -> postMove.execute(context));

        return outcomes;
    }
}
