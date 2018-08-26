package com.xiiilab.weather;

/**
 * Created by XIII-th on 24.08.2018
 */
public enum Season {
    WINTER(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY),
    SPRING(Month.MARCH, Month.APRIL, Month.MAY),
    SUMMER(Month.JUNE, Month.JULY, Month.AUGUST),
    AUTUMN(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER);

    public final Month[] months;

    Season(Month... months) {
        this.months = months;
    }
}
