package com.ghostipedia.cosmiccore.api.capability;

public interface IHeatInfoProvider {

    /**
     * @param current
     * @param minimum Minimum thermalEnergy before clamping occurs
     * @param maximum Maximum thermalEnergy before clamping occurs
     * @param underload If less than minimum, bypasses clamping. Specifies threshold at which an underload can/will occur
     * @param overload If more than maximum, bypasses clamping. Specifies threshold at which an overload can/will occur
     * @param canUnderload Does nothing as a setter, only use for reading
     * @param canOverload Does nothing as a setter, only use for reading
     */
    record HeatInfo(
            Long current,
            Long minimum,
            Long maximum,
            Long underload,
            Long overload,
            boolean canUnderload,
            boolean canOverload
    ) {
        /**
         * Instantiate a HeatInfo with no currentThermalEnergy that is incapable of underloading or overloading
         */
        public static HeatInfo of(long minimum, long maximum) {
            return new HeatInfo((long) 0, minimum, maximum, minimum, maximum, false, false);
        }

        /**
         * Instantiate a HeatInfo with currentThermalEnergy that is incapable of underloading or overloading
         */
        public static HeatInfo of(long current, long minimum, long maximum) {
            return new HeatInfo(current, minimum, maximum, minimum, maximum, false, false);
        }
    }

    // I need some way to store all dimensions temps, idk do it in the next interface.
    HeatInfo getHeatInfo();

    boolean supportsImpossibleHeatValues();
}
