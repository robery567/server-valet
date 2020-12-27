package com.servervaletdev.model;

import com.servervaletdev.wrapper.Ssh;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "server")
public class Server {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "hostname")
    private String hostName;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "port")
    private Integer port;

    @Column(name = "date_added")
    private java.sql.Timestamp dateAdded;

    @Column(name = "distribution_name")
    private String distributionName = "Linux";

    @Transient
    private Ssh ServerConnection;

    @Transient
    private Double totalRamMemory;

    @Transient
    private Double usedRamMemory;

    @Transient
    private Double totalStorageMemory;

    @Transient
    private Double usedStorageMemory;

    @Transient
    private Double usedCpuPercentage;

    @Transient
    private String cpuName;

    @Transient
    private Integer totalUptime;

    @Transient
    private Integer activeProcesses;

    @Transient
    private Integer connectedUsers;

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Server(String hostname, String username, String password, Integer port) {
        this.commonConstructor(hostname, username, password, port);
    }

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Server(String hostname, String username, String password, Integer port, Integer ownerId) {
        this.commonConstructor(hostname, username, password, port);
        this.userId = ownerId;
    }

    /**
     * This method will do the common instructions that should be run in every constructor
     *
     * @param hostname The server hostname to connect via SSH
     * @param username The server username to connect via SSH
     * @param password The password hostname to connect via SSH
     * @param port     The server port to connect via SSH
     */
    private void commonConstructor(String hostname, String username, String password, Integer port) {
        this.hostName = hostname;
        this.userName = username;
        this.password = password;
        this.port = port;

        this.ServerConnection = new Ssh(hostname, username, password, port);
        this.ServerConnection.connect();
        this.fetchRemoteAttributes();
    }

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Server(Integer serverId, String hostname, String username, String password, Integer port, Integer ownerId) {
        this.id = serverId;
        this.hostName = hostname;
        this.userName = username;
        this.password = password;
        this.port = port;
        this.userId = ownerId;

        System.out.println(serverId + " " + hostname);
    }

    /**
     * Gets the server Id
     *
     * @return Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the server Id
     *
     * @param id Server Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the User Id
     *
     * @return Integer
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the User Id
     *
     * @param userId The server user id of the owner
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the server hostname
     *
     * @return String
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the server hostname
     *
     * @param hostName The server hostname to connect via SSH
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Gets the date when the server has been added to the database
     *
     * @return TimeStamp
     */
    public Timestamp getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the date when the server has been added to the database
     *
     * @param dateAdded The creation date
     */
    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Gets the server username
     *
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the server password
     *
     * @param userName The server username to connect via SSH
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the server password
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the server password
     *
     * @param password The server password to connect via SSH
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the server port
     *
     * @return Integer
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Sets the server port
     *
     * @param port The server port to connect via SSH
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Fetch the distribution name
     *
     * @return String
     */
    public String getDistributionName() {
        return this.distributionName;
    }

    /**
     * Sets the distribution name by fetching it from the server
     */
    public void setDistributionName() {
        this.ServerConnection.exec("cat /etc/*-release | grep DISTRIB_ID");

        String returnedResponse = this.ServerConnection.getMessage();

        if (returnedResponse.length() == 0) {
            this.distributionName = "Linux";
        } else {
            this.distributionName = this.ServerConnection.getMessage().split("=")[1].trim();
        }
    }

    /**
     * Gets the Server Total RAM Memory (MB)
     *
     * @return Double
     */
    public Double getTotalRamMemory() {
        return totalRamMemory;
    }

    /**
     * Sets the Server Total RAM Memory (MB)
     */
    public void setTotalRamMemory() {
        this.ServerConnection.exec("grep MemTotal /proc/meminfo | awk '{print $2*0.001}'");

        this.totalRamMemory = Double.valueOf(this.ServerConnection.getMessage());
    }

    /**
     * Gets the Server Total Used Ram Memory (MB)
     *
     * @return Double
     */
    public Double getUsedRamMemory() {
        return usedRamMemory;
    }

    /**
     * Sets the Server Total Used Ram Memory (MB)
     */
    public void setUsedRamMemory() {
        this.ServerConnection.exec("free | grep Mem | awk '{print ($4+$3)*0.001}'");

        this.usedRamMemory = Double.valueOf(this.ServerConnection.getMessage());
    }

    /**
     * Gets the Server Total Storage Memory (GB)
     *
     * @return Double
     */
    public Double getTotalStorageMemory() {
        return totalStorageMemory;
    }

    /**
     * Sets the Server Total Storage Memory (GB)
     */
    public void setTotalStorageMemory() {
        this.ServerConnection.exec("df -h / | tail -1 |  awk '{print $2}'");

        this.totalStorageMemory = Double.valueOf(this.ServerConnection.getMessage().split("G")[0]);
    }

    /**
     * Gets the Server Total Used Storage Memory (GB)
     *
     * @return Double
     */
    public Double getUsedStorageMemory() {
        return usedStorageMemory;
    }

    /**
     * Sets the Server Total Used Storage Memory (GB)
     */
    public void setUsedStorageMemory() {
        this.ServerConnection.exec("df -h / | tail -1 |  awk '{print $3}'");

        this.usedStorageMemory = Double.valueOf(this.ServerConnection.getMessage().split("G")[0]);
    }

    /**
     * Gets the percentage of used CPU
     *
     * @return Double
     */
    public Double getUsedCpuPercentage() {
        return usedCpuPercentage;
    }

    /**
     * Sets the percentage of used CPU
     */
    public void setUsedCpuPercentage() {
        this.ServerConnection.exec("top -d 0.5 -b -n2 | grep \"Cpu(s)\"|tail -n 1 | awk '{print $2 + $4}'");

        this.usedCpuPercentage = Double.valueOf(this.ServerConnection.getMessage());
    }

    /**
     * Gets the server CPU Name
     *
     * @return String
     */
    public String getCpuName() {
        return cpuName;
    }

    /**
     * Sets the server CPU Name
     */
    public void setCpuName() {
        this.ServerConnection.exec("cat /proc/cpuinfo | grep 'model name' | uniq");

        this.cpuName = this.ServerConnection.getMessage().split(":")[1].trim();
    }


    /**
     * Gets the total uptime of the server
     * @return Integer
     */
    public Integer getTotalUptime() {
        return totalUptime;
    }

    /**
     * Sets the total uptime of the server
     */
    public void setTotalUptime() {
        this.ServerConnection.exec("uptime | awk -F'( |,|:)+' '{print $6*24+$8}'");

        this.totalUptime = Integer.valueOf(this.ServerConnection.getMessage().trim());
    }


    /**
     * Gets the total number of active processes
     * @return Integer
     */
    public Integer getActiveProcesses() {
        return activeProcesses;
    }

    /**
     * Sets the total number of active processes
     */
    public void setActiveProcesses() {
        this.ServerConnection.exec("ps aux | wc -l");

        this.activeProcesses = Integer.valueOf(this.ServerConnection.getMessage().trim());
    }

    /**
     * Gets the number of total logged in users on the server
     * @return Integer
     */
    public Integer getConnectedUsers() {
        return connectedUsers;
    }

    /**
     * Sets the number of total logged in users on the server
     */
    public void setConnectedUsers() {
        this.ServerConnection.exec("who | wc -l");

        this.connectedUsers = Integer.valueOf(this.ServerConnection.getMessage().trim());
    }

    /**
     * Fetch all the remote attributes
     */
    private void fetchRemoteAttributes() {
        this.setCpuName();
        this.setUsedCpuPercentage();
        this.setUsedStorageMemory();
        this.setTotalStorageMemory();
        this.setTotalRamMemory();
        this.setUsedRamMemory();
        this.setDistributionName();
        this.setTotalUptime();
        this.setActiveProcesses();
        this.setConnectedUsers();
    }
}
