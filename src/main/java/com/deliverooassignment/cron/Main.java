package com.deliverooassignment.cron;

public class Main {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println("Usage: java -jar cron-parser.jar \"<cron string>\"");
            System.exit(1);
        }
        CronExpression cronExpression = CronExpression.parse(args[0]);
        cronExpression.printExpanded();
    }
}
