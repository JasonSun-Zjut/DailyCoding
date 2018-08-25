package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * @author
 * @date /08/25
 */
public class Server {
    private ServerSocketChannel serverChannel;
    private Selector selector;


    public Server() {
        try {
            initServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initServer() throws IOException {
        serverChannel = ServerSocketChannel.open();
        selector = Selector.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(1019));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void startServer() throws IOException {
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    new Thread(new ReadableTask(selectionKey)).start();
                }
            }

        }
    }

    static class ReadableTask implements Runnable {
        private SelectionKey selectionKey;

        public ReadableTask(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        public void run() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            try {
                while (true) {
                    int count = socketChannel.read(byteBuffer);
                    if (count > 0) {
                        byteBuffer.flip();
                        String info = new String(byteBuffer.array());
                        System.out.println("From Server:" + info);
                        byteBuffer.clear();
                    } else if (count == -1) {
                        selectionKey.cancel();
                        socketChannel.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socketChannel != null) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
