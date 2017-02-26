package net.darkhax.damagecontrol;

import net.darkhax.damagecontrol.handler.ConfigurationHandler;
import net.darkhax.damagecontrol.handler.DamageHandler;
import net.darkhax.damagecontrol.libs.Constants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER)
public class DamageControl {
    
    @Instance(Constants.MODID)
    public static DamageControl instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        MinecraftForge.EVENT_BUS.register(new DamageHandler());
        ConfigurationHandler.initConfig(event.getSuggestedConfigurationFile());
        ConfigurationHandler.initGlobalModifiers();
        ConfigurationHandler.initDamageSourceModifiers();
    }
}