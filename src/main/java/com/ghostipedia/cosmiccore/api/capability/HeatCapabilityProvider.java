package com.ghostipedia.cosmiccore.api.capability;

import com.ghostipedia.cosmiccore.api.capability.recipe.IHeatContainer;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.ghostipedia.cosmiccore.api.capability.CosmicCapabilities.CAPABILITY_HEAT_CONTAINER;

public class HeatCapabilityProvider implements ICapabilityProvider {
    private final MetaMachineBlockEntity machine;
    private LazyOptional<IHeatContainer> container = null;

    public HeatCapabilityProvider(MetaMachineBlockEntity machine) {
        this.machine = machine;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        return CAPABILITY_HEAT_CONTAINER.orEmpty(capability, getContainer());
    }

    public LazyOptional<IHeatContainer> getContainer() {
        if (container == null) {
            if (machine.metaMachine instanceof IHeatContainer heatContainer) {
                container = LazyOptional.of(() -> heatContainer);
            } else if (machine.metaMachine instanceof IMultiController){
                container = LazyOptional.of(HeatContainerWrapper::new);
            } else {
                container = LazyOptional.empty();
            }
        }
        return container;
    }

    @Getter
    @Setter
    public static class HeatContainerWrapper implements IHeatContainer {


        /**
         * @return The current amount of Thermal Energy
         */
        @Override
        public long getCurrentThermalEnergy() {
            return 0;
        }

        /**
         * @return The minimum amount of Thermal Energy the HeatContainer can have before it is clamped (unless it can Underload)
         */
        @Override
        public long getMinimumThermalEnergy() {
            return 0;
        }

        /**
         * @return The maximum amount of Thermal Energy the HeatContainer can have before it is clamped (unless it can Overload)
         */
        @Override
        public long getMaximumThermalEnergy() {
            return 0;
        }

        /**
         * Check {@link #getHeatCanBeUnderloaded()} for whether this HeatContainer can underload
         *
         * @return The threshold at which an Underload will occur
         */
        @Override
        public long getUnderloadThreshold() {
            return 0;
        }

        /**
         * Check {@link #getHeatCanBeOverloaded()} for whether this HeatContainer can overload
         *
         * @return The threshold at which an Overload will occur
         */
        @Override
        public long getOverloadThreshold() {
            return 0;
        }

        @Override
        public void setCurrentThermalEnergy(long thermalEnergy) {

        }

        @Override
        public float getConductanceRate() {
            return 0;
        }

        @Override
        public float getConductanceRateEnvironment() {
            return 0;
        }

        @Override
        public long getBaseTemperature() {
            return 0;
        }

        @Override
        public long getLastThermalChange() {
            return 0;
        }

        /**
         * @param side The direction we want to check if heat can input from
         * @return if this container can accept heat from this side
         */
        @Override
        public boolean inputsHeat(Direction side) {
            return false;
        }
    }
}
