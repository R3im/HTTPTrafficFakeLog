HTTP Traffic Fake Log
=====================

HTTP Traffic Fake Log is a simple program that generate fake HTTP traffic logs based on the [W3C log format](https://www.w3.org/Daemon/User/Config/Logging.html#common-logfile-format)


Contents
--------

* [Installation](#installation)
* [Build instructions](#build-instructions)

Installation
------------

1. Download jar:
   [Download](https://github.com/R3im/HTTPTrafficFakeLog/raw/master/build/libs/HTTPTrafficFakeLog-1.0.jar).
2. Install JRE 1.8+:<br>
   [Download and install JRE 1.8 (or newer)](https://www.java.com/fr/download/).
3. Execute:
   Open a command prompt/a terminal, `cd` to the jar location,
   and execute `java -jar HTTPTrafficFakeLog.jar`.
   Jar options:
```
   `USAGE: HTTPTrafficFakeLog [option...] [--help]
	-?, -h, --help               Shows this help message.
	-log, -l                     Set HTTP log file fullpath (default: "/var/log/access.log").
	-verbose, -v                 Log statistics into console while writing to file.`
```

Build instructions
------------------

1. Install JDK 1.8+:<br>
   [Download and install JDK 1.8 (or newer)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
2. Install Eclipse:<br>
   [Download and install Eclipse](https://www.eclipse.org/downloads/).
3. Install Gradle<br>
   Download and install Gradle 4.10.2 (or newer).
   Follow the instructions: [Gradle install](https://gradle.org/install/)
4. Install Git:<br>
   Download [Git](http://git-scm.com/downloads) and install it.
   This step is optional.
5. Clone the HTTPTrafficFakeLog repository:<br>
   If you installed Git, execute the following command: `git clone https://github.com/R3im/HTTPTrafficFakeLog.git`<br>
   Otherwise, download the repository as a [master zip](https://github.com/R3im/HTTPTrafficFakeLog/archive/master.zip) and extract it.
6. Download dependencies:<br>
   Open a command prompt/a terminal, `cd` to the project root directory,
   and execute `./gradle eclipse`.
7. Generate Jar:
   Open a command prompt/a terminal, `cd` to the project root directory,
   and execute `./gradle fatjar`.

	
