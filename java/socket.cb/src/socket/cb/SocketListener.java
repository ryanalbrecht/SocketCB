/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket.cb;

import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public interface SocketListener {
    public void onOpen(HashMap data);
    public void onClose(HashMap data);
    public String onMessage(HashMap data);
}
