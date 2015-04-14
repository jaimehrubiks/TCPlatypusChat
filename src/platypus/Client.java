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

import platypus.config.User;
import platypus.data.Message;
import platypus.io.ErrorLogger;
import platypus.io.Keyboard;
import platypus.network.ConnectionException;
import platypus.network.TCPsocket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JaimehRubiks
 */
public class Client {
    
    private TCPsocket clientSocket;
    private final int portNumber;
    private final String hostName;
    private final User user;
    
    public Client(String host, int port){
        this.hostName = host;
        this.portNumber = port;
        user = new User("Anon");
    }
    
    public void run(){
        String str;
        Message msg;
        boolean online;
        
        showGreet();
        do{
            showInstructions();
            str = Keyboard.readLine();
            online = startConnection(str.split(" "));
        }while(online == false);
        
        try{
            Thread rx = new Thread(new ClientRX(clientSocket));
            rx.start();
            while(online){
                str = Keyboard.readLine();
                if(rx.isAlive()){
                    if(!str.equals("")){
                        msg = new Message(user.getName(),str);
                        clientSocket.sendMessage(msg);
                    }
                }else{
                    online = false;
                }
            }   
        } catch(ConnectionException ce){
        } catch(Exception e){
            ErrorLogger.toFile(e.toString());
        }
    }
    
    private boolean startConnection(String[] args){
        
        if(args.length==2 && args[0].equalsIgnoreCase("/connect")){
            user.changeName(args[1]);
        }else{
            showInstructions();
            return false;
        }
        
        try{
            clientSocket = new TCPsocket(hostName,portNumber);
        } catch(ConnectionException ce){
            ErrorLogger.toScreen(ce.getMessage());
            return false;
        }
        
        try{
            System.out.println("Connecting to the server");
            clientSocket.sendUser(user);
            Message msg = clientSocket.getMessage();
            System.out.println(msg.toString());
            clientSocket.checkConnection();
            return true;
        } catch(ConnectionException ce){
            ErrorLogger.toFile(ce.getMessage());
            return false;
        }
    }
    
    private void showInstructions(){
        System.out.println("\nuse /connect to connect to the server *"+hostName+":"+portNumber+"*");
        System.out.println("Example: /connect username [password]\n");
    }
    
    private void showGreet(){
        System.out.println("\n------------------------------------------------------");
        System.out.println("--------------------TCPlatypusCHAT--------------------");
        System.out.println("------------------------------------------------------");
    }
    
}
