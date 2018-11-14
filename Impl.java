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


//remote object
public class Impl implements RemoteInterface {
  private Map<String, Set<Integer>> upToDatefileCacheInClientsMap = new HashMap<>();
  private Set<String> inWritingStatusFileList = new HashSet<>();
  private int currentClientId = 0;

  @Override
  public int getClientId() {
    return currentClientId++;
  }
 @Override
  public boolean checkIsFileModifyingAndLockItIfNot(String fileName) throws RemoteException {
    if(inWritingStatusFileList.contains(fileName)){
      return true;
    }
    inWritingStatusFileList.add(fileName);
    return false;
 }

  @Override
  public void invalidateCacheForFile(String fileName, int clientIdHasUpToDateCache) throws RemoteException{
      upToDatefileCacheInClientsMap.put(fileName, new HashSet<>(Arrays.asList(clientIdHasUpToDateCache)));
  }

  @Override
  public boolean isFileCacheUpToDateForClient(String fileName, int clientId) throws RemoteException {
    return upToDatefileCacheInClientsMap.size() > 0
    && upToDatefileCacheInClientsMap.get(fileName) != null
    && upToDatefileCacheInClientsMap.get(fileName).stream().filter(id -> id.equals(clientId)).count() == 1;
  }

  @Override
  public void notifyServerClientHasCachedLatestFile(String fileName, int clientId) throws RemoteException{
    if(upToDatefileCacheInClientsMap.containsKey(fileName)) {
      Set<Integer> currentClientList = upToDatefileCacheInClientsMap.get(fileName);
      if (currentClientList == null)
        currentClientList = new HashSet<>();
      currentClientList.add(clientId);
      upToDatefileCacheInClientsMap.put(fileName, currentClientList);
    } else {
      upToDatefileCacheInClientsMap.put(fileName, new HashSet<>(Arrays.asList(clientId)));
    }

  }

  @Override
  public String createFile(String str) throws RemoteException {
    File file = new File(str);
    try {
      if (file.createNewFile()) {
        return str + " is created\n";
      } else {
        return str + " already existed\n";
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String readFile(String str) throws RemoteException {
    try {
      BufferedReader br = new BufferedReader(new FileReader(str));
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
    return "File doesn't exist";
  }

  @Override
  public boolean checkFileNameExists(String str) throws RemoteException {
    try {
      File f = new File(str);
      boolean exists = f.exists();
      return exists;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public String writeFile(String fileName, String filContent) throws RemoteException {
    try {
      PrintWriter writer = new PrintWriter(fileName);
      writer.print(filContent);
      writer.close();
      inWritingStatusFileList.remove(fileName);
      return "Write Completed ";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<String> getServerFileList() {
    List<String> ret;
    File folder = new File(".");
    ret = Arrays.stream(folder.listFiles())
        .map(File::getName)
        .filter(name -> name.endsWith(".txt"))
        .collect(Collectors.toList());
    return ret;
  }

  @Override
  public String deleteFile(String str) throws RemoteException {
    File file = new File(str);

    if (file.delete()) {
      return "Succeeded\n";
    } else {
      return "Failed\n";
    }
  }

    /*@Override
    public String viewFile() throws RemoteException {
        String display = "";
        File f = null;
        File[] paths;
        try {
            f = new File(System.getProperty("user.dir"));
            paths = f.listFiles();
            for(File path:paths) {
                display += path.getName() + "\t";
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return display;
    }*/
}