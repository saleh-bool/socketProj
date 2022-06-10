package socket;

import java.io.IOException;
import java.net.ServerSocket;

public class Receiver implements AutoCloseable{
    private ServerSocket serverSocket;
    public Transmitter transmitter;

    public Receiver(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Receiver(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Receiver(Receiver receiver) {
        serverSocket = receiver.getServerSocket();
        transmitter = receiver.transmitter;
    }

    public void listen() {
        System.out.println("start listening ...");
        try {
            transmitter = new Transmitter(serverSocket.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connected");
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    //TODO
    protected void finalize() {
        close();
    }

    @Override
    public void close() {
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
