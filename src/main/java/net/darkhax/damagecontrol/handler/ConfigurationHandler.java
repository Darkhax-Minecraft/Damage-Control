package net.darkhax.damagecontrol.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

    private static final Map<ResourceLocation, Float> entityBaseHealth = new HashMap<>();

    private static final Map<DamageSource, Float> damageModifiers = new HashMap<>();

    public static float healthModifier = 1f;

    /**
     * An instance of the Configuration object being used.
     */
    private static Configuration config = null;

    /**
     * Initializes the configuration file.
     *
     * @param file The file to read/write config stuff to.
     */
    public static void initConfig (File file) {

        config = new Configuration(file);
    }

    /**
     * Syncs all configuration properties.
     */
    public static void syncConfig () {

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void initGlobalModifiers () {

        final String category = "GLOBAL_MODIFIERS";
        healthModifier = config.getFloat("GlobalMaxHealthModifier", category, 1f, 0f, 1024f, "The global max health modifier.");

        syncConfig();
    }

    public static void initDamageSourceModifiers () {

        final DamageSource[] sources = new DamageSource[] { DamageSource.IN_FIRE, DamageSource.LIGHTNING_BOLT, DamageSource.ON_FIRE, DamageSource.LAVA, DamageSource.HOT_FLOOR, DamageSource.IN_WALL, DamageSource.DROWN, DamageSource.STARVE, DamageSource.CACTUS, DamageSource.FALL, DamageSource.FLY_INTO_WALL, DamageSource.OUT_OF_WORLD, DamageSource.GENERIC, DamageSource.MAGIC, DamageSource.WITHER, DamageSource.ANVIL, DamageSource.FALLING_BLOCK, DamageSource.DRAGON_BREATH };

        for (final DamageSource source : sources) {

            getDamageModifier(source);
        }
    }

    public static float getMaxHealth (EntityLivingBase entity) {

        return getMaxHealth(EntityList.getKey(entity), entity);
    }

    public static float getDamageModifier (DamageSource source) {

        if (damageModifiers.containsKey(source)) {
            return damageModifiers.get(source);
        }

        final float modifier = config.getFloat("DamageModifier_" + source.damageType, "DAMAGE_SOURCE_MODIFIERS", 1f, 0f, 1024f, "The modifier for all damage from the " + source.damageType + " source.");
        damageModifiers.put(source, modifier);

        syncConfig();

        return modifier;
    }

    public static float getMaxHealth (ResourceLocation entityId, EntityLivingBase entity) {

        if (entityBaseHealth.containsKey(entityId)) {
            return entityBaseHealth.get(entityId);
        }

        final float configAmount = config.getFloat("MaxHealth_" + entityId, "MAX_HEALTH", (float) entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue(), 0f, (float) ((RangedAttribute) SharedMonsterAttributes.MAX_HEALTH).maximumValue, "The maximum health value for " + entityId);
        entityBaseHealth.put(entityId, configAmount);

        syncConfig();

        return configAmount;
    }
}