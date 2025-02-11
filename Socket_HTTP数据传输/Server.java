import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            while (!serverSocket.isClosed()) {
                int i = inputStream.read();
                if (i == -1) {
                    break;
                }
                System.out.print((char) i);

                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                String html = "<html><body><h1>Hello World</h1></body></html>";
                writer.write("HTTP/1.1 200 OK\r\n");
                writer.write("Content-Type: text/html; charset=UTF-8\r\n");
                writer.write("Content-Length: " + html.length() + "\r\n");
                writer.write("\r\n");
                writer.write(html);
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}