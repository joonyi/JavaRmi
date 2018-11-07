package com.example.fileserver;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.*;

public class FileServerImpl extends UnicastRemoteObject implements FileServerRemoteInterface {

	public static final String FilesDirectoryPath = System.getProperty("user.home") + "/Desktop/FileServer/Files";

	protected FileServerImpl() throws RemoteException {
		super();
	}

	@Override
	public void pingServer(String clientName) throws RemoteException {
		System.out.println("File server pinged by " + clientName);
	}

	@Override
	public boolean createFile(String fileNamewithExtension) throws RemoteException {
		File newFile = new File(FilesDirectoryPath + "/" + fileNamewithExtension);
		try {
			if (newFile.createNewFile()) {
				return true;
			} else {
				System.out.println("File " + fileNamewithExtension + " already exists");
			}
		} catch (IOException e) {
			System.out.println("Exception occured during file creation: " + e);
		}

		return false;
	}

}
