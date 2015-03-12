MAINCLASS="StunServer"

rm -rf build/
echo "compiling classes..."
mkdir -p build/classes
javac -sourcepath src/main -d build/classes src/main/com/hal/stun/StunServer.java

echo "building manifest..."
echo "Main-Class: com.hal.stun.StunServer" > build/manifest

mkdir -p build/jar
echo "building jar..."
jar cfm build/jar/StunServer.jar build/manifest -C build/classes .