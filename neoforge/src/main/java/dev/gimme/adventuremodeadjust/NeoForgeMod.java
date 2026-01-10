package dev.gimme.adventuremodeadjust;

import dev.gimme.adventuremodeadjust.domain.config.ServerConfig;
import dev.gimme.adventuremodeadjust.domain.util.Constants;
import dev.gimme.adventuremodeadjust.infrastructure.NeoForgeServerConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class NeoForgeMod {

    public NeoForgeMod(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, NeoForgeServerConfig.SPEC);
        ServerConfig.INSTANCE = new NeoForgeServerConfig();
    }
}
