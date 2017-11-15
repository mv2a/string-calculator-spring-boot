
Spring boot app that exposes a single endpoint for calculating numbers contained in a input string.

This app simulates an in memory repository. I have decided to build my own instead of using i.e h2, since the use case
here is quite limited so a simple ConcurrentHashMap will do.

The tests should serve as a guidance for the understanding of the code including the use of the in memory repository
which exposes no controls to the api user, it is for testing the concept only.


Requirements:
Java 8 and Gradle.




Build with: gradle clean build

An executable jar will be generated under build/libs

This app exposes a default swagger documentation: http://localhost:8080/swagger-ui.html which can be used to interact
with the api.



Tiago Leao