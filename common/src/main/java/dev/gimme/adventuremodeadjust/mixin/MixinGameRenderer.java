package dev.gimme.adventuremodeadjust.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Adjusts GameRenderer to make sure the custom adventure mode rules are used when rendering block outlines in the client.
 */
@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    /**
     * Redirects the isEmpty check to always return false, so that the next check for adventure mode permissions
     * is always reached.
     */
    @Redirect(
            method = "shouldRenderBlockOutline",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"),
            require = 1)
    private boolean redirectItemStackIsEmpty(ItemStack itemStack) {
        return false;
    }
}
