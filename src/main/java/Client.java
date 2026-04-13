import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket sock;
    private BufferedWriter bWriter;
    private BufferedReader bReader;
    private String userName;

    public Client(Socket socket, String userName){
        try{
            this.sock = socket;
            this.bWriter = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            this.bReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.userName = userName;
        } catch (IOException e){
            closeEverything(this.sock, this.bReader, this.bWriter);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        Client client = new Client(socket,  username);

        client.sendMessage();
        client.listenForMessage();
    }

    public void sendMessage(){
        try{
            this.bWriter.write(this.userName);
            this.bWriter.newLine();
            this.bWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(this.sock.isConnected()) {
                String messageToSend = scanner.nextLine();

                this.bWriter.write(messageToSend);
                this.bWriter.newLine();
                this.bWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(this.sock, this.bReader, this.bWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageToReceive;

                while (sock.isConnected()){
                    try{
                        messageToReceive = bReader.readLine();
                        System.out.println(messageToReceive);
                    } catch (IOException e){
                        closeEverything(sock, bReader, bWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bReader, BufferedWriter bWriter){

        try{
            if (bReader != null){
                bReader.close();
            }
            if (bWriter != null){
                bWriter.close();
            }

            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
