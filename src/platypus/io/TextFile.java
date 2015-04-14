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
package platypus.io;

import java.io.*;
/**
 *
 * @author JaimehRubiks
 */
public class TextFile {
    
    private final String name;
    
    public TextFile(String name){
        this.name = name;
    }
    
    private BufferedReader openReadStream() throws IOException{
        File fl = new File(name);
        FileReader fr = new FileReader(fl);
        BufferedReader br = new BufferedReader(fr);
        return br;
    }
    
    private PrintWriter openWriteStream(boolean append) throws IOException{
        File fl = new File(name);
        FileWriter fw = new FileWriter(fl,append);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        return pw;
    }
    
    public void writeString(String str, boolean append){
        try(
            PrintWriter pw = openWriteStream(append);
        ){
            pw.write(str+"\r\n");
        } catch(IOException ioe){
            
        }
        
    }
    
    public void writeString(String str){
        writeString(str,false);
    }
    
    public String readString(){
        StringBuilder str = new StringBuilder();
        String temp;
        try(
            BufferedReader br = openReadStream();
        ){
            while(true){
                temp = br.readLine();
                str.append(temp);
                str.append('\n');
            }
        } catch(IOException ioe){
            
        }
        return str.toString();
    }
    
    
    
    
    
}
