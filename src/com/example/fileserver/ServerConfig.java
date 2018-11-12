package src.com.example.fileserver;

public class ServerConfig {

	public static final String DEFAULT_HOST_NAME = "localhost";
	public static final String DEFAULT_PORT_NUMBER = "4099";
	public static final String DEFAULT_REMOTE_INTERFACE_IDENTIFIER = "fileserver";

	String hostName;
	String portNumber;
	String remoteInterfaceIdentifier;

	public ServerConfig(String hostName, String portNumber, String remoteInterfaceIdentifier) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.remoteInterfaceIdentifier = remoteInterfaceIdentifier;
	}

	public ServerConfig()
	{
		this.hostName = DEFAULT_HOST_NAME;
		this.portNumber = DEFAULT_PORT_NUMBER;
		this.remoteInterfaceIdentifier = DEFAULT_REMOTE_INTERFACE_IDENTIFIER;
	}
}
