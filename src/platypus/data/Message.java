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
package platypus.data;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author JaimehRubiks
 */
public class Message implements Serializable{
    
    private final String time;
    private final String user;
    private final String message;
    
    public Message(String user, String str){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        this.time = df.format(dateobj);
        this.user = user;
        this.message = str;
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append('[').append(time).append("] ");
        str.append( user).append(": ");
        str.append(message);
        return str.toString();
    }
    
    public String getMessage(){
        return message;
    }
    
    public String getSender(){
        return user;
    }
    
}
