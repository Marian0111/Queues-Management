package  GUI;

import Logic.Scheduler;
import Model.Client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;

public class SimulationFrame{
    private final JFrame frame = new JFrame("Queue management");
    private final JPanel panel = new JPanel();
    private JLabel title = new JLabel("Queue management");
    private JLabel nrServersLbl = new JLabel("No. of Servers:");
    private JLabel nrClientsLbl = new JLabel("No. of Clients:");
    private JLabel minArrivalLbl = new JLabel("Min arrival time:");
    private JLabel maxArrivalLbl = new JLabel("Max arrival time:");
    private JLabel minServiceLbl = new JLabel("Min service time:");
    private JLabel maxServiceLbl = new JLabel("Max service time:");
    private JLabel timeLimitLbl = new JLabel("Time limit:");
    private JLabel timeLbl = new JLabel("TIME: ");
    private JTextField nrServersTxt = new JTextField();
    private JTextField nrClientsTxt = new JTextField();
    private JTextField minArrivalTxt = new JTextField();
    private JTextField maxArrivalTxt = new JTextField();
    private JTextField minServiceTxt = new JTextField();
    private JTextField maxServiceTxt = new JTextField();
    private JTextField timeLimitTxt = new JTextField();
    private JLabel avgWaitingLbl = new JLabel();
    private JLabel avgServiceLbl = new JLabel();
    private JLabel peakHourLbl = new JLabel();
    private JScrollPane scroll;
    private JTable table;
    private JButton startBtn = new JButton("START");
    private Border border = BorderFactory.createLineBorder(Color.lightGray);
    public SimulationFrame() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        panel.setBackground(Color.LIGHT_GRAY);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) (dimension.getWidth() - frame.getWidth()) / 2, (int) (dimension.getHeight() - frame.getHeight()) / 2);

        title.setFont(new Font("Calibri", Font.BOLD, 25));
        title.setBounds((frame.getWidth() - 300) / 2, 20, 300, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        timeLbl.setFont(new Font("Calibri", 0, 20));
        timeLbl.setBounds(1050, 20, 100, 30);
        panel.add(timeLbl);

        nrServersLbl.setBounds(50, 60, 100, 30);
        panel.add(nrServersLbl);

        nrClientsLbl.setBounds(50, 90, 100, 30);
        panel.add(nrClientsLbl);

        nrServersTxt.setBounds(200, 60, 100, 30);
        nrServersTxt.setBorder(border);
        panel.add(nrServersTxt);

        nrClientsTxt.setBounds(200, 90, 100, 30);
        nrClientsTxt.setBorder(border);
        panel.add(nrClientsTxt);

        minArrivalLbl.setBounds(350, 60, 100, 30);
        panel.add(minArrivalLbl);

        maxArrivalLbl.setBounds(350, 90, 100, 30);
        panel.add(maxArrivalLbl);

        minArrivalTxt.setBounds(500, 60, 100, 30);
        minArrivalTxt.setBorder(border);
        panel.add(minArrivalTxt);

        maxArrivalTxt.setBounds(500, 90, 100, 30);
        maxArrivalTxt.setBorder(border);
        panel.add(maxArrivalTxt);

        minServiceLbl.setBounds(650, 60, 100, 30);
        panel.add(minServiceLbl);

        maxServiceLbl.setBounds(650, 90, 100, 30);
        panel.add(maxServiceLbl);

        minServiceTxt.setBounds(800, 60, 100, 30);
        minServiceTxt.setBorder(border);
        panel.add(minServiceTxt);

        maxServiceTxt.setBounds(800, 90, 100, 30);
        maxServiceTxt.setBorder(border);
        panel.add(maxServiceTxt);

        timeLimitLbl.setBounds(950, 60, 100, 30);
        panel.add(timeLimitLbl);

        timeLimitTxt.setBounds(1050, 60, 100, 30);
        timeLimitTxt.setBorder(border);
        panel.add(timeLimitTxt);

        avgServiceLbl.setBounds(300, 730, 250, 30);
        avgServiceLbl.setFont(new Font("Calibri",Font.BOLD,15));
        panel.add(avgServiceLbl);

        avgWaitingLbl.setBounds(550, 730, 250, 30);
        avgWaitingLbl.setFont(new Font("Calibri",Font.BOLD,15));
        panel.add(avgWaitingLbl);

        peakHourLbl.setBounds(800, 730, 250, 30);
        peakHourLbl.setFont(new Font("Calibri",Font.BOLD,15));
        panel.add(peakHourLbl);

        startBtn.setBounds(1050, 90, 100, 30);
        startBtn.setBorder(border);
        panel.add(startBtn);
        panel.setLayout(null);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    public void createTable(int noServers, Scheduler scheduler){

        Vector columnNames = new Vector(2);
        Vector data = new Vector();

        columnNames.addElement("Servers");
        columnNames.addElement("Clients");

        for(int i = 0; i < noServers; i++){
            Vector servers = new Vector();
            servers.add("Server " + (i + 1) + " :");
            servers.add(scheduler.getServerList().get(i).toString());
            data.add(servers);
        }

        table = new JTable(data, columnNames);
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setPreferredWidth(1000);
        table.setFont(new Font("Calibri", 0, 15));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 20));

        scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds((frame.getWidth() - 1100) / 2, 130, 1100, 400);
        scroll.setBorder(border);
        panel.add(scroll);
    }
    public void waitingClients(ArrayList<Client> generatedClients){

        Vector columnNames = new Vector(10);
        Vector data = new Vector();
        columnNames.add("Waiting Clients");
        for(int i = 0; i <= generatedClients.size() / 18; i++) {
            Vector clients = new Vector();
            String s = "";
            for (int j = 0; j < 17; j++) {
                if(i*17+j == generatedClients.size())
                    break;
                s = s + " " + generatedClients.get(i*17+j);
            }
            clients.add(s);
            data.add(clients);
        }

        table = new JTable(data, columnNames);
        table.setRowHeight(50);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setFont(new Font("Calibri", 0, 15));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 20));

        scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds((frame.getWidth() - 1100) / 2, 550, 1100, 170);
        scroll.setBorder(border);
        panel.add(scroll);
    }
    public void details(float avgWaiting, float avgService, int peakHour){
        DecimalFormat df = new DecimalFormat("0.000");
        avgServiceLbl.setText("Average Service Time : " + df.format(avgService));
        avgWaitingLbl.setText("Average Waiting Time : " + df.format(avgWaiting));
        peakHourLbl.setText("Peak Hour : " + peakHour);
    }
    public int getNrServers() {
        return Integer.parseInt(nrServersTxt.getText());
    }
    public int getNrClients() {
        return Integer.parseInt(nrClientsTxt.getText());
    }
    public int getMinArrivalTime() {
        return Integer.parseInt(minArrivalTxt.getText());
    }
    public int getMaxArrivalTime() {
        return Integer.parseInt(maxArrivalTxt.getText());
    }
    public int getMinServiceTime() {
        return Integer.parseInt(minServiceTxt.getText());
    }
    public int getMaxServiceTime() {
        return Integer.parseInt(maxServiceTxt.getText());
    }
    public int getTimeLimit() {
        return Integer.parseInt(timeLimitTxt.getText());
    }
    public void setTime(int time) {
        timeLbl.setText("TIME : " + time);
    }
    public JButton getStartBtn() {
        return startBtn;
    }
    public void dispatchLabels(){
        avgServiceLbl.setText("");
        avgWaitingLbl.setText("");
        peakHourLbl.setText("");
    }
}