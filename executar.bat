if not exist out mkdir out
javac -d out src/main/java/com/boaspraticas/banco/*.java src/main/java/com/boaspraticas/banco/model/*.java src/main/java/com/boaspraticas/banco/service/*.java
java -cp out com.boaspraticas.banco.Main
