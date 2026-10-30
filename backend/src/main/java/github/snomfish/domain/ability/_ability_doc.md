# AbilityId:
- enum class containing an enum for each ability name

# AbilityRegistry:
- where every ability object is created and stored in a map

# IAbility;
- interface for ability containing the execute() definition

# Ability:
- main ability class, contains a list of ability rules and how many times the ability can trigger

# AbilityRule
- contains an event, condition and effect
- when the event happens the condition is checked and if true the effect is applied

# AbilityInstance
- contains an abilityId and if that ability has triggered once bet battle/ per swap in
- separated immutable ability functionality from the mutable battlecontext (this is mutable)

# AbilityBuilder + AbilityRuleBuilder
- builders