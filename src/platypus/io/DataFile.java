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
public class DataFile {
    
    private final String name;
    
    public DataFile(String name){
        this.name = name;
    }
    
    private ObjectInputStream openReadStream() throws IOException{
        File fl = new File(name);
        FileInputStream fis = new FileInputStream(fl);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois;
    }
    
    private ObjectOutputStream openWriteStream(boolean append) throws IOException{
        File fl = new File(name);
        FileOutputStream fos = new FileOutputStream(fl,append);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        return oos;
    }
}
