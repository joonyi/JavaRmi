import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;


public class Client {

    public Client() {}

    public static void main(String[] args){

        //String mood = inProfit() ? "happy" : "sad";
        //if args length is lessthan 1, then assign the value localHost to field hostName
        //otherwise, assign args[0] thats passed to hostName
        String hostName = (args.length < 1) ? "localHost" : args[0];
        try {
            //Locate a host from the registry mechanism.
            Registry registry = LocateRegistry.getRegistry(hostName);
            //look up the remote object by its name
            RemoteInterface stub = (RemoteInterface) registry.lookup("nameOfRObj"); 

            String name = stub.sayHello("Joonyi");
            System.out.println("Got info from server: " + " " + name);

            List<String> list =  new ArrayList<String>();
            list.add(stub.readFile());

            for(String line : list)
            System.out.println("Content of file:\n" + line);

            String create = stub.createFile("Joonyi");
            System.out.println(create);

            String delete = stub.deleteFile("Joonyi");
            System.out.println(delete);

        } catch (Exception e) {
            System.out.println("Client exception thrown: " + e.toString());
            e.printStackTrace();
        }
    }
}