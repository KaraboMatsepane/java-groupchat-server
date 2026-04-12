import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    public static List<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;  //client socket on the server side
    private BufferedReader bReader;
    private BufferedWriter bWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) throws IOException {
        try{
            this.socket = socket;
            this.bReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.clientUsername = bReader.readLine();
            clientHandlers .add(this);
            broadcastMessage("SERVER: " + this.clientUsername + " has entered the chat!");
        } catch (IOException e){
            closeEverything(this.socket, this.bReader, this.bWriter);
        }

    }


    @Override
    public void run(){
        String messageFromClient;

        while (this.socket.isConnected()){
            try{
                messageFromClient = bReader.readLine();
            }catch (IOException e){
                closeEverything(this.socket, this.bReader, bWriter);
                break;
            }

        }
    }

    public void broadcastMessage(String messageToSend){
        for (ClientHandler client: clientHandlers){
            try{
                if(!client.clientUsername.equals(this.clientUsername)){
                    client.bWriter.write(messageToSend);
                    client.bWriter.newLine();
                    client.bWriter.flush();
                }
            } catch (IOException e){
                closeEverything(this.socket, bReader, bWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + this.clientUsername + " has left the chat! ");
    }

    public void closeEverything(Socket socket, BufferedReader bReader, BufferedWriter bWriter){
        try{
            if(bReader != null){
                bReader.close();
            }
            if(bWriter != null){
                bWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
