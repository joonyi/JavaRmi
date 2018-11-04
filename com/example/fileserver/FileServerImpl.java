package com.example.fileserver;
import java.rmi.RemoteException;
import java.rmi.server.*;

public class FileServerImpl extends UnicastRemoteObject implements FileServerRemoteInterface {

	protected FileServerImpl() throws RemoteException {
		super();
	}

	@Override
	public void pingServer(String clientName) {
		System.out.println("File server pinged by " + clientName);
	}

}
