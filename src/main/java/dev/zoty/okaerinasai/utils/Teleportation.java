package dev.zoty.okaerinasai.utils;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Random;

public class Teleportation {
    private static final Random random = new Random();

    public static boolean teleportAndPlayEffects(Player player, Location location) {
        Location originLocation = player.getLocation();
        World originWorld = originLocation.getWorld();
        World world = location.getWorld();

        if (player.teleport(location)) {

            Particle particle = Settings.getParticle();
            int particleAmount = Settings.getParticleAmount();

            // Play leaving sound
            originWorld.playSound(
                    originLocation,
                    Sound.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS,
                    4.0F,
                    (1.0F + (random.nextFloat() - random.nextFloat()) * 0.3F) * 0.8F
            );

            // Add leaving particles
            // If there are more than 1 particle, spread them.
            if (particleAmount > 1) {
                for (int i = 0; i < particleAmount; i++) {
                    originWorld.spawnParticle(particle, location, 1, MathUtils.randomParticleOffset(), MathUtils.randomParticleOffset(), MathUtils.randomParticleOffset());
                }
            } else {
                originWorld.spawnParticle(particle, location,1 );
            }

            // Play entering sound
            world.playSound(
                    originLocation,
                    Sound.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS,
                    4.0F,
                    (1.0F + (random.nextFloat() - random.nextFloat()) * 0.3F) * 0.8F
            );

            // Add entering particles
            // If there are more than 1 particle, spread them.
            if (particleAmount > 1) {
                for (int i = 0; i < particleAmount; i++) {
                    world.spawnParticle(particle, location, 1, MathUtils.randomParticleOffset(), MathUtils.randomParticleOffset(), MathUtils.randomParticleOffset());
                }
            } else {
                world.spawnParticle(particle, location,1 );
            }

            return true;
        } else {
            return false;
        }
    }
}
