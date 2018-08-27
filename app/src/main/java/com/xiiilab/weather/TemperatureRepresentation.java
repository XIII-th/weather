package com.xiiilab.weather;

/**
 * Created by XIII-th on 27.08.2018
 */
public enum TemperatureRepresentation {
    CELSIUS{
        @Override
        public String toString(float temperature) {
            return String.format("%f°C", temperature);
        }
    },
    FAHRENHEIT{
        @Override
        public String toString(float temperature) {
            return String.format("%f°F", temperature * 1.8F + 32F);
        }
    },
    KELVIN{
        @Override
        public String toString(float temperature) {
            return String.format("%f°K", temperature + 273.15F);
        }
    };

    public abstract String toString(float temperature);
}
