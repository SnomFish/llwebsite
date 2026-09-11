package github.snomfish.domain.move;

import github.snomfish.domain.move.structures.NoMove;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;

public class MoveBuilder {
    

    private Move move;


    public static MoveBuilder newMove(MoveId id) {
        return new MoveBuilder(id);
    }


    private MoveBuilder(MoveId id) {
        this.move = new Move(
            id,
            "default name give by move builder",
            new NoCondition(),
            TypeId.NULL,
            CategoryId.MELEE,
            new Constant(255),
            new Constant(255),
            new NoMove(),
            new NoMove(),
            new NoMove(),
            new NoMove()
        );
    }
    public Move build() {
        return move;
    }


    public MoveBuilder name(String name) {
        move.setName(name);
        return this;
    }
    public MoveBuilder condition(ICondition condition) {
        move.setCondition(condition);
        return this;
    }
    public MoveBuilder type(TypeId type) {
        move.setType(type);
        return this;
    }
    public MoveBuilder category(CategoryId category) {
        move.setCategory(category);
        return this;
    }
    public MoveBuilder energyCost(INumber energyCost) {
        move.setEnergyCost(energyCost);
        return this;
    }
    public MoveBuilder priority(INumber priority) {
        move.setPriority(priority);
        return this;
    }

    public MoveBuilder beforeHit(IMove beforeHit) {
        move.setBeforeHit(beforeHit);
        return this;
    }
    public MoveBuilder onHit(IMove onHit) {
        move.setOnHit(onHit);
        return this;
    }
    public MoveBuilder afterHit(IMove afterHit) {
        move.setAfterHit(afterHit);
        return this;
    }
    public MoveBuilder onMiss(IMove onMiss) {
        move.setOnMiss(onMiss);
        return this;
    }
}
