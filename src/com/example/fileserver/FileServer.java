package src.com.example.fileserver;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileServer {

	private static void createFilesDirectory() {
		System.out.println("Initializing files directory");

		try {
			File fileDirectory = new File(FileServerImpl.FilesDirectoryPath);

			if (fileDirectory.mkdirs()) {
				System.out.println("Files directory is created");
			} else {
				System.out.println("Files directory already exists");
			}
		} catch (Exception e) {
			System.out.println("Unable to create files directory in directory (" + FileServerImpl.FilesDirectoryPath
					+ "): " + e.getMessage());
			throw e;
		}
	}

	public static void main(String[] args) throws RemoteException {

		System.out.println("Starting file server");

		try {
			// Initializing Files Directory
			createFilesDirectory();

			// Registering File Server implementation with the Java RMI Registry
			ServerConfig config = new ServerConfig();
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(config.portNumber));
			registry.rebind(config.remoteInterfaceIdentifier, new FileServerImpl());
			System.out.println("Bound remote interface to RMI registry. ID:" + config.remoteInterfaceIdentifier);

		} catch (Exception e) {
			System.err.println("Server exception thrown" + e.toString());
		}
	}
}