package dev.zoty.okaerinasai.utils;

import java.util.Random;

public class MathUtils {
    private static final Random random = new Random();

    public static float randomParticleOffset() {
        return randomParticleOffset(1.0F);
    }

    // Helper to return a somewhat good offset position for the particles
    public static float randomParticleOffset(float multiplier) {
        return (-1.0F + random.nextFloat()) * Math.max(multiplier / 2.0F, 1.0F);
    }
}
