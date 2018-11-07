package com.example.fileserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServerRemoteInterface extends Remote {

	public void pingServer(String clientName) throws RemoteException;

	public boolean createFile(String fileNamewithExtension) throws RemoteException;

	public boolean writeFile(String fileName, String fileContent) throws RemoteException;

	public String readFile(String fileName) throws RemoteException;

	public boolean deleteFile(String fileName) throws RemoteException;
}
