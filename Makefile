JCC = javac
JFLAGS = -g

all:
	javac *.java

Server:
	$(JCC) $(JFLAGS) Server.java

Client:
	$(JCC) $(JFLAGS) Client.java

Impl:
	$(JCC) $(JFLAGS) Impl.java

RemoteInterface:
	$(JCC) $(JFLAGS) RemoteInterface.java

clean:
	rm *.class
