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

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author JaimehRubiks
 */
public class Server {
    
    private final int portNumber;
    
    public Server(int port){
        this.portNumber = port;
    }
    
    public void run(){
        
        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
	            Thread t = new Thread(new ServerThread(serverSocket.accept()));
                    t.start();
	        }
	} catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber+" .Check port availability and internet connection");
            System.exit(-1);
        }
        
    }
    
}
