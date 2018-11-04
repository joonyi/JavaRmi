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
			fileServer.pingServer("FileClient1");
		} catch (Exception ex) {
			System.err.println("Client exception thrown: " + ex);
		}
	}
}
