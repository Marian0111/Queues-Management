package Model;

public class Client implements Comparable<Client>{
    private int arrivalTime;
    private static int currentID = 1;
    private int serviceTime;
    private int ID;
    public Client(int arrivalTime, int serviceTime){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.ID = currentID;
        currentID++;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public int getID() {
        return ID;
    }
    public String toString(){
        String s = "";
        s = "(" + ID + ", " + arrivalTime + ", " + serviceTime + ")";
        return s;
    }
    @Override
    public int compareTo(Client o) {
        Client c = (Client)o;
        if(arrivalTime - c.getArrivalTime() == 0)
            return(ID - c.getID());
        return(arrivalTime- c.getArrivalTime());
    }
}