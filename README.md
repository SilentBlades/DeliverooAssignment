# Cron Expression Parser

A command line application or script that parses a cron string and expands each field
to show the times at which it will run. It is compiled using Java 11.0.27.

---

## What it does

1. **Splits** your cron line into 5 fields + the command
2. **Parses** each field (lists, wildcards, ranges, steps)
3. **Prints** out each set: 
   <pre>minute 0 15 30 45
   hour 0
   day of month 1 15
   month 1 2 3 4 5 6 7 8 9 10 11 12
   day of week 1 2 3 4 5
   command /usr/bin/find</pre>
4. **Does not** handle the special time strings such as "@yearly".

## Prerequisites

1. Java 11.0.27
2. Maven 3.6+
3. Git

## Quick Start

1. Clone the repo:
    `git clone -b master https://github.com/SilentBlades/DeliverooAssignment.git`
2. Move to appropriate directory where the project is cloned.
3. Compile:
   `mvn clean compile`
4. Package:
   `mvn clean package`
5. Run the app:
   `java -jar target/cron-parser.jar "*/15 0 1,15 * 1-5 /usr/bin/find"`
6. Run the tests:
   `mvn test`