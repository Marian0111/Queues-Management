package Logic;

import Model.*;

import java.util.*;

public interface Strategy {
    public void addClient(List<Server> servers, Client c);
}
