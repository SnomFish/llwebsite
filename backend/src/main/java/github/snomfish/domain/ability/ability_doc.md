# AbilityId:
- enum class contaiing an enum for each ability name

# AbilityRegistry:
- where every ability object is created and stored in a map

# IAbility;
- interface for ability containing the execute() definition

# Ability:
- main ability class, contains a list of ability rules

# AbilityRule
- contains an event, condition and effect
- when the event happens the condition is checked and if true the effect is applied