# clojured-taxi-rides

This repository contains an application that will be used to test and improve my
Clojure bridge to Apache Flink, called
[clojure2flink](https://github.com/thr0n/clojure2flink).
It is heavily inspired by the Apache Flink training available
at http://dataartisans.github.io/flink-training/

## Introduction

You can make use of four different test applications:

1) `ride-cleansing.clj` - Reads all the taxi rides from the input file
and drops every ride that didn't start and end in New York City.

2) `popular-places.clj` - Does the same filtering as the applicaton before.
But furthermore it identifies popular places by counting the number of taxi
rides that started and ended in the same area within a specified time window.

3) `write-rides-to-kafka.clj` - Does the same filtering as the first application
writes the retained rides into a Kafka cluster.

4) `read-rides-from-kafka.clj` - Reads the filtered rides from a Kafka cluster
and does the same analysis as the second application.

If you want to execute one of the Kafka examples please make sure
that you're running a properly configured Kafka cluster.

## Supported software versions

clojured-taxi-rides uses the following software environment for development and testing:

- Clojure: 1.8.0
- Java: 1.8.0_111-b14
- Apache Flink: 1.1.3

## Installation and preparation

1) Download the Taxi Data Stream from
http://dataartisans.github.io/flink-training/trainingData/nycTaxiRides.gz

2) Create the following directory (if not already exist):
<PROJECT_ROOT>/resources/datasets/

3) Copy (don't unzip!) the downloaded `nycTaxiRides.gz` file into this directory

## Usage

`TODO:` How to execute a specific testframe! -> e.g. local/cluster execution
	
	$ lein compile
	$ lein run

    $ java -jar clojured-taxi-rides-0.1.0-standalone.jar [args]


## Examples

`TODO:` Provide some sample output

### Known issues

- The path to the `nycTaxiRides.gz` file has to be specified
in the source code directly. I will try to provide a way
to pass the file path via command-line arguments.

- If you want to execute the Kafka examples please make sure your Kafka cluster
is using the default configuration.
Otherwise the execution will fail.

## Closing remark

Special thanks to the whole team of
[data Artisans](http://data-artisans.com/) for providing this taxi rides example!


## License

Licensed under the [Apache Public License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
