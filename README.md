[![Build Status](https://travis-ci.org/jfaster/mango-benchmark.svg?branch=master)](https://travis-ci.org/jfaster/mango-benchmark)

![](https://raw.githubusercontent.com/jfaster/mango-benchmark/master/benchmark.png)

* Jdbc means using only native jdbc API, do not use any ORM frameworks.
* One *Query Cycle* is defined as single `select id, name, age from user where id = ?`.
* One *Update Cycle* is defined as single `update user set age = ? where id = ?`.

<sup>
<sup>1</sup> Versions: mango 1.3.5, spring-jdbc 5.2.25.RELEASE, mybatis 3.5.13, hsqldb 2.7.2, Java 11.0.15.1 <br/>
<sup>2</sup> Java options: -server -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Xms1096m -Xmx1096m <br/>
</sup>

How to run?
-----------

Requires JDK 11 or higher.

* `git clone https://github.com/jfaster/mango-benchmark.git`
* `cd mango-benchmark`
* `mvn clean package`
* `sh benchmark.sh` for Linux or `benchmark.cmd` for Windows

The `benchmark.sh` and `benchmark.cmd` script is a wrapper around JMH execution.
A full run of the benchmark will take about 10 minutes for all frameworks.
