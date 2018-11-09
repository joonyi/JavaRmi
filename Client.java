import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

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

            String hello = stub.sayHello("Client");
            System.out.println(hello);
           
            Scanner in = new Scanner(System.in);
            while(true) {
                System.out.println("1.Create\t2.Read\t3.Write\n4.Delete\t5.View\t6.Exit");
            	System.out.print("Select an operation number (1, 2, 3, 4, 5) ");
            	int cin = in.nextInt();
            	if (cin == 1) {
            		System.out.print("1.Create - Please Enter File Name: ");
                    Scanner sc = new Scanner(System.in);
                    String fileName = sc.nextLine();
                    System.out.println(stub.createFile(fileName));
                }
            	else if (cin == 2) {
            		System.out.print("2.Read - Please Enter File Name: ");
                    Scanner sc = new Scanner(System.in);
                    String fileName = sc.nextLine();
            		List<String> list = new ArrayList<String>();
            		list.add(stub.readFile(fileName));
		            for(String line : list)
		            	System.out.println(line);
                    
            	}
            	else if (cin == 3) {
            		System.out.print("2.Write - Please Enter File Name: ");
                    Scanner sc = new Scanner(System.in);
                    String fileName = sc.nextLine();
                    String content = fileName + " ";
                    if (stub.checkFile(fileName) == true) {
                        System.out.println("You can start writing");
                        sc = new Scanner(System.in);
                        content += sc.nextLine();
                        stub.writeFile(content);
                    } else {
                        System.out.println("File does not exist. Please create one.");
                    }
                }
        		else if (cin == 4) {
        			System.out.print("4.Delete ");
                    Scanner sc = new Scanner(System.in);
                    String fileName = sc.nextLine();
                    System.out.println(stub.deleteFile(fileName));
                }
                else if (cin == 5) {
                    System.out.println("5.View");
                    List<String> list = new ArrayList<String>();
                    list.add(stub.viewFile());
                    for(String line : list)
                        System.out.println(line);
                }
        		else if (cin == 6) {
        			System.out.println("5.Exit");
                    break;
                }
            	else {
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


}