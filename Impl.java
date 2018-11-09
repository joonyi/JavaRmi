import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.rmi.RemoteException;
import java.io.PrintWriter;


//remote object
public class Impl implements RemoteInterface {

    @Override
    public String sayHello(String str) throws RemoteException {
        return "From the Server: Hello " + str;
    }

    @Override
    public String createFile(String str) throws RemoteException {
        File file = new File(str);
        try {
            if(file.createNewFile())
                return str + " is created\n";
            else
                return str + " already existed\n";
        }
        catch(IOException e) {
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
                if(line == null) break;
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
    public boolean checkFile(String str) throws RemoteException {
        try {
            File f = new File(str);
            boolean exists = f.exists();
            return exists;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String writeFile(String str) throws RemoteException {
        try {
            int name = str.indexOf(" ");
            PrintWriter writer = new PrintWriter(str.substring(0,name));
            writer.print(str.substring(name+1));
            writer.close();
            return "Write Completed ";

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteFile(String str) throws RemoteException {
        File file = new File(str); 
          
        if(file.delete())
            return "Succeeded\n";
        else
            return "Failed\n";
    }

    @Override
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
    }
}