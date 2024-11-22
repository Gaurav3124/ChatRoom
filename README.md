
# Chat Room Application

A simple multithreaded chat room application implemented in Java. This project demonstrates socket programming and thread management, allowing multiple clients to communicate with each other via a central server.

---

## Features

- **Multi-client Support**: Multiple clients can connect to the server simultaneously.
- **Broadcast Messaging**: Messages from a client are broadcast to all other connected clients.
- **Nickname Management**:
  - Clients can set or change their nickname using the `/nick` command.
  - Nickname changes are broadcast to all clients.
- **Leave Option**: Clients can gracefully exit the chat using the `/leave` command, notifying other participants.
- **Threaded Client Handling**: Each client connection is handled on a separate thread to ensure smooth communication.

---

## Technologies Used

- **Java**:
  - `Socket` and `ServerSocket` for network communication.
  - `BufferedReader` and `PrintWriter` for input and output.
  - `ExecutorService` for managing client threads.
- **Multithreading** for handling concurrent client connections.
- **Maven** (optional, if you use it) for project dependency management.

---

## Getting Started

### Prerequisites
- **Java Development Kit (JDK)** 8 or later.
- A terminal or IDE to run the program.

---

### Running the Server

1. Compile and run the `Server` class:
   ```bash
   javac Server/Server.java
   java Server.Server
   ```
2. The server will start listening on port `9090` by default.

---

### Running a Client

1. Compile and run the `Client` class (if implemented separately):
   ```bash
   javac Client/Client.java
   java Client.Client
   ```
2. Connect to the server using the server's IP address and port number.

---

## Commands

### Available Client Commands

- **Set Nickname**:  
  Use `/nick <new_nickname>` to set or change your nickname.  
  Example:  
  ```
  /nick Alice
  ```
- **Leave the Chat**:  
  Use `/leave` to disconnect from the server.  

---

## Project Structure

```plaintext
.
├── Server/
│   ├── Server.java                  # Main server implementation
│   ├── ClientConnectionHandler.java # Handles individual client connections
├── Client/                          # (Optional, if implemented)
│   ├── Client.java                  # Client-side application
├── .gitignore                       # Excluded files and directories
├── README.md                        # Project documentation
```

---

## Example Usage

1. Start the server:
   ```bash
   java Server.Server
   ```
   Output:
   ```
   Server started on port 9090
   ```

2. Start multiple clients:
   ```bash
   java Client.Client
   ```
   Output on client connection:
   ```
   Please enter a valid nickname:
   Alice
   Alice joined the chat!
   ```

3. Send messages:
   ```
   Hello everyone!
   ```
   Output on other clients:
   ```
   Alice: Hello everyone!
   ```

---

## Future Enhancements

- Implement private messaging (`/msg <nickname> <message>`).
- Add a user list command to display connected clients (`/list`).
- GUI client for a more user-friendly experience.
- Secure communication using TLS/SSL.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
