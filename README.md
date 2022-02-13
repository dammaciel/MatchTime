This converter was developed using Spring Boot 2.6.3 framework, Java 11 and Maven.

In order to execute the solution you can either run as a Spring Boot application in your IDE or through the .jar file.

Regarding the second option, to generate the jar file you should execute, under the project folder (MatchTime), the following command on your command line:
 - mvn clean package;
 - jar file will be available on the following path: MatchTime\target\MatchTimeConverter-1.0.0.jar;
 - in alternative, you can use the jar file sent;

Once jar file is available, you can run the solution executing the following command:
 - java -jar {path to jar file}      (e.g. java -jar MatchTime\target\MatchTimeConverter-1.0.0.jar) 

After having the solution started and you can use it through the API:
 - By using Postman:
     - POST
     - http://localhost:8080/api/match/convertPeriod
     - Content-Type: application/x-www-form-urlencoded
     - Body: key->time, value->{match_time} (e.g. time=[H1] 46:15.752)
 - By using command cURL:
      - e.g. curl -X POST http://localhost:8080/api/match/convertPeriod -H "Content-Type: application/x-www-form-urlencoded" -d "time=[H1] 46:15.752" 
    
