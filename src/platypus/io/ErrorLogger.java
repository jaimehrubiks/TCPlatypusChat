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

/**
 *
 * @author JaimehRubiks
 */
public class ErrorLogger {
    
    private static final TextFile logger = new TextFile("Log.txt");
    
    public static void toFile(String str){
        toFile("Error",str);
    }
    
    public synchronized static void toFile(String type, String str){
        logger.writeString(type+": "+str, true);
    }
    
    public static void toScreen(String str){
        System.out.println(str);
        toFile("Log",str);
    }
}
