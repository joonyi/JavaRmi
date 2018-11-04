package com.example.fileserver;
import java.rmi.Remote;

public interface FileServerRemoteInterface extends Remote{

	 public void pingServer(String clientName);

}
