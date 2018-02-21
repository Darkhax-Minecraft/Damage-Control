package net.darkhax.damagecontrol;

import net.darkhax.damagecontrol.handler.ConfigurationHandler;
import net.darkhax.damagecontrol.handler.DamageHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "dmgcontrol", name = "Damage Control", version = "@VERSION@", certificateFingerprint = "@FINGERPRINT@")
public class DamageControl {

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new DamageHandler());
        ConfigurationHandler.initConfig(event.getSuggestedConfigurationFile());
        ConfigurationHandler.initGlobalModifiers();
        ConfigurationHandler.initDamageSourceModifiers();
    }
}