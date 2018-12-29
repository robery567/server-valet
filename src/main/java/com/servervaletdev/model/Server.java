package com.servervaletdev.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
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

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Server(String hostname, String username, String password, Integer port) {
        this.hostName = hostname;
        this.userName = username;
        this.password = password;
        this.port = port;
    }

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Server(String hostname, String username, String password, Integer port, Integer ownerId) {
        this.hostName = hostname;
        this.userName = username;
        this.password = password;
        this.port = port;
        this.userId = ownerId;
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
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
