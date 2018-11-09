JCC = javac
SRC = src/com/example/fileserver
BIN = bin
JFLAGS = -g

all:
	$(JCC) $(SRC)/*.java -d $(BIN)


server:
	$(JCC) $(JFLAGS) $(SRC)/ServerConfig.java $(SRC)/FileServerRemoteInterface.java $(SRC)/FileServer.java $(SRC)/FileServerImpl.java -d $(BIN)


client:	
	$(JCC) $(JFLAGS) $(SRC)/ServerConfig.java $(SRC)/FileServerRemoteInterface.java $(SRC)/FileClient.java -d $(BIN)


clean:
	rm -rf $(BIN)


run-server:
	java -classpath $(BIN) com.example.fileserver.FileServer


run-client:
	java -classpath $(BIN) com.example.fileserver.FileClient
