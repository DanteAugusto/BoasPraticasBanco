if not exist out mkdir out

if not exist lib\lombok.jar (
    echo Lombok encontrado em lib\lombok.jar - usando versao do repositorio
) else (
    if not exist lib mkdir lib
    powershell -Command "Invoke-WebRequest -Uri 'https://projectlombok.org/downloads/lombok.jar' -OutFile 'lib\lombok.jar'"
)
javac -cp lib\lombok.jar -processor lombok.launch.AnnotationProcessorHider$AnnotationProcessor -d out src/main/java/com/boaspraticas/banco/*.java src/main/java/com/boaspraticas/banco/model/*.java src/main/java/com/boaspraticas/banco/service/*.java src/main/java/com/boaspraticas/banco/util/*.java
java -cp "out;lib\lombok.jar" com.boaspraticas.banco.Main
