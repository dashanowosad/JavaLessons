1) Download https://www.azul.com/downloads/zulu-community/?&version=java-13&os=linux&architecture=x86-64-bit&package=jre-fx this package. Unpack and add it in usr/lib/jvm
2) Open console and do this comand sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/zulu13.29.11-ca-fx-jre13.0.2-linux_x64/bin/java 100
3) Next sudo update-alternatives --config java and choose needs version

P. S. Another version java with default was incompatible!!!!

Zulu time in milliseconds = 2-5
JVM default time in milliseconds = 2-6
Byte code equal
