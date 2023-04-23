package Logic;

import Model.*;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addClient(List<Server> servers, Client c) {
        int minWaitingPeriod = servers.get(0).getWaitingPeriod().get();
        int position = 0;
        for(int i = 1; i < servers.size(); i++){
            if(servers.get(i).getWaitingPeriod().get() < minWaitingPeriod) {
                minWaitingPeriod = servers.get(i).getWaitingPeriod().get();
                position = i;
            }
        }
        servers.get(position).addClient(c);
    }
}
