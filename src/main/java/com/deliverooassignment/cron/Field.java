package com.deliverooassignment.cron;

import java.util.Map;

public enum Field {
    MINUTE("minute", 0, 59),
    HOUR("hour", 0, 23),
    DAY_OF_MONTH("day of month", 1, 31),
    MONTH("month", 1, 12),
    DAY_OF_WEEK("day of week", 1, 7);

    public static Map<String, Integer> DAYS_OF_WEEK = Map.ofEntries(
            Map.entry("SUN", 0), Map.entry("MON", 1), Map.entry("TUE", 2),
            Map.entry("WED", 3), Map.entry("THURS", 4), Map.entry("FRI", 5),
            Map.entry("SAT", 6)
    );

    private final String label;
    private final int min, max;

    Field(String label, int min, int max) {
        this.label = label;
        this.min = min;
        this.max = max;
    }

    public String getLabel() { return label; }
    public int getMin()      { return min; }
    public int getMax()      { return max; }

    public static Integer mapNameToNumber(String dow) {
        return DAYS_OF_WEEK.get(dow);
    }
}
