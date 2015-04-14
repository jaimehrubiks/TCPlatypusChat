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
package platypus.network;
import platypus.io.ErrorLogger;
import platypus.data.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.net.InetSocketAddress;
import platypus.config.User;
/**
 *
 * @author JaimehRubiks
 */
public class TCPsocket {
    
    private static final int port = 8000;
    private static final String hostname = "localhost";
    private Socket comSocket;
    private ObjectOutputStream txBuffer;
    private ObjectInputStream rxBuffer;
    
     public TCPsocket() throws ConnectionException{
        this(hostname,port);
    }
    
    public TCPsocket(String hostname, int port) throws ConnectionException{
        try{
            comSocket = new Socket();
            comSocket.connect(new InetSocketAddress(hostname, port), 2000);
            txBuffer = new ObjectOutputStream(comSocket.getOutputStream());
            rxBuffer = new ObjectInputStream(comSocket.getInputStream());
        } catch(IllegalArgumentException iae){
            throw new ConnectionException("*Port number must be between 0 and 65535, inclusive. Use a port > 1023");
        } catch(UnknownHostException uhe){
            closeConnection();
            throw new ConnectionException("*IP address could not be determined from given hostname");
        } catch(IOException ioe){
            closeConnection();
            throw new ConnectionException("*Error connecting to given server. Is server running?");
        } catch(Exception e){
            closeConnection();
            ErrorLogger.toFile("*UnknownError creating TCP socket");
        }
    }
    
    public TCPsocket(Socket comSocket){
        try{
            this.comSocket = comSocket;
            txBuffer = new ObjectOutputStream(comSocket.getOutputStream());
            rxBuffer = new ObjectInputStream(comSocket.getInputStream());
        } catch(UnknownHostException uhe){
            ErrorLogger.toScreen(uhe.toString());
            closeConnection();
        } catch(IOException ioe){
            ErrorLogger.toFile(ioe.toString());
            closeConnection();
        } 
    }

    
    public final void closeConnection(){
        try{
            if(rxBuffer != null)
                rxBuffer.close();
        }catch(Exception e){
            ErrorLogger.toFile("Log",e.toString());
        }
        try{
            if(txBuffer != null)
                txBuffer.close();
        }catch(Exception e){
            ErrorLogger.toFile("Log",e.toString());
        }
        try{
            if(comSocket != null)
                comSocket.close();
        }catch(Exception e){
            ErrorLogger.toFile("Log",e.toString());
        }
           
    }
    
    
    public synchronized void sendMessage(Message msg) throws ConnectionException{
        checkConnection();
        try{    
            txBuffer.writeObject(msg);
        } catch(IOException ioe){
            ConnectionException ce = new ConnectionException("Lost connection / IOsend");
            throw ce;
        } catch(Exception e){
            ErrorLogger.toFile(e.toString());
        }
    }
    
    public Message getMessage() throws ConnectionException{
        checkConnection();
        Message msg = new Message("uknown","unrreported error");
        try{
            msg = (Message) rxBuffer.readObject();
        }catch(IOException | ClassNotFoundException e){
            ConnectionException ce = new ConnectionException("Lost connection / IOget");
            throw ce;
        }
        return msg;
    } 
    
    
    public void sendUser(User user) throws ConnectionException{
        checkConnection();
        try{
            txBuffer.writeObject(user);
        } catch(IOException ioe){
            ConnectionException ce = new ConnectionException("Lost connection / UserSend");
            throw ce;
        } catch(Exception e){
            ErrorLogger.toFile(e.toString());
        }
    }
    
    public User getUser() throws ConnectionException{
        User user = new User("Unknown");
        checkConnection();
        try{
            user = (User) rxBuffer.readObject();
        } catch(ClassNotFoundException e){
            ErrorLogger.toFile("Error class User not found");
        } catch(IOException ioe){
            ConnectionException ce = new ConnectionException("Error receiving user authentication");
            throw ce;   
        }
        return user;
    } 
            
    public void checkConnection() throws ConnectionException{
        boolean check = comSocket.isClosed();
        
        if(check == true){
            closeConnection();
            throw new ConnectionException("Connection is closed");
        }
        
    }        
    
    
}
