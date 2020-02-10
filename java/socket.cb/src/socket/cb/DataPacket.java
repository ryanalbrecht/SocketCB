/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket.cb;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.time.Instant;


/**
 *
 * @author Ryan
 */
public class DataPacket {
    private int clientID;
    private long timestamp;
    private JsonElement packet;
    
    public DataPacket(int clientID, String packet){
        this.clientID = clientID;
        this.packet = JsonParser.parseString(packet);
        this.timestamp = Instant.now().getEpochSecond();
    }
    
    @Override
    public String toString(){
        Gson gson = new Gson();        
        return gson.toJson(this);
    }
}
