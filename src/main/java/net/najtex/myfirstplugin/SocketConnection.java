package net.najtex.myfirstplugin;

import java.io.*;
import java.net.*;

public class SocketConnection {

    private Socket clientSocket;
    private PrintWriter out;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    public void stopConnection() throws IOException {
        out.close();
        clientSocket.close();
    }

}
