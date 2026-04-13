# java-groupchat-server

# Java TCP Chat Application

A simple multi-client chat application built in Java using raw TCP sockets. This project was created to learn the fundamentals of socket programming.

---

## What I Learned

- How TCP sockets work (ServerSocket, Socket)
- How a server accepts and manages multiple clients using threads
- How to send and receive data over a network using BufferedReader / BufferedWriter
- How to broadcast messages to multiple connected clients
- How to handle client disconnections gracefully

---

## Project Structure

```
├── Server.java          # Starts the server and listens for incoming connections
├── ClientHandler.java   # Manages each connected client in its own thread
└── Client.java          # The client application users run to join the chat
```

---

## How It Works

1. The **Server** opens a `ServerSocket` on port `9999` and waits for connections.
2. When a client connects, a new **ClientHandler** is spawned in its own thread.
3. The client sends its username first, then can send messages freely.
4. The **ClientHandler** reads incoming messages and broadcasts them to all other connected clients.
5. When a client disconnects, the server notifies the remaining clients and removes the handler.

---

## How to Run

### 1. Compile

```bash
javac Server.java ClientHandler.java Client.java
```

### 2. Start the server

```bash
java Server
```

### 3. Connect clients (run in separate terminals)

```bash
java Client
```

You will be prompted to enter a username. Open as many terminals as you like — each one is a separate client.

---

## Key Concepts Used

| Concept | Class Used |
|---|---|
| Server socket | `java.net.ServerSocket` |
| Client socket | `java.net.Socket` |
| Reading from stream | `BufferedReader`, `InputStreamReader` |
| Writing to stream | `BufferedWriter`, `OutputStreamWriter` |
| Concurrency | `java.lang.Thread`, `Runnable` |

---

## Limitations

- Runs on localhost only (no LAN/internet support out of the box)
- No encryption — messages are sent as plain text
- No chat history — messages are not persisted anywhere
- No private messaging — all messages are broadcast to everyone