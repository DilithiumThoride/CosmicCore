package com.ghostipedia.cosmiccore.api.pipe;

import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import lombok.Getter;

import java.util.Objects;

public class HeatPipeProperties implements IMaterialProperty {

    @Getter
    private final float conductanceRate;
    @Getter
    private final float conductanceEnvironment;
    @Getter
    private final long thermalCapacity;
    @Getter
    private final long thermalCapacityNegative;
    @Getter
    private final long underloadThreshold;
    @Getter
    private final long overloadThreshold;

    private final int hash;

    /**
     * @param minCapacity
     * @param maxCapacity
     * @param conductanceRate The rate at which the thermalCapacity is linearly interpolated to its neighbours and the environment.
     *                        <br><br>A value of 1.0 would cause the source temperature to instantly match the target temperature.
     * @param underloadThreshold
     * @param overloadThreshold
     */
    public HeatPipeProperties(long minCapacity, long maxCapacity, float conductanceRate,
                              long underloadThreshold, long overloadThreshold) {
        assert conductanceRate > 0;
        this.thermalCapacity = maxCapacity;
        this.thermalCapacityNegative = minCapacity;
        this.conductanceRate = conductanceRate;
        this.conductanceEnvironment = conductanceRate / 100f;
        this.overloadThreshold = overloadThreshold;
        this.underloadThreshold = underloadThreshold;
        hash = Objects.hash(this.conductanceRate, this.conductanceEnvironment,
                this.thermalCapacity, this.thermalCapacityNegative);
    }

    /**
     * Creates a {@link HeatPipeProperties} that is not capable of Underloading or Overloading
     */
    public static HeatPipeProperties of(long minCapacity, long maxCapacity, float conductanceRate) {
        return new HeatPipeProperties(minCapacity, maxCapacity, conductanceRate, minCapacity, maxCapacity);
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {

    }

    @Override
    public int hashCode() {
        return hash;
    }
}
