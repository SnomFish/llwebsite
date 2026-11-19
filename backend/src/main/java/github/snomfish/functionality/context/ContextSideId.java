package github.snomfish.functionality.context;

import java.util.function.Function;

import github.snomfish.domain.SideId;

public enum ContextSideId {
    USER(
        c -> c.userState(),
        c -> c.targetState()
    ),
    TARGET(
        c -> c.targetState(),
        c -> c.userState()
    ),
    ;


    private Function<BattleContext, SideId> self;
    private Function<BattleContext, SideId> opp;


    private ContextSideId(
        Function<BattleContext, SideId> self,
        Function<BattleContext, SideId> opp
    ) {
        this.self = self;
        this.opp = opp;
    }


    public SideId self(BattleContext context) {return self.apply(context);}
    public SideId opp(BattleContext context) {return opp.apply(context);}
}
