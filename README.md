# dwp-test

## Background
This API was built to ingest data from the API at https://bpdts-test-app.herokuapp.com/ and return a list of users living in London or within a given radius of London.

## Installing dependencies

The easiest way to build and run the application is to run it in an IDE like IntelliJ or Eclipse. If you plan on doing it this way, ensure you run a package build with Maven, then skip to the "After running" section at the bottom.

If that's not possible, you'll need to install both Java and Maven manually.

[Go here to learn how to install Java](https://java.com/en/download/help/download_options.xml).

Once that is finished, you'll need to [install Maven.](https://maven.apache.org/install.html)

## Running the program

Once Java and Maven are installed, you'll need to run the following commands in the command line or Git Bash. First, change directory to where you have downloaded the repository. Then run:

`mvn compile`

to compile the code. If all is well, then run:

`mvn clean package`

to do a clean install. Assuming all test cases pass, you should be able to run:

`mvn exec:java -Dexec.mainClass=com.dwpAPI.Main`

which will start the program.

## After running

Once the program is running, go to http://localhost:8080/users/london?distanceInMiles=50 to see a JSON list of all users located in London or withing 50 miles of London.

The parameter distanceInMiles is the radius from the centre of London; this can be changed dynamically.
