package com.example.fileserver;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileServer {

	public FileServer() {
		
	}

	public static void main(String[] args) {
		
		try {
			// Registering File Server implementation with the Java RMI Registry
			Registry registry = LocateRegistry.createRegistry(5099);
			registry.rebind("fileserver", new FileServerImpl());

		} catch (Exception e) {
			System.err.println("Server exception thrown" + e.toString());
		}
	}
}