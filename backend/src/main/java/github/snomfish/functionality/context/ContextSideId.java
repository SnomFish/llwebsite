package github.snomfish.functionality.context;

import java.util.function.Function;

import github.snomfish.domain.SideId;

public enum ContextSideId {
    USER(
        c -> c.userState()
        //c -> c.targetState()
    ),
    TARGET(
        c -> c.targetState()
        //c -> c.userState()
    ),
    ;


    private Function<BattleContext, SideId> self;
    //private Function<BattleContext, SideId> opposition;


    private ContextSideId(
        Function<BattleContext, SideId> self
        //Function<BattleContext, SideId> opposition
    ) {
        this.self = self;
        //this.opposition = opposition;
    }


    public SideId self(BattleContext context) {return self.apply(context);}
    //public SideId opposition(BattleContext context) {return opposition.apply(context);}
}
