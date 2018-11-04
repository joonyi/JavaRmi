JCC = javac
SRC = src/com/example/fileserver
JFLAGS = -g

all:
	$(JCC) $(SRC)/*.java

Server:
	$(JCC) $(JFLAGS) Server.java

Client:
	$(JCC) $(JFLAGS) Client.java

Impl:
	$(JCC) $(JFLAGS) Impl.java

RemoteInterface:
	$(JCC) $(JFLAGS) RemoteInterface.java

clean:
	rm $(SRC)/*.class
