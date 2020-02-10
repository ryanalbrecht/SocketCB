/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket.cb;

import com.google.gson.Gson;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import socket.cb.SocketListener;

/**
 *
 * @author Ryan
 */
public class Server extends WebSocketServer {

    private String uuid;
    private ArrayList<Integer> clientIDs;
    private SocketListener socketListener;

    public Server(String host, int port, SocketListener socketListener) {
        super(new InetSocketAddress(host, port));
        this.clientIDs = new ArrayList<>();
        this.uuid = UUID.randomUUID().toString();
        this.socketListener = socketListener;
    }

    public Server(String host, int port) {
        super(new InetSocketAddress(host, port));
        this.clientIDs = new ArrayList<>();
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        int clientID = generateClientID();
        clientIDs.add(clientID);      
        conn.setAttachment(clientID);
        
        HashMap data = new HashMap<String, Object>();
        data.put("clientID", conn.getAttachment());        
        socketListener.onOpen(data);
        
        conn.send("Welcome to the server: " + this.getUuid()); //This method sends a message to the new client      
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
        //remove the client ID
        HashMap data = new HashMap<String, Object>();
        data.put("clientID", conn.getAttachment());      
        socketListener.onOpen(data);
        
        int clientID = (int)conn.getAttachment();
        clientIDs.remove(new Integer(clientID));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        DataPacket dataPacket = new DataPacket(conn.getAttachment(), message);
        String dataPacketMessage = dataPacket.toString();
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
    
        HashMap data = new HashMap<String, Object>();
        data.put("clientID", conn.getAttachment()); 
        data.put("message", message);
        
        String newMessage = socketListener.onMessage(data);
        
        System.out.println("sending message " + newMessage);
        
        broadcast(newMessage);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }

    public String getUuid() {
        return uuid;
    }
    
    private int generateClientID(){
        int clientID;        
        do{
            clientID = new Random().nextInt(99999 - 11111 + 1) + 11111;
        }while( clientIDs.contains(clientID));
        
        return clientID;
    }
    
}
