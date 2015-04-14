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

/**
 *
 * @author JaimehRubiks
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int port;
        String host;
        //String username;
        
        if (args.length == 2){
            if (args[0].equals("-l")){
                try {
                    port = Integer.parseInt(args[1]);
                    new Server(port).run();
                } catch (NumberFormatException e) {
                    showInstructions();
                    System.exit(1);
                }
            }
        }
        
        else if(args.length == 3){
            try {
                host = args[1];
                port = Integer.parseInt(args[2]);
                //username = args[3];
                new Client(host,port).run();
            } catch (NumberFormatException e) {
                showInstructions();
                System.exit(1);
            }
        }
        
        else{
            showInstructions();
            System.exit(1);
        }
        
                    
    }
    
    public static void showInstructions(){
        System.out.println("");
        System.out.println("Please provide command-line args as the following examples:");
        System.out.println("CLIENT: PlatypusChat -c host port");
        System.out.println("SERVER: PlatypusChat -l port");
        System.out.println("PlatypusChat -c eldoctordeldesierto.com 8000");
        System.out.println("PlatypusChat -l 8000");
        System.out.println("");
        System.out.println("Server must be running before clients. Port must be > 1023");
   
    }
    
}
