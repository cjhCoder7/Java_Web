import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080);
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("连接到服务器：" + socket.getInetAddress() + ":" + socket.getPort());

            boolean connected = true;
            while (connected) {
                // 提示用户输入指令
                System.out.print("请输入指令 (FILE_REQUEST/EXIT): ");
                String instruction = keyboard.readLine();

                if (instruction.equalsIgnoreCase("EXIT")) {
                    connected = false;
                    break;
                }

                // 发送 FILE_REQUEST
                PrintWriter writer = new PrintWriter(outputStream, true);
                writer.println("FILE_REQUEST");

                // 接收服务器响应
                BufferedReader serverReader = new BufferedReader(new InputStreamReader(inputStream));
                String response = serverReader.readLine();
                System.out.println("服务器响应：" + response);

                if ("ACCEPT".equalsIgnoreCase(response)) {
                    while (true) {
                        // 输入文件名
                        System.out.print("请输入要传输的文件名 (或输入 EXIT 退出): ");
                        String fileName = keyboard.readLine();

                        if (fileName.equalsIgnoreCase("EXIT")) {
                            writer.println(fileName);
                            break;
                        }

                        // 发送文件内容
                        File file = new File(fileName);
                        if (file.exists()) {
                            // 发送文件名
                            writer.println(fileName);
                            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                // 发送 EOF 标记表示文件传输结束
                                outputStream.write("EOF".getBytes());
                                outputStream.flush();
                                System.out.println("文件 " + fileName + " 已发送！");
                            }
                        } else {
                            System.out.println("文件不存在：" + fileName);
                            writer.println("ERROR: 文件不存在");
                        }
                    }
                } else {
                    System.out.println("服务器拒绝文件传输请求！");
                }
            }
        } catch (IOException e) {
            System.err.println("客户端意外退出：" + e.getMessage());
        }
    }
}