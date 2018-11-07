package com.example.fileserver;

import java.rmi.Naming;

public class FileClient {

	public static void main(String[] args) {

		System.out.println("Starting Client");

		try {

			ServerConfig config = new ServerConfig();
			String remoteUrl = "rmi://" + config.hostName + ":" + config.portNumber + "/"
					+ config.remoteInterfaceIdentifier;
			System.out.println("Connecting to file server with remote url: " + remoteUrl);
			FileServerRemoteInterface fileServer = (FileServerRemoteInterface) Naming.lookup(remoteUrl);
			System.out.println("Established connection with file server");

			// Ping server
			fileServer.pingServer("File Client #1");

			// Create file
			fileServer.createFile("apple.txt");
			fileServer.createFile("orange.txt");

			// Write file
			if (fileServer.writeFile("apple.txt", "I'm apple")) {
				System.out.println("Written to file apple.txt");
			}

			// Write file
			if (fileServer.writeFile("orange.txt", "I'm orange")) {
				System.out.println("Written to file orange.txt");
			}

			// Read file
			String appleContent = fileServer.readFile("apple.txt");
			System.out.println("Read from file apple.txt");
			System.out.println(appleContent);

			// Read file
			String orangeContent = fileServer.readFile("orange.txt");
			System.out.println("Read from file orange.txt");
			System.out.println(orangeContent);

			// Delete file
			if (fileServer.deleteFile("orange.txt")) {
				System.out.println("Deleted file orange.txt");
			}

		} catch (Exception ex) {
			System.err.println("Client exception thrown: " + ex);
		}
	}
}
