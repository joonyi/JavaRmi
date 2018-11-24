import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Client {

  public Client() {
  }

  private static Map<String, String> cache;

  public static void main(String[] args) {

    String hostName = (args.length < 1) ? "localHost" : args[0];
    
    cache = new HashMap<>();
    
    try {
      Registry registry = LocateRegistry.getRegistry(hostName);
      RemoteInterface stub = (RemoteInterface) registry.lookup("fileServer");

      int clientId = stub.getClientId();
      System.out.println("The assigned Id is: " + clientId);

      Scanner in = new Scanner(System.in);
      
      while (true) {
        System.out.println("1.Create\t2.Read\t3.Write\n4.Delete\t5.Exit");
        System.out.print("Select an operation number (1, 2, 3, 4, 5) ");
        int cin = in.nextInt();
        
        if (cin == 1) {
          System.out.print("1.Create - Please Enter File Name: ");
          Scanner sc = new Scanner(System.in);
          String fileName = sc.nextLine();
          System.out.println(stub.createFile(fileName));
        } 
        else if (cin == 2) {
          System.out.println("2.Read");
          dumpExistingFileListOnServer(stub);
          System.out.println("Please Enter name of the file you want to read:");
          String fileContent;
          Scanner sc = new Scanner(System.in);
          String fileName = sc.nextLine();

          if (stub.isFileCacheUpToDateForClient(fileName, clientId)) {
            System.out.println("Cache hit for file " + fileName + "\nLoading from local cache...");
            fileContent = cache.get(fileName);
          } else {
            System.out.println("Cache not hit for file " + fileName + "\nClearing locally cached file content and reloading from server...");
            cache.remove(fileName);
            try {
            	fileContent = stub.readFile(fileName);
            	stub.notifyServerClientHasCachedLatestFile(fileName, clientId);
            	cache.put(fileName, fileContent);
            } catch(RemoteException ex) {
            	System.out.println("Server Exception: " + ex.getMessage());
            	continue;
            }
          }
          System.out.println("Here is the content of the file:");
          System.out.println("\n" + fileContent);
        } 
        else if (cin == 3) {
          System.out.println("3.Write");
          dumpExistingFileListOnServer(stub);
          System.out.println("Please Enter the name of the file you want to update: ");
          Scanner sc = new Scanner(System.in);
          String fileName = sc.nextLine();
          String existingContent = "";
          
          if (stub.checkFileNameExists(fileName)) {
            if (stub.checkAndAcquireWriteLock(fileName, true)) {
              System.out.println("The file is being modified by others and you can only choose to read it");
              continue;
            }
            
            String time = String.valueOf(System.currentTimeMillis());
            
            try {
            	existingContent = stub.readFile(fileName);
            } catch (RemoteException ex) {
            	System.out.println("Server Exception: " + ex.getMessage());
            	continue;
            }
            
            PrintWriter writer = new PrintWriter("/tmp/tmp" + time + ".txt");
            writer.print(existingContent);
            writer.close();

            System.out.println("You can start writing in vi now");
            ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/vi", "/tmp/tmp" + time + ".txt");
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);

            Process p = processBuilder.start();
            // wait for editor termination.
            p.waitFor();

            BufferedReader br = new BufferedReader(new FileReader("/tmp/tmp" + time + ".txt"));
            StringBuilder tmpContent = new StringBuilder();
            while (true) {
              String line = br.readLine();
              if (line == null) {
                break;
              }
              tmpContent.append(line + "\n");
            }
            
            try{
            	stub.writeFile(fileName, tmpContent.toString(), clientId);
            	System.out.println("File: " + fileName + " updated successfully and locally cached");
                cache.put(fileName, tmpContent.toString());
            } catch(RemoteException ex) {
            	System.out.println("Server Exception: "+ ex.getMessage());
            	continue;
            }
 
          } else {
            System.out.println("File does not exist. Please create one.");
          }
        } 
        else if (cin == 4) {
          System.out.println("4.Delete ");
          dumpExistingFileListOnServer(stub);
          System.out.println("Please Enter name of the file you want to delete:");
          Scanner sc = new Scanner(System.in);
          String fileName = sc.nextLine();
          try {
        	  if(stub.deleteFile(fileName)) {
    	          cache.remove(fileName);
    	          System.out.println("Successfully deleted the file");
              } else {
            	  System.out.println("Failed to delete the file");
              }     	  
          } catch(RemoteException ex) {
        	  System.out.println("Server Exception: "+ex.getMessage());
          }
        }
        else if (cin == 5) {
          System.out.println("5.Exit");
          break;
        } else {
          System.out.println(cin + " --> Invalid operation");
          break;
        }
        System.out.println("************************************");
      }
    } catch (Exception e) {
      System.out.println("Client exception thrown: " + e.toString());
      e.printStackTrace();
    }
  }

  private static void dumpExistingFileListOnServer(RemoteInterface stub) throws RemoteException {
    List<String> fileNames = stub.getServerFileList();
    System.out.println("Here are the files on server:");
    fileNames.forEach(System.out::println);
    System.out.println();
  }
}