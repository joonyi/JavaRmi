
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

public class Server {

	public Server() {
	}

	public static void main(String[] args) {
		try {

			Impl remoteObj = new Impl();
			RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(remoteObj, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("fileServer", stub);
			System.out.println("File server is ready to listen");

		} catch (Exception e) {
			System.err.println("Server exception thrown" + e.toString());
			e.printStackTrace();
		}
	}
}