
set JAR_DIR=C:\Users\Young\Programs\EyeDeal\Eye-Deal
set JAR_NAME=Eye-Deal-1.0.jar
set LOG_DIR=%JAR_DIR%\logs

cd %JAR_DIR%
mkdir %LOG_DIR%
java -jar %JAR_NAME% > %LOG_DIR%\log.txt 2>&1
