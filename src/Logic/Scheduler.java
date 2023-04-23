package Logic;

import Model.*;

import java.io.*;
import java.util.*;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxClientsPerServer;
    private Strategy strategy;
    public Scheduler(int maxNoServers, int maxClientsPerServer){
        this.maxNoServers = maxNoServers;
        this.maxClientsPerServer = maxClientsPerServer;
        servers = new ArrayList<>(maxNoServers);
        for(int i = 0; i < maxNoServers; i++) {
            Server s = new Server(this.maxClientsPerServer);
            servers.add(s);
            Thread t = new Thread(s);
            t.start();
        }
    }
    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQueueStrategy();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }
    public void dispatchClient(Client c){
        strategy.addClient(servers, c);
    }
    public List<Server> getServerList() {
        return servers;
    }
    public void print(FileWriter writer){
        for(int i = 0; i < maxNoServers; i++){
            try{
                writer.write("Server " + i + ": " + servers.get(i).toString() + '\n');
            }catch (Exception e){
                System.out.println("Failed to write in txt!");
                e.printStackTrace();
            }
        }
    }
}
