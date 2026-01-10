package dev.gimme.adventuremodeadjust.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class MixinPlayer {

    @Redirect(
            method = "blockActionRestricted",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"),
            require = 1)
    private boolean redirectItemStackIsEmpty(ItemStack instance) {
        return false;
    }
}
