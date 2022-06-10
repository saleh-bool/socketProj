package socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Transmitter {
    private Socket socket     = null;
    private DataInputStream inputStream   = null;
    private DataOutputStream outputStream = null;


    public Transmitter() {}

    public Transmitter(Transmitter transmitter) {
        socket = transmitter.socket;
        inputStream = transmitter.inputStream;
        outputStream = transmitter.outputStream;
    }

    Transmitter(Socket socket) {
        this.socket = socket;
        setInOut(socket);
    }

    public void connect(int  port) {
        try {
            socket = new Socket("127.0.0.1",port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setInOut(socket);
    }

    private void setInOut(Socket socket) {
        try {
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if(socket != null) socket.close();
            if(inputStream != null) inputStream.close();
            if(outputStream != null) outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String string) {
        try {
            outputStream.writeUTF(string);
        } catch (IOException e ){
            e.printStackTrace();
        }
    }

    public String receive() {
        String string = "Not found";
        try {
            string = inputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @Override
    protected void finalize() {
        disconnect();
    }
}
