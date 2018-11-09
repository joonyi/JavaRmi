JCC = javac
<<<<<<< HEAD
SRC = src/com/example/fileserver
BIN = bin
JFLAGS = -g

all:
	$(JCC) $(SRC)/*.java -d $(BIN)
=======
JFLAGS = -g

all:
	javac *.java
>>>>>>> b65f79fdf341c0256b4c606ece430fc0d55a722c


server:
	$(JCC) $(JFLAGS) $(SRC)/ServerConfig.java $(SRC)/FileServerRemoteInterface.java $(SRC)/FileServer.java $(SRC)/FileServerImpl.java -d $(BIN)


client:	
	$(JCC) $(JFLAGS) $(SRC)/ServerConfig.java $(SRC)/FileServerRemoteInterface.java $(SRC)/FileClient.java -d $(BIN)


clean:
<<<<<<< HEAD
	rm -rf $(BIN)


run-server:
	java -classpath $(BIN) com.example.fileserver.FileServer


run-client:
	java -classpath $(BIN) com.example.fileserver.FileClient
=======
	rm *.class
>>>>>>> b65f79fdf341c0256b4c606ece430fc0d55a722c
