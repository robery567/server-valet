package com.servervaletdev.wrapper;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;
//import java.io.OutputStream;


public class Ssh {
    private final String hostname;

    private final String username;

    private final String password;

    private Integer port = 22;

    private Session connection;

    /**
     * @param hostname The remote client machine hostname
     * @param username The remote client machine username
     * @param password The remote client machine password
     */
    public Ssh(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
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
    }

    /**
     * Instantiate the connection
     * @return true on success, otherwise false
     */
    public Boolean connect() {
        try {
            JSch jsch = new JSch();
            this.connection = jsch.getSession(this.username, this.hostname, this.port);

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

    /**
     * Execute a given command through ssh
     * @param command The command to be executed
     * @return True on success, false on failure
     */
    public Boolean exec(String command) {
        try {
            Channel channel = this.connection.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            //OutputStream outputBuffer;

            channel.setInputStream(null);

            //channel.setOutputStream(outputBuffer);

            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];

            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);

                    if (i < 0) {
                        break;
                    }

                    System.out.print(new String(tmp, 0, i));
                }

                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }

                    break;
                }

                Thread.sleep(1000);
            }

            channel.disconnect();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
    }
}