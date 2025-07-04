package com.deliverooassignment.cron;

public enum Field {
    MINUTE("minute", 0, 59),
    HOUR("hour", 0, 23),
    DAY_OF_MONTH("day of month", 1, 31),
    MONTH("month", 1, 12),
    DAY_OF_WEEK("day of week", 1, 7);

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
}
