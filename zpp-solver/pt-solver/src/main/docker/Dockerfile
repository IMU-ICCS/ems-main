FROM openjdk:9
ADD pt-solver.jar pt-solver.jar
CMD /config/wait-for-cdo.sh && java -Djavax.net.ssl.trustStore=/config/common/melodic-truststore.p12 -Djavax.net.ssl.trustStorePassword=melodic -Djavax.net.ssl.trustStoreType=pkcs12 -XX:ErrorFile=/logs/java_error%p.log -XX:HeapDumpPath=/logs -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9005 -jar pt-solver.jar
