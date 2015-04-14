/* 
 * Copyright (C) 2015 Jaime Hidalgo García
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package platypus;
import platypus.network.TCPsocket;
import platypus.data.*;
import platypus.io.ErrorLogger;
import platypus.network.ConnectionException;

/**
 *
 * @author JaimehRubiks
 */
public class ClientRX implements Runnable  {
    
    TCPsocket rxSocket;
    
    public ClientRX(TCPsocket sss){
        rxSocket = sss;
    }
    
    
    @Override
    public void run(){
        boolean waiting = true;
        Message msg;
        while(waiting){
            try{    
                msg = rxSocket.getMessage();
                if( msg.getSender().equals("_SERVER_") && msg.getMessage().equals("Bye")){
                    waiting = false;
                }
                System.out.println(msg.toString());
            } catch(ConnectionException ce){
                ErrorLogger.toScreen("Could not establish connection. Server might have been disconnected.");
                waiting = false;
            }
        }
    }
}
