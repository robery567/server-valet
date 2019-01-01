package com.servervaletdev.wrapper;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class Ssh {
    private final String hostname;

    private final String username;

    private final String password;

    private Integer port = 22;

    private Session connection;

    private StringBuilder lastCommandOutput;

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Ssh(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.lastCommandOutput = new StringBuilder();
    }

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Ssh(String hostname, String username, String password, Integer port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.lastCommandOutput = new StringBuilder();
    }

    /**
     * Instantiate the connection
     * @return true on success, otherwise false
     */
    public Boolean connect() {
        try {
            JSch jsch = new JSch();

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            this.connection = jsch.getSession(this.username, this.hostname, this.port);
            this.connection.setConfig(config);
            this.connection.setPassword(this.password);
            this.connection.connect();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
    }

    /**
     * Close the connection
     */
    public void disconnect() {
        this.connection.disconnect();
    }


    public String getMessage() {
        return this.lastCommandOutput.toString();
    }

    /**
     * Execute a given command through ssh
     * @param command The command to be executed
     * @return True on success, false on failure
     */
    public Boolean exec(String command) {
        try {
            Channel channel = this.connection.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);

            InputStream commandOutput = channel.getInputStream();

            channel.connect();

            int readByte = commandOutput.read();

            while(readByte != 0xffffffff) {
                this.lastCommandOutput.append((char)readByte);
                readByte = commandOutput.read();
            }

            ((ChannelExec) channel).setErrStream(System.err);

            channel.disconnect();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
    }
}