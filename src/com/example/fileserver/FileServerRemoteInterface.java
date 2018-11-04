package com.example.fileserver;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServerRemoteInterface extends Remote {

	 public void pingServer(String clientName) throws RemoteException;

}
