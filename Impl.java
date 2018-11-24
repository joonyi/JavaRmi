import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;

public class Impl implements RemoteInterface {
	
	private Map<String, Set<Integer>> upToDatefileCacheInClientsMap = new HashMap<>();
	private Set<String> inWritingStatusFileList = new HashSet<>();
	private int currentClientId = 0;

	@Override
	public synchronized int getClientId() {
		return currentClientId++;
	}

	@Override
	public boolean checkAndAcquireWriteLock(String fileName, Boolean shouldAcquireWriteLock) throws RemoteException {
		//Checking and locking the file for writing must happen atomically
		synchronized (inWritingStatusFileList) {
			try {
				System.out.println("Sleep for 10s before checking and acquiring file's write lock");
				TimeUnit.SECONDS.sleep(10);
				System.out.println("Woke up after 10s");
			} catch (InterruptedException ex) {
				System.err.println(ex);
			}
			if (inWritingStatusFileList.contains(fileName)) {
				return true;
			}
			if (shouldAcquireWriteLock) {
				inWritingStatusFileList.add(fileName);
				return false;
			}
		}
		return false;
	}

	
	public void updateFileCacheInClientsMap(String fileName, int clientId) {
		synchronized (upToDatefileCacheInClientsMap) {
			upToDatefileCacheInClientsMap.put(fileName, new HashSet<Integer>(Arrays.asList(clientId)));
		}
	}

	@Override
	public boolean isFileCacheUpToDateForClient(String fileName, int clientId) throws RemoteException {
		synchronized (upToDatefileCacheInClientsMap) {
			return upToDatefileCacheInClientsMap.size() > 0 && upToDatefileCacheInClientsMap.get(fileName) != null
					&& upToDatefileCacheInClientsMap.get(fileName).stream().filter(id -> id.equals(clientId))
							.count() == 1;
		}
	}

	@Override
	public void notifyServerClientHasCachedLatestFile(String fileName, int clientId) throws RemoteException {
		synchronized (upToDatefileCacheInClientsMap) {
			if (upToDatefileCacheInClientsMap.containsKey(fileName)) {
				Set<Integer> currentClientList = upToDatefileCacheInClientsMap.get(fileName);
				if (currentClientList == null) {
					currentClientList = new HashSet<Integer>();
				}
				currentClientList.add(clientId);
				upToDatefileCacheInClientsMap.put(fileName, currentClientList);
			} else {
				upToDatefileCacheInClientsMap.put(fileName, new HashSet<Integer>(Arrays.asList(clientId)));
			}
		}
	}

	@Override
	public String createFile(String fileName) throws RemoteException {
		File file = new File(fileName);
		try {
			if (file.createNewFile()) {
				return fileName + " is created\n";
			} else {
				return fileName + " already existed\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String readFile(String fileName) throws RemoteException {
		
		if (!checkFileNameExists(fileName)) {
			throw new RemoteException("File doesn't exist");
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder content = new StringBuilder();
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				content.append(line + "\n");
			}
			return content.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean checkFileNameExists(String fileName) throws RemoteException {
		try {
			File f = new File(fileName);
			boolean exists = f.exists();
			return exists;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String writeFile(String fileName, String filContent, int clientId) throws RemoteException {
		
		if (!checkFileNameExists(fileName)) {
			throw new RemoteException("File doesn't exist");
		}

		try {
			PrintWriter writer = new PrintWriter(fileName);
			writer.print(filContent);
			writer.close();
			synchronized (inWritingStatusFileList) {
				inWritingStatusFileList.remove(fileName);
			}
			updateFileCacheInClientsMap(fileName, clientId);
			return "Write Completed ";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getServerFileList() throws RemoteException {
		List<String> ret;
		File folder = new File(".");
		ret = Arrays.stream(folder.listFiles()).map(File::getName).filter(name -> name.endsWith(".txt"))
				.collect(Collectors.toList());
		return ret;
	}

	@Override
	public Boolean deleteFile(String fileName) throws RemoteException {
		if (!checkFileNameExists(fileName)) {
			throw new RemoteException("File doesn't exist");
		}
		File file = new File(fileName);
		if (checkAndAcquireWriteLock(fileName, false)) {
			throw new RemoteException("The file is currently being modified. Cannot delete.");
		}
		if (file.delete()) {
			synchronized (upToDatefileCacheInClientsMap) {
				upToDatefileCacheInClientsMap.remove(fileName);
			}
			return true;
		} else {
			return false;
		}
	}
}