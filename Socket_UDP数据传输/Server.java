import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(8080)) {
            while (true) {
                // UDP缓冲区
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                System.out.println("收到客户端请求：" + packet.getAddress().getHostAddress() + ":" + packet.getPort());
                System.out.println("收到客户端数据包：" + new String(packet.getData(), 0, packet.getLength()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}