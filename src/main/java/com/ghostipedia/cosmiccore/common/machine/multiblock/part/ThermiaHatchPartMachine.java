package com.ghostipedia.cosmiccore.common.machine.multiblock.part;

import com.ghostipedia.cosmiccore.api.capability.recipe.IHeatContainer;
import com.ghostipedia.cosmiccore.api.machine.trait.NotifiableThermiaContainer;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ThermiaHatchPartMachine extends TieredIOPartMachine implements IHeatContainer {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(ThermiaHatchPartMachine.class, TieredIOPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @DescSynced
    private final NotifiableThermiaContainer thermiaContainer;
    public ThermiaHatchPartMachine(IMachineBlockEntity holder, int tier, IO io) {
        super(holder, tier, io);
        this.thermiaContainer = createThermiaContainer();
    }

    protected NotifiableThermiaContainer createThermiaContainer() {
        NotifiableThermiaContainer container;
        if (io == IO.OUT) {
            container = new NotifiableThermiaContainer(this, IO.OUT, getThermiaLimits(tier), 0);
            container.setSideOutputCondition(s -> s == getFrontFacing());
            container.setCapabilityValidator(s -> s == null || s == getFrontFacing());
        } else {
            container = new NotifiableThermiaContainer(this, IO.IN, getThermiaLimits(tier), 0);
            container.setSideInputCondition(s -> s == getFrontFacing());
            container.setCapabilityValidator(s -> s == null || s == getFrontFacing());
        }
        return container;
    }


    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 128, 63);

        group.addWidget(new ImageWidget(4, 4, 120, 55, GuiTextures.DISPLAY));
        group.addWidget(new LabelWidget(8, 8, Component
                .translatable("gui.cosmiccore.thermia_hatch.label." + (this.io == IO.IN ? "import" : "export"))));
        group.addWidget(new LabelWidget(8, 18, () -> I18n.get("gui.cosmiccore.thermia_hatch.hatch_limit")));
        group.addWidget(new LabelWidget(8, 28,
                () -> I18n.get(FormattingUtil.formatNumbers(thermiaContainer.getOverloadThreshold()), "K"))
                .setClientSideWidget());
        group.addWidget(new LabelWidget(8, 38, () -> I18n.get("gui.cosmiccore.thermia_hatch.stored_temp"))
                .setClientSideWidget());
        group.addWidget(new LabelWidget(8, 48,
                () -> I18n.get(FormattingUtil.formatNumbers(thermiaContainer.getCurrentThermalEnergy()), "K"))
                .setClientSideWidget());
        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    //TODO: Review these numbers. I don't know what measurement these were in. The current implementation is millikelvin
    public static HeatInfo getThermiaLimits(int tier) {
        return switch (tier) {
            case GTValues.ZPM -> HeatInfo.of(0, 95000);
            case GTValues.UV -> HeatInfo.of(0, 128000);
            case GTValues.UHV -> HeatInfo.of(0, 108000);
            case GTValues.UEV -> HeatInfo.of(0, 158000);
            case GTValues.UIV -> HeatInfo.of(0, 198400);
            case GTValues.UXV -> HeatInfo.of(0, 360000);
            case GTValues.OpV -> HeatInfo.of(0, 2500000);
            case GTValues.MAX -> HeatInfo.of(0, Long.MAX_VALUE); // H O T
            default -> HeatInfo.of(273000, 273000);
        };
    }

    @Override
    public void saveCustomPersistedData(CompoundTag tag, boolean forDrop) {
        tag.putLong("Thermal", thermiaContainer.getCurrentThermalEnergy());
        super.saveCustomPersistedData(tag, forDrop);
    }

    @Override
    public void loadCustomPersistedData(CompoundTag tag) {
        thermiaContainer.setCurrentThermalEnergy(tag.getLong("Thermal"));
        super.loadCustomPersistedData(tag);
    }

    @Override
    public long acceptHeatFromNetwork(Direction side, long thermalEnergy) {
        return thermiaContainer.acceptHeatFromNetwork(side, thermalEnergy);
    }

    @Override
    public boolean inputsHeat(Direction side) {
        return thermiaContainer.inputsHeat(side);
    }

    @Override
    public boolean outputsHeat(Direction side) {
        return thermiaContainer.outputsHeat(side);
    }

    @Override
    public long changeHeat(long thermalEnergy) {
        return thermiaContainer.changeHeat(thermalEnergy);
    }

    @Override
    public long getOverloadThreshold() {
        return thermiaContainer.getOverloadThreshold();
    }

    @Override
    public long getUnderloadThreshold() {
        return thermiaContainer.getUnderloadThreshold();
    }

    @Override
    public long getLastThermalChange() {
        return thermiaContainer.getLastThermalChange();
    }

    @Override
    public long getCurrentThermalEnergy() {
        return thermiaContainer.getCurrentThermalEnergy();
    }

    @Override
    public void setCurrentThermalEnergy(long energy) {
        thermiaContainer.setCurrentThermalEnergy(energy);
    }

    @Override
    public long getMaximumThermalEnergy() {
        return thermiaContainer.getMaximumThermalEnergy();
    }

    @Override
    public long getMinimumThermalEnergy() {
        return thermiaContainer.getMinimumThermalEnergy();
    }

    @Override
    public long getBaseTemperature() {
        return thermiaContainer.getBaseTemperature();
    }

    @Override
    public float getConductanceRate() {
        return thermiaContainer.getConductanceRate();
    }
    public float getConductanceRateEnvironment() {
        return thermiaContainer.getConductanceRateEnvironment();
    }

    @Override
    public boolean supportsImpossibleHeatValues() {
        return thermiaContainer.supportsImpossibleHeatValues();
    }
}
