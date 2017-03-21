

-To execute the tool via maven (serviceType=0 for generation of the constraint problem, or serviceType=1 for value assigments): 

mvn clean install exec:java -Dexec.args="serviceType ouputDir appName modelName"

or 

mvn clean install exec:java -Dexec.args="serviceType ouputDir appName"

for using CDO

-To create a executable jar with all the required dependencies: 

mvn clean compile assembly:single


-To execute the jar: 

java -jar la-based-reasoner-converter-jar-with-dependencies.jar serviceType outputDir appName modelName

or 

java -jar la-based-reasoner-converter-jar-with-dependencies.jar serviceType outputDir appName 

For using CDO

- To use the tool as a library. 

Generation: You have to create an instance of ToLaBasedReasonerFormat and invoke the toFormatNF(ConstraintProblem cp, String appName, File destDir) method. 

Assignment: You have to create an instance of ToLaBasedReasonerFormat  and invoke the method. assignValues(String fileName, String appName) method. The appName is the Application ID in the CDO server