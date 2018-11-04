package com.example.fileserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileServer {

	public static void main(String[] args) throws RemoteException {

		System.out.println("Starting file server");
		try {
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