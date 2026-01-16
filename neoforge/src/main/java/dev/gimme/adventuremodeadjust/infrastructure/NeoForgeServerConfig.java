package dev.gimme.adventuremodeadjust.infrastructure;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import dev.gimme.adventuremodeadjust.domain.config.ServerConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class NeoForgeServerConfig extends ServerConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<Config> CAN_PLACE_ON = BUILDER
            .comment("""
                    What blocks specific items can be placed on when in adventure mode.
                    This uses regex patterns. For example, `"ladder|torch": ".*"` allows ladders and torches to be placed
                    on any block, and `"obsidian": "obsidian"` allows obsidian to be placed only on other obsidian blocks.
                    
                    If a colon (":") is included, the pattern is matched against the full namespaced IDs (e.g., minecraft:dirt).
                    This can be useful to target specific mods.""")
            .define(
                    "canPlaceOn",
                    () -> {
                        Config defaultConfig = TomlFormat.newConfig();
                        defaultConfig.set("ladder|torch|tnt", ".*");
                        defaultConfig.set("crafting_table|furnace|campfire", "grass_block|stone|deepslate");
                        defaultConfig.set("obsidian", "obsidian");
                        defaultConfig.set("ender_eye", "end_portal_frame");
                        return defaultConfig;
                    },
                    NeoForgeServerConfig::validateRegexMap
            );

    private static final ModConfigSpec.ConfigValue<Config> CAN_BREAK = BUILDER
            .comment("""
                    What blocks specific items can break when in adventure mode.
                    For example, `".*": "ladder|.*_log"` allows any held item to break ladders and any type of tree.
                    
                    For using only your fists, "air" is the key.""")
            .define(
                    "canBreak",
                    () -> {
                        Config defaultConfig = TomlFormat.newConfig();
                        defaultConfig.set(List.of(".*"), "ladder|torch|wall_torch|.*_log|.*_ore|obsidian|netherrack|nether_wart|end_stone");
                        return defaultConfig;
                    },
                    NeoForgeServerConfig::validateRegexMap
            );

    /**
     * Validates that the given object is a map of regexes to regexes.
     */
    private static boolean validateRegexMap(Object o) {
        if (!(o instanceof Config cfg)) return false;

        for (Config.Entry e : cfg.entrySet()) {
            if (!isValidRegex(e.getKey())) return false;

            if (!(e.getValue() instanceof String s)) return false;
            if (!isValidRegex(s)) return false;
        }
        return true;
    }

    /**
     * Checks if the given string is a valid regex pattern.
     */
    private static boolean isValidRegex(@NotNull String regex) {
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException ex) {
            return false;
        }
        return true;
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    @Override
    public Map<String, String> getCanPlaceOn() {
        return CAN_PLACE_ON.get().entrySet().stream().collect(Collectors.toMap(UnmodifiableConfig.Entry::getKey, UnmodifiableConfig.Entry::getValue));
    }

    @Override
    public Map<String, String> getCanBreak() {
        return CAN_BREAK.get().entrySet().stream().collect(Collectors.toMap(UnmodifiableConfig.Entry::getKey, UnmodifiableConfig.Entry::getValue));
    }
}
