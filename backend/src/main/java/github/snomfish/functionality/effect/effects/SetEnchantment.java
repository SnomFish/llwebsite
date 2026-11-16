package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class SetEnchantment {
	

	public enum EnchantmentId {
		AUGMENT,
		SWIFTEN,
		NEAR_ENCHANTMENT,
		FAR_ENCHANTMENT
	}


	private EnchantmentId enchantment;
	private int maxAge;


	private SetEnchantment(EnchantmentId enchantment) {
		this.enchantment = enchantment;
		this.maxAge = 999;
	}


	private SetEnchantment(SetEnchantment copy) {
		this.enchantment = copy.enchantment;
	}
	public SetEnchantment deepCopy() {
		return new SetEnchantment(this);
	}


	public static SetEnchantment augment() {
		return new SetEnchantment(EnchantmentId.AUGMENT);
	}
	public static SetEnchantment swiften() {
		return new SetEnchantment(EnchantmentId.SWIFTEN);
	}
	public static SetEnchantment nearEnchantment() {
		return new SetEnchantment(EnchantmentId.NEAR_ENCHANTMENT);
	}
	public static SetEnchantment farEnchantment() {
		return new SetEnchantment(EnchantmentId.FAR_ENCHANTMENT);
	}
	public SetEnchantment withMaxAge(int maxAge) {
		this.maxAge = maxAge;
		return this;
	}


	// ALWAYS SETS ENCHANTMENTS FOR THE USER'S SIDE OF THE FIELD
	public List<Branch<BattleContext>> execute(BattleContext context) {
		return switch (enchantment) {
			case AUGMENT ->				setAugment(context);
			case SWIFTEN ->				setSwiften(context);
			case NEAR_ENCHANTMENT -> 	setNearEnchantment(context);
			case FAR_ENCHANTMENT -> 	setFarEnchantment(context);
		};
	}


	private List<Branch<BattleContext>> wrap(BattleContext context) {return List.of(new Branch<>(context, 1));}


	public List<Branch<BattleContext>> setAugment(BattleContext context) {
		Mechanics mechanics = context.gamestate().mechanics();
		if (mechanics.has(SideMechanicId.AUGMENT)) return wrap(context);

		List<Branch<BattleContext>> outcomes;
		outcomes = ApplyMechanic.toUserSide(SideMechanicId.AUGMENT).withMaxAge(maxAge).execute(context);
		outcomes = flatMap(outcomes, c -> c.dispatchEvent(RuleForAllId.LOOMIAN_SET_AUGMENT, ContextSideId.USER));

		return outcomes;
	}


	public List<Branch<BattleContext>> setSwiften(BattleContext context) {
		Mechanics mechanics = context.gamestate().mechanics();
		if (mechanics.has(SideMechanicId.SWIFTEN)) return wrap(context);

		List<Branch<BattleContext>> outcomes;
		outcomes = ApplyMechanic.toUserSide(SideMechanicId.SWIFTEN).withMaxAge(maxAge).execute(context);
		outcomes = flatMap(outcomes, c -> c.dispatchEvent(RuleForAllId.LOOMIAN_SET_SWIFTEN, ContextSideId.USER));

		return outcomes;
	}


	private List<Branch<BattleContext>> setNearEnchantment(BattleContext context) {
		Mechanics mechanics = Value.USER().SIDE_MECHANICS().get(context);
		if (mechanics.has(SideMechanicId.NEAR_ENCHANTMENT)) return wrap(context);
		
		List<Branch<BattleContext>> outcomes;
		outcomes = ApplyMechanic.toUserSide(SideMechanicId.NEAR_ENCHANTMENT).withMaxAge(maxAge).execute(context);
		outcomes = flatMap(outcomes, c -> c.dispatchEvent(RuleForAllId.LOOMIAN_SET_NEAR_ENCHANTMENT, ContextSideId.USER));

		return outcomes;
	}


	private List<Branch<BattleContext>> setFarEnchantment(BattleContext context) {
		Mechanics mechanics = Value.USER().SIDE_MECHANICS().get(context);
		if (mechanics.has(SideMechanicId.FAR_ENCHANTMENT)) return wrap(context);

		List<Branch<BattleContext>> outcomes;
		outcomes = ApplyMechanic.toUserSide(SideMechanicId.FAR_ENCHANTMENT).withMaxAge(maxAge).execute(context);
		outcomes = flatMap(outcomes, c -> c.dispatchEvent(RuleForAllId.LOOMIAN_SET_FAR_ENCHANTMENT, ContextSideId.USER));

		return outcomes;
	}
}
