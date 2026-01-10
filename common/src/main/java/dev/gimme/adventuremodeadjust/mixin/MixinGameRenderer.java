package dev.gimme.adventuremodeadjust.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Redirect(
            method = "shouldRenderBlockOutline",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"),
            require = 1)
    private boolean redirectItemStackIsEmpty(ItemStack itemStack) {
        return false;
    }
}
