package dev.zoty.okaerinasai;

import org.bukkit.Particle;

public class Settings {
    private static final String HOME_COOLDOWN = "home-cooldown";
    private static final Double DEFAULT_HOME_COOLDOWN = 5.0D;
    private static final String WARP_COOLDOWN = "warp-cooldown";
    private static final Double DEFAULT_WARP_COOLDOWN = 10.0D;
    private static final String PARTICLE = "particle";
    private static final String DEFAULT_PARTICLE = "ENCHANT";
    private static final String PARTICLE_AMOUNT = "particle-amount";
    private static final int DEFAULT_PARTICLE_AMOUNT = 20;
    private static final String DEFAULT_HOME = "default-home";
    private static final String DEFAULT_DEFAULT_HOME = "home";
    private static final String DEFAULT_WARP = "default-warp";
    private static final String DEFAULT_DEFAULT_WARP = "spawn";

    public static float getHomeCooldown() {
        return ((Double) Okaerinasai.getInstance().getConfig().getDouble(HOME_COOLDOWN, DEFAULT_HOME_COOLDOWN)).floatValue();
    }

    public static float getWarpCooldown() {
        return ((Double) Okaerinasai.getInstance().getConfig().getDouble(WARP_COOLDOWN, DEFAULT_WARP_COOLDOWN)).floatValue();
    }

    // Will convert the string into a valid Particle, if the String doesn't represent a valid particle then it returns
    // the default particle. If the default particle is invalid there is not much we can do.
    public static Particle getParticle() {
        String particleString = Okaerinasai.getInstance().getConfig().getString(PARTICLE, DEFAULT_PARTICLE).toUpperCase();
        try {
            return Particle.valueOf(particleString);
        } catch (IllegalArgumentException e) {
            Okaerinasai.getInstance().getLogger().warning("Invalid particle name in config.yml: " + particleString);
            Okaerinasai.getInstance().getLogger().warning("Defaulting to: " + DEFAULT_PARTICLE);
            return Particle.valueOf(DEFAULT_PARTICLE);
        }
    }

    public static int getParticleAmount() {
        int particleAmount = Okaerinasai.getInstance().getConfig().getInt(PARTICLE_AMOUNT, DEFAULT_PARTICLE_AMOUNT);
        if (particleAmount < 1) {
            return  DEFAULT_PARTICLE_AMOUNT;
        } else {
            return particleAmount;
        }
    }

    public static String getDefaultHome() {
        return Okaerinasai.getInstance().getConfig().getString(DEFAULT_HOME, DEFAULT_DEFAULT_HOME);
    }

    public static String getDefaultWarp() {
        return Okaerinasai.getInstance().getConfig().getString(DEFAULT_WARP, DEFAULT_DEFAULT_WARP);
    }
}
