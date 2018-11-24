

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteInterface extends Remote {
	
	public int getClientId() throws RemoteException;

	public String createFile(String fileName) throws RemoteException;

	public String readFile(String fileName) throws RemoteException;

	public List<String> getServerFileList() throws RemoteException;

	public String writeFile(String fileName, String content, int clientId) throws RemoteException;

	public boolean checkFileNameExists(String fileName) throws RemoteException;

	public boolean checkAndAcquireWriteLock(String fileName, Boolean shouldAcquireWriteLock) throws RemoteException;

	public Boolean deleteFile(String fileName) throws RemoteException;

	public boolean isFileCacheUpToDateForClient(String filename, int clientId) throws RemoteException;

	public void notifyServerClientHasCachedLatestFile(String filename, int clientId) throws RemoteException;
	
	// public String viewFile() throws RemoteException;	
}