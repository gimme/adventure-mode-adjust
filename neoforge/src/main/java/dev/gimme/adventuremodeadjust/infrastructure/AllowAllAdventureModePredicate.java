package dev.gimme.adventuremodeadjust.infrastructure;

import net.minecraft.world.item.AdventureModePredicate;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.List;

/**
 * An adventure mode predicate that allows all actions.
 */
public class AllowAllAdventureModePredicate extends AdventureModePredicate {

    public static final AllowAllAdventureModePredicate INSTANCE = new AllowAllAdventureModePredicate();

    public AllowAllAdventureModePredicate() {
        super(List.of(), false);
    }

    @Override
    public boolean test(BlockInWorld block) {
        return true;
    }
}
