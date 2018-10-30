import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author
 * @date /10/30
 */
public class SocketServer {
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public SocketServer(int port) throws IOException {
        init(port);
    }

    private void init(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        startServer();
    }

    private void startServer() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            executorService.execute(new Runnable() {
                private InputStream inputStream;
                private BufferedReader bufferedReader;
                @Override
                public void run() {
                    try {
                        inputStream = socket.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String info;
                        while ((info = bufferedReader.readLine()) != null) {
                            System.out.println("服务器接收到了来自" + socket.getInetAddress() + socket.getPort() + "的消息：" + info);
                        }
                        socket.shutdownInput();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                            inputStream.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws IOException {
        SocketServer socketServer=new SocketServer(10086);
    }
}
