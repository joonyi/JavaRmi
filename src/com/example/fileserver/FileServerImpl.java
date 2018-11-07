package com.example.fileserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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

	@Override
	public boolean writeFile(String fileName, String fileContent) throws RemoteException {
		try {
			File file = new File(FilesDirectoryPath + "/" + fileName);
			if (file.exists()) {
				byte[] bytes = fileContent.getBytes();
				Files.write(file.toPath(), bytes, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
				System.out.println("Successfully written to file: " + fileName);
				return true;
			} else {
				System.out.println("Given file " + fileName + " does not exsist");
			}
		} catch (Exception ex) {
			System.out.println("Unable to write file " + fileName + "/n" + ex.getMessage());
		}
		return false;
	}

	@Override
	public String readFile(String fileName) throws RemoteException {
		try {
			File file = new File(FilesDirectoryPath + "/" + fileName);
			if (file.exists()) {
				byte[] bytes = Files.readAllBytes(file.toPath());
				System.out.println("Successfully read from file: " + fileName);
				return new String(bytes);
			} else {
				System.out.println("Given file " + fileName + " does not exsist");
			}
		} catch (Exception ex) {
			System.out.println("Unable to to read from file " + fileName + "\n" + ex.getMessage());
		}
		return "";
	}

	@Override
	public boolean deleteFile(String fileName) throws RemoteException {
		try {
			File file = new File(FilesDirectoryPath + "/" + fileName);
			if (Files.deleteIfExists(file.toPath())) {
				System.out.println("Successfully deleted file: " + fileName);
				return true;
			} else {
				System.out.println("Given file " + fileName + " does not exsist");
			}
		} catch (Exception ex) {
			System.out.println("Unable to delete file " + fileName + "/n" + ex.getMessage());
		}
		return false;
	}
}
