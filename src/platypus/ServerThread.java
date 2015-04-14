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

import platypus.network.*;
import platypus.data.*;
import platypus.io.ErrorLogger;
import platypus.config.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;



/**
 *
 * @author JaimehRubiks
 */
public class ServerThread implements Runnable {
    
    private final static ArrayList<ServerThread> handler = new ArrayList<>();
    private final TCPsocket clientSocket;
    private User user;

    public ServerThread(Socket comSocket){
        clientSocket = new TCPsocket(comSocket);
        synchronized (handler){
            handler.add(this);
        }
    }
    
    @Override
    public void run(){
        Message msg;
        
        boolean waiting = clientAuthentication();
        
        try{
            while(waiting){
                msg = clientSocket.getMessage();
                if(msg.getMessage().charAt(0) == '/'){
                    msg = serverCommand(msg.getMessage());
                    unicastMessage(msg);
                } else  
                    broadcastMessage(msg);
            }
        } catch(ConnectionException ce){
            ErrorLogger.toFile("connectionException",ce.toString()); 
        } catch(Exception e){
            ErrorLogger.toFile("unknown", e.toString());
        } finally{
            clientDisconnect(waiting);
        }
    }
    
    public static void broadcastMessage(Message msg){
        synchronized (handler){
            for(ServerThread connection: handler){
                try{
                    connection.clientSocket.sendMessage(msg);
                } catch(ConnectionException e){
                    ErrorLogger.toFile("Warning","Exception on broadMessage()"+e.toString());
                    connection.clientSocket.closeConnection();
                }

            }
        }
    }
    
    public void unicastMessage(Message msg) throws ConnectionException{
        try{
            clientSocket.sendMessage(msg);
        } catch(ConnectionException ce){
            ErrorLogger.toScreen(ce.getMessage());
            clientDisconnect();
        }

    }
    
    public Message serverMessage(String str){
        Message msg = new Message("_SERVER_",str);
        System.out.println(msg.toString());
        return msg;
    }
    
    public boolean clientAuthentication(){
        boolean correct;
        ServerThread temp;
        try{
            user = clientSocket.getUser();
            correct = aunthenticationValidateUsername();
            if (!correct) return false;    
            
            unicastMessage(serverMessage("Hi "+user.getName()+" welcome back!"));
            broadcastMessage(serverMessage(user.getName()+" joined the chat"));
            correct = true;
        } catch(ConnectionException e){
            ErrorLogger.toScreen(e.getMessage());
            correct = false;
        }
        
        return correct;
    }
    
    public void clientDisconnect(){
            clientDisconnect(true);
        }
    
    public void clientDisconnect(boolean waslogin){
        synchronized (handler){
            handler.remove(this);
        }
        try{
            if(waslogin)
                broadcastMessage(serverMessage(user.getName()+" left the chat"));
        } catch(Exception e){}
        
        clientSocket.closeConnection();
        Thread.currentThread().interrupt();
        
    }
    
    public Message serverCommand(String command) throws ConnectionException{
        Message msg;
        
        if(command.equalsIgnoreCase("/ONLINE")){
            msg = serverMessage("There are " +Integer.toString(handler.size())+" people online." );
        } else if(command.equalsIgnoreCase("/WHO")){
            StringBuilder str = new StringBuilder();
            str.append("Online users: ");
            for(ServerThread connection : handler){
                str.append(connection.user.getName()).append(" ");
            }
            msg = serverMessage(str.toString());
        } else if(command.equalsIgnoreCase("/EXIT")){
            msg = serverMessage("Bye");
            unicastMessage(msg);
            throw new ConnectionException("User Disconnected manually"); 
        } else if(command.equalsIgnoreCase("/HELP")){
            StringBuilder str = new StringBuilder();
            str.append("\nAvailable commands:\n");
            str.append("/online [number of connected people]\n");
            str.append("/who [display connected users' names]\n");
            str.append("/exit [disconnect normally from server]\n");
            str.append("/help [this message]\n");
            msg = serverMessage(str.toString());
        }else{
            msg = serverMessage("Unknown command. Please use /help to learn available commands");
        }
        return msg;
    }
    
    public boolean aunthenticationValidateUsername() throws ConnectionException{
        ListIterator<ServerThread> runner = handler.listIterator();
        ServerThread temp;
        boolean correct = true;
        
        if(user.getName().equalsIgnoreCase("_SERVER_") )
            return false;
        
        if(runner.hasNext()){
            temp = runner.next();
            if( temp.user.getName().equalsIgnoreCase(user.getName()) && temp!=this ){
                unicastMessage(serverMessage("That username is already taken. Please choose another."));
                correct = false;
            }
        }
        return correct;
    }
    



}
