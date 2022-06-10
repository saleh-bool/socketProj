package server;

import socket.Receiver;

public class Server {
    public static final int SERVER_PORT = 8000;
    private Receiver receiver;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    private void run() {
        receiver = new Receiver(SERVER_PORT);
        while (true) addServerView();
    }

    private void addServerView() {
        receiver.listen();
        ServerView serverView = new ServerView(receiver.transmitter);
        Thread thread = new Thread(serverView::contact);
        thread.start();
    }
}
