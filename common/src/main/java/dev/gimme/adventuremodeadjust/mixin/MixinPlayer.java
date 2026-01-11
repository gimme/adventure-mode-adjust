package dev.gimme.adventuremodeadjust.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Adjusts Player to make sure the custom adventure mode rules are used on the server.
 */
@Mixin(Player.class)
public class MixinPlayer {

    /**
     * Redirects the isEmpty check to always return false, so that the next check for adventure mode permissions
     * is always reached.
     */
    @Redirect(
            method = "blockActionRestricted",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"),
            require = 1)
    private boolean redirectItemStackIsEmpty(ItemStack instance) {
        return false;
    }
}
