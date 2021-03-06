/*
	File: RootChatServiceImpl.java
	Author: Francisco Ortiz Abril
	Description: this file implements the methods that
	are needed in the chat service. 

*/
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;

class RootChatServiceImpl extends UnicastRemoteObject implements RootChatService {
    List<LocalServer> l;
    RootChatServiceImpl() throws RemoteException{
       l = new LinkedList<LocalServer>();
    }
    
    public void chargeServer(ChatService s, String name) throws RemoteException{
       LocalServer localServer = new LocalServer(name, s);
       if(!validateId(name))
         l.add(localServer);
    }
    
    public void deleteServer(String s) throws RemoteException{
      for(int i =0; i<l.size(); i++)
      {
        if(l.get(i).getRef(s))
           l.remove(i);
      }
    }
    
    public List<String> getServers() throws RemoteException{
       List<String> names = new LinkedList<String>();
       for (LocalServer s:l)
          names.add(s.getName());
       
       return names;
    }
    
    public List<String> getNames() throws RemoteException{
      List<String> res = new LinkedList<String>();
      for (LocalServer s:l)
       {
         if(s.getClients() != null)
           res.addAll(s.getClients());
       }
      return res;
    }    
    
    public ChatService getServiceOfClient(String nameClient) throws RemoteException{
      ChatService server = null;
       for (LocalServer s:l)
        {
          if(s.findClient(nameClient)){
             server = s.getServer();
             break;
          }
        }
      return server;
    }
    
    //Estado: devolverá un 1 si un cliente se encuentra en la lista de otro Server. Recorrerá todas las listas de los 
    //servidores parándose en la primera coincidencia.
    public int connectToServer() throws RemoteException{
      int resul = 0;  
      
      return resul;
    }
    
    public boolean validateId(String n) throws RemoteException{
      boolean res = false;
      for (LocalServer s:l)
         if(s.getRef(n))
            res = true;
      return res;
    }
    
    public void addUser(String nameClient, String nameServer){
       for (LocalServer s:l)
          if(s.getName().equals(nameServer))
             s.addClient(nameClient);
    }
    
    public void deleteUser(String nameClient, String nameServer){
       for (LocalServer s:l)
           if(s.getName().equals(nameServer))
             s.deleteClient(nameClient);
    }
}
