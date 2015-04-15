# TCPlatypusChat
_Java terminal Chat client and multi-client-server using full TCP connections with sockets. Supporting users, logs and client-server commands._

##Usage
You can setup the application to act as a server, or as a client

_Server_

`java -jar TCPlatypusChat.jar -l port`

example: `java -jar TCPlatypusChat.jar -l 8000`

_Client_

`java -jar TCPlatypusChat.jar -c hostname port`

example: `java -jar TCPlatypusChat.jar -c eldoctordeldesierto.com 8000`

## Testing server
You can test this application by connecting to the given build (link below) and using the following command

`java -jar TCPlatypusChat.jar -c eldoctordeldesierto.com 8000`


## Building the source code
NetBeans 8.0.1 was used, and JDK 7 environment. However any java compiler should work if source files are setup correctly.


## Author
Jaime Hidalgo García

## License
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
