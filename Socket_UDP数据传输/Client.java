import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            while (line != null) {
                byte[] data = line.getBytes();
                // 发送数据包
                DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), 8080);
                socket.send(packet);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}