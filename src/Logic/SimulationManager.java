package Logic;

import Model.Client;
import GUI.*;
import Model.Server;

import java.io.FileWriter;
import java.util.*;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable{
    private int timeLimit = 60;
    private int minProcessingTime = 2;
    private int maxProcessingTime = 4;
    private int minArrivingTime = 2;
    private int maxArrivingTime = 30;
    private int numberOfServers = 2;
    private int numberOfClients = 4;
    private int totalServiceTime;
    private int totalWaitingTime;
    private float avgServiceTime;
    private float avgWaitingTime;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Client> generatedClients;
    public SimulationManager(){
        totalServiceTime = 0;
        frame = new SimulationFrame();
        frame.getStartBtn().addActionListener(l ->{
            frame.dispatchLabels();
            timeLimit = frame.getTimeLimit();
            maxProcessingTime = frame.getMaxServiceTime();
            minProcessingTime = frame.getMinServiceTime();
            maxArrivingTime = frame.getMaxArrivalTime();
            minArrivingTime = frame.getMinArrivalTime();
            numberOfClients = frame.getNrClients();
            numberOfServers = frame.getNrServers();
            generatedClients = new ArrayList<>();
            scheduler = new Scheduler(numberOfServers, numberOfClients);
            scheduler.changeStrategy(selectionPolicy);
            generateNRandomClients();
            Thread t = new Thread(this);
            t.start();
        });
//        generatedClients = new ArrayList<>();
//        scheduler = new Scheduler(numberOfServers, numberOfClients);
//        scheduler.changeStrategy(selectionPolicy);
//        generateNRandomClients();
    }
    private void generateNRandomClients(){
        for(int i = 0; i < numberOfClients; i++){
            Random random = new Random();
            int processingTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            totalServiceTime += processingTime;
            int arrivalTime = random.nextInt(maxArrivingTime - minArrivingTime + 1) + minArrivingTime;
            Client c = new Client(arrivalTime, processingTime);
            generatedClients.add(c);
        }
        avgServiceTime = (float)totalServiceTime / numberOfClients;
        Collections.sort(generatedClients);
    }
    @Override
    public void run() {
        int currentTime = 0;
        int maxClients = 0;
        int peakHour = 0;
        try {
//            FileWriter writer = new FileWriter("Test.txt");
            while (stopCondition(currentTime)) {
                frame.waitingClients((ArrayList<Client>) generatedClients);
//                writer.write("Waiting clients: ");
//                for(int i = 0; i < generatedClients.size(); i++)
//                    writer.write(generatedClients.get(i).toString() + " ");
//                writer.write('\n');
                int i = 0;
                while(i < generatedClients.size()){
                    if (generatedClients.get(i).getArrivalTime() == currentTime) {
                        Client c = generatedClients.get(i);
                        scheduler.dispatchClient(c);
                        generatedClients.remove(generatedClients.get(i));
                    }
                    else i++;
                }
                int nrClients = 0;
                for(Server s : scheduler.getServerList())
                    nrClients += s.getClients().size();
                if(nrClients > maxClients){
                    maxClients = nrClients;
                    peakHour = currentTime;
                }
                frame.setTime(currentTime);
                frame.createTable(numberOfServers, scheduler);
//                writer.write("Time " + currentTime + '\n');
//                scheduler.print(writer);
                currentTime++;
                sleep(1000);
            }
//            writer.write("Time " + currentTime + '\n');
//            scheduler.print(writer);
//            writer.close();
            frame.createTable(numberOfServers, scheduler);
            for(Server s: scheduler.getServerList()){
                totalWaitingTime += s.getTotalWaitingTime();
                s.stopLoop();
            }
            avgWaitingTime = (float)totalWaitingTime / numberOfClients;
            frame.details(avgWaitingTime, avgServiceTime, peakHour);
        } catch (Exception e) {
            System.out.println("Failed to run thread!");
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        SimulationManager gen = new SimulationManager();
//        Thread t = new Thread(gen);
//        t.start();
    }
    private boolean stopCondition(int currentTime){
        if(currentTime > timeLimit)
            return false;
        if(generatedClients.isEmpty()){
            for(Server s : scheduler.getServerList())
                if(s.getWaitingPeriod().get() != 0)
                    return true;
            return false;
        }
        return true;
    }
}
