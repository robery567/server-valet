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

    @Column(name="distribution_name")
    private String distributionName = "Linux";

    @Transient
    private Ssh ServerConnection;

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
     * @param hostname The server hostname to connect via SSH
     * @param username The server username to connect via SSH
     * @param password The password hostname to connect via SSH
     * @param port The server port to connect via SSH
     */
    private void commonConstructor(String hostname, String username, String password, Integer port) {
        this.hostName = hostname;
        this.userName = username;
        this.password = password;
        this.port = port;

        this.ServerConnection = new Ssh(hostname, username, password, port);
        this.ServerConnection.connect();
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
     * @return Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the server Id
     * @param id Server Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the User Id
     * @return Integer
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the User Id
     * @param userId The server user id of the owner
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the server hostname
     * @return String
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the server hostname
     * @param hostName The server hostname to connect via SSH
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Gets the date when the server has been added to the database
     * @return TimeStamp
     */
    public Timestamp getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the date when the server has been added to the database
     * @param dateAdded The creation date
     */
    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Gets the server username
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the server password
     * @param userName The server username to connect via SSH
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the server password
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the server password
     * @param password The server password to connect via SSH
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the server port
     * @return Integer
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Sets the server port
     * @param port The server port to connect via SSH
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Fetch the distribution name
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

        this.distributionName = this.ServerConnection.getMessage().split("=")[1];
    }
}
