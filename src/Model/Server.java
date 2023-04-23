package Model;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private int totalWaitingTime;
    private boolean loop = false;
    public Server(int noClients){
        clients = new PriorityBlockingQueue<>(noClients);
        waitingPeriod = new AtomicInteger();
        totalWaitingTime = 0;
        loop = true;
    }
    public void addClient(Client client){
        try{
            clients.put(client);
            totalWaitingTime += waitingPeriod.get();
            waitingPeriod.addAndGet(client.getServiceTime());
        }catch (Exception e){
            System.out.println("Failed to add the Client to Queue");
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(loop){
            Client nextClient;
            try{
                if(!clients.isEmpty()) {
                    nextClient = clients.element();
                    while(nextClient.getServiceTime() > 0) {
                        Thread.sleep(1000);
                        waitingPeriod.decrementAndGet();
                        nextClient.setServiceTime(nextClient.getServiceTime() - 1);
                    }
                    clients.remove();
                }
            }catch (Exception e){
                System.out.println("Failed to take the Client from Queue");
                e.printStackTrace();
            }
        }
    }
    public BlockingQueue<Client> getClients() {
        return clients;
    }
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public String toString() {
        String s = "";
        for (Client c : clients)
            s = s + c.toString() + " ";
        if (s.equals(""))
            s = s + "closed";
        return s;
    }
    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }
    public void stopLoop(){
        loop = false;
    }
}
