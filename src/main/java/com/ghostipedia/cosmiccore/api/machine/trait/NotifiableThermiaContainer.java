package com.ghostipedia.cosmiccore.api.machine.trait;

import com.ghostipedia.cosmiccore.api.capability.recipe.IHeatContainer;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;

import java.util.List;
import java.util.function.Predicate;

public class NotifiableThermiaContainer extends NotifiableRecipeHandlerTrait<Integer> implements IHeatContainer {
    @Getter
    private final IO handlerIO;
    @Persisted
    @DescSynced
    @Getter
    private long energy;
    private long lastEnergy;
    @Setter
    private Predicate<Direction> sideInputCondition;
    @Setter
    private Predicate<Direction> sideOutputCondition;

    @Getter
    private final long minimumThermalEnergy;
    @Getter
    private final long maximumThermalEnergy;
    @Getter
    private final long underloadThreshold;
    @Getter
    private final long overloadThreshold;
    private final float conductanceRate;

    public NotifiableThermiaContainer(MetaMachine machine, IO io, HeatInfo heatInfo, float conductanceRate) {
        super(machine);
        this.handlerIO = io;
        this.energy = heatInfo.current();
        this.minimumThermalEnergy = heatInfo.minimum();
        this.maximumThermalEnergy = heatInfo.maximum();
        this.underloadThreshold = heatInfo.underload();
        this.overloadThreshold = heatInfo.overload();
        this.conductanceRate = conductanceRate;
    }

    public void serverTick() {
        if (getMachine().getLevel().isClientSide) return;
    }

    @Override
    public boolean inputsHeat(Direction side) {
        return handlerIO.support(IO.IN) && (sideInputCondition == null || sideInputCondition.test(side));
    }

    @Override
    public boolean outputsHeat(Direction side) {
        return handlerIO.support(IO.OUT) && (sideOutputCondition == null || sideOutputCondition.test(side));
    }

    @Override
    public long getCurrentThermalEnergy() {
        return energy;
    }

    @Override
    public void setCurrentThermalEnergy(long energy) {
        this.energy = energy;
    }

    @Override
    public float getConductanceRate() {
        return conductanceRate;
    }

    @Override
    public float getConductanceRateEnvironment() {
        return 500; //TODO: read dimension map for environmental temperature stuff
    }

    @Override
    public long getBaseTemperature() {
        return 300000; //300K TODO: read dimension map for environmental temperature stuff
    }

    @Override
    public long getLastThermalChange() {
        return energy - lastEnergy;
    }

    @Override
    public List<Integer> handleRecipeInner(IO io, GTRecipe recipe, List<Integer> left,
                                           boolean simulate) {
        return null;
    }

    @Override
    public List<Object> getContents() {
        return null;
    }

    @Override
    public double getTotalContentAmount() {
        return 0;
    }

    @Override
    public RecipeCapability<Integer> getCapability() {
        return null;
    }
}
