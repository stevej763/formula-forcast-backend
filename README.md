# Comands to run the application
### Build Gradle
./gradlew build -x test
### Run  built with Java
java -Xms512m -Xmx1024m -jar build/libs/formulaforecast-0.0.1-SNAPSHOT.jar 

### Look for old process to kill:
sudo lsof -i :8080