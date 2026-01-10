package dev.gimme.adventuremodeadjust.mixin;

import dev.gimme.adventuremodeadjust.domain.AdventureModeAdjust;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class MixinItemStack {

    @Inject(method = "canPlaceOnBlockInAdventureMode", at = @At("HEAD"), cancellable = true)
    private void onCanPlaceOnBlockInAdventureMode(BlockInWorld block, CallbackInfoReturnable<Boolean> cir) {
        if (AdventureModeAdjust.isAllowedToPlace((ItemStack) (Object) this, block)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "canBreakBlockInAdventureMode", at = @At("HEAD"), cancellable = true)
    private void onCanBreakBlockInAdventureMode(BlockInWorld block, CallbackInfoReturnable<Boolean> cir) {
        if (AdventureModeAdjust.isAllowedToBreak((ItemStack) (Object) this, block)) {
            cir.setReturnValue(true);
        }
    }
}
