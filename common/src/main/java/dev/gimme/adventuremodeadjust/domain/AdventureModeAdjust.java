package dev.gimme.adventuremodeadjust.domain;

import dev.gimme.adventuremodeadjust.domain.config.ServerConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class AdventureModeAdjust {

    /**
     * Checks if the given itemStack is allowed to be placed on the specified block according to the server configuration.
     */
    public static boolean isAllowedToPlace(ItemStack itemStack, BlockInWorld onBlock) {
        return matchesConfig(itemStack, onBlock, ServerConfig.INSTANCE.getCanPlaceOn());
    }

    /**
     * Checks if the given itemStack is allowed to break the specified block according to the server configuration.
     */
    public static boolean isAllowedToBreak(ItemStack itemStack, BlockInWorld block) {
        return matchesConfig(itemStack, block, ServerConfig.INSTANCE.getCanBreak());
    }

    /**
     * Checks if the specified block matches the configured blocks for the given itemStack.
     */
    private static boolean matchesConfig(@NotNull ItemStack itemStack, @NotNull BlockInWorld block, @NotNull Map<String, String> blocksByItems) {
        var itemRegistry = block.getLevel().registryAccess().lookupOrThrow(Registries.ITEM);
        var itemResourceLocation = itemRegistry.getKey(itemStack.getItem());
        if (itemResourceLocation == null) return false;

        return blocksByItems.entrySet().stream().anyMatch(entry ->
                matchesRegex(itemResourceLocation, entry.getKey()) && matchesBlockRegex(block, entry.getValue())
        );
    }

    /**
     * Checks if the given block matches the specified block regex.
     */
    private static boolean matchesBlockRegex(@NotNull BlockInWorld block, @NotNull String blockRegex) {
        var blockRegistry = block.getLevel().registryAccess().lookupOrThrow(Registries.BLOCK);
        Identifier blockResourceLocation = blockRegistry.getKey(block.getState().getBlock());
        if (blockResourceLocation == null) return false;

        return matchesRegex(blockResourceLocation, blockRegex);
    }

    /**
     * Checks if the given resource location matches the specified regex.
     */
    private static boolean matchesRegex(@NotNull Identifier resourceLocation, @NotNull String regex) {
        if (regex.contains(":")) {
            return resourceLocation.toString().matches(regex);
        } else {
            return resourceLocation.getPath().matches(regex);
        }
    }
}
