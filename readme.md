## java-async
A small project for testing javas async apis.

## Usage
Create a class that implements [ITest](src/main/java/de/manu/javasync/ITest.java). It must be named "Test" at the end as the executor checks for Test classes.
To start the test, add the tests name to the [config file](src/main/resources/config.json) and be sure to load the class into the [tests Set](src/main/java/de/manu/javasync/Main.java).