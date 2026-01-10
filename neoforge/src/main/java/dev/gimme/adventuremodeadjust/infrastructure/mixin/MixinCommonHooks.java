package dev.gimme.adventuremodeadjust.infrastructure.mixin;

import dev.gimme.adventuremodeadjust.infrastructure.AllowAllAdventureModePredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommonHooks.class)
public class MixinCommonHooks {

    @Redirect(
            method = "onPlaceItemIntoWorld",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"),
            require = 1)
    private static Object skipExistingAdventureModeCheck(ItemStack itemStack, DataComponentType<?> dataComponentType) {
        if (dataComponentType != DataComponents.CAN_PLACE_ON) {
            throw new IllegalStateException("Expected only CAN_PLACE_ON to be requested");
        }
        return AllowAllAdventureModePredicate.INSTANCE;
    }

    @Inject(
            method = "onPlaceItemIntoWorld",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"),
            require = 1,
            cancellable = true)
    private static void addStandardAdventureModeCheck(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemstack = context.getItemInHand();
        Level level = context.getLevel();
        if (!itemstack.canPlaceOnBlockInAdventureMode(new BlockInWorld(level, context.getClickedPos(), false))) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
