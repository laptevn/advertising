# Advertising server
## Instructions
- Project can be build using `./gradlew build` command. Unit tests will be run after building.<br/>
- Integration tests can be run using `./gradlew integrationTest` command.<br/>
You can run `./gradlew build integrationTest` command to build and to run integration tests.<br/>
- Application can be run with `java -jar build/libs/advertising-1.0-SNAPSHOT.jar` command.<br/>
- You can pass new path of the directory that contains CSV files for import while running the application using `java -jar build/libs/advertising-1.0-SNAPSHOT.jar --dbDirectory="[PATH TO DIRECTORY]"` command.<br/>For example path can be "./db" if you run this command in the project directory.

## Notes:
1. Double data type is used for Revenue field. Double doesn't handle power of 10 well.<br/>
So when we need accurate results, we need to use BigDecimal type.<br/>
However it requires much more memory and hence CPU than double, so when these resources are important we can sacrifice some accuracy.<br/>
I used double since there is no formal requirement and for simplicity.
## TODO:
1. Cover ReportsController with unit tests.<br/>
Integration tests cover it fully now but it will be great to have unit tests too. I left just integration tests here to save time.