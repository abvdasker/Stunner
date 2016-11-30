MAINCLASS="StunServer"

rm -rf build/
echo "compiling classes..."
mkdir -p build/classes
javac -sourcepath src/main/java -d build/classes src/main/java/com/hal/stun/StunServer.java

echo "copying default configuration..."
mkdir -p build/resources
cp src/main/resources/default.properties build/resources

echo "building manifest..."
echo "Main-Class: com.hal.stun.StunServer" > build/manifest

mkdir -p dist/jar
echo "building jar..."
jar cfm dist/jar/StunServer.jar build/manifest -C build/classes .
