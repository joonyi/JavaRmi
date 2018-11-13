 /**
 * The purpose of this class is to implement the server. 
 * This class will have a main() method that:-
 * 1- creates an instance(object of remote object) of the remote object implementation,
 * 2- then exports the remote object
 * 3- then register the remote object with a Java RMI registry
 * */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//RMI Server
public class Server {

public Server() {}

    public static void main(String[] args) {
        try {

            //Create an instance of the remote object
            //here remoteObj is an instance of remote object 'HelloImpl'
            Impl remoteObj = new Impl();

            //To Export the remote object, we will use 'UnicastRe...exportObject(remoteObj, TCPPortNo)' method
            //When you export a remote object, you make that object available to accept incoming calls from clients
            //If you pass a zero to the method, the default TCP port number 1099 is used.
            //Note that the exportObject() method will return a stub, which is a term used to describe a proxy class
            //The stub class is a key to make remote object available for remote invocation
            RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(remoteObj, 0);
            // Bind the remote object's stub in the registry
            // create a registry instance, this will get me the handle to the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("nameOfRObj", stub); // here we bind an instance of the object in the registry 
            System.out.println("Hello Server is ready to listen...");

        } catch (Exception e) {
            System.err.println("Server exception thrown" + e.toString());
            e.printStackTrace();
        }
    }
}