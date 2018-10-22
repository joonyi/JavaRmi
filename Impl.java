import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.rmi.RemoteException;

//remote object
public class Impl implements RemoteInterface {

    @Override
    public String sayHello(String str) throws RemoteException {
        return "Hello: " + str;
    }

    @Override
    public String createFile(String str) throws RemoteException {
        File file = new File(str + ".txt");
        try {
            if(file.createNewFile())
                return str + ".txt is created\n";
            else
                return str + ".txt already existed\n";
        }
        catch(IOException e) {
          e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readFile() throws RemoteException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            String content = "";
            while (true) {
                String line = br.readLine();
                if(line == null) break;
                content += line;
            }
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String writeFile(String str) throws RemoteException {
        return "Create: " + str;
    }

    @Override
    public String deleteFile(String str) throws RemoteException {
        File file = new File(str + ".txt"); 
          
        if(file.delete())
            return "Succeeded to delete the file\n";
        else
            return "Failed to delete the file\n";
    }
}