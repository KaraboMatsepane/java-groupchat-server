import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            while (!serverSocket.isClosed()){
               Socket clientSocket = serverSocket.accept();
                System.out.println("A new client has connected.");
                //ClientHandler clienthandler = new ClientHandler(clientSocket);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
