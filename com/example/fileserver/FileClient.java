package com.example.fileserver;

import java.rmi.Naming;

public class FileClient {

	public FileClient() {

	}

	public static void main(String[] args) {
		try {

			FileServerRemoteInterface fileServer = (FileServerRemoteInterface) Naming
					.lookup("rmi://localhost/5099/fileServer");
			fileServer.pingServer("Client1");
		} catch (Exception ex) {
			System.out.println("Client exception thrown: " + ex);
			ex.printStackTrace();
		}
	}
}
