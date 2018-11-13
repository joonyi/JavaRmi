/**
 * The purpose of this interface is to declare a set of remote methods
 * and each remote method must declare RemoteException in its throws clause
 * Here we are simple making this available to remote accesseser
 *
 * A remote interface defines a remote service.
 * The interface java.rmi.remote extend not interface or methods,
 * it is a marker interface which distinguishes remote interfaces from non-remote interfaces.*/

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface RemoteInterface extends Remote {
	/**
	 * method returns a String message to its caller
	 * @param str :value of String*/
	public int getClientId() throws RemoteException;
  public void invalidateCacheForFile(String fileName) throws RemoteException;
	public String createFile(String str) throws RemoteException;
	public String readFile(String str) throws RemoteException;
  public List<String> getServerFileList() throws RemoteException;
	public String writeFile(String fileName, String content) throws RemoteException;
	public boolean checkFileNameExists(String str) throws RemoteException;
  public boolean checkIsFileModifyingAndLockItIfNot(String str) throws RemoteException;
	public String deleteFile(String str) throws RemoteException;
  public boolean isFileCacheUpToDateForClient(String filename, int clientId) throws RemoteException;
  public void notifyServerClientHasCachedLatestFile(String filename, int clientId) throws RemoteException;
	//public String viewFile() throws RemoteException;
}