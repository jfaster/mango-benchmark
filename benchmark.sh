#!/bin/bash

JAVA_OPTIONS="-server -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Xms1096m -Xmx1096m"

java -jar ./target/benchmarks.jar -jvmArgs "$JAVA_OPTIONS" -t 8
