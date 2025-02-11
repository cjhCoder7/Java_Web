import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("服务器已启动，等待客户端连接...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端已连接：" + socket.getInetAddress() + ":" + socket.getPort());

                new Thread(() -> {
                    try (InputStream inputStream = socket.getInputStream();
                            OutputStream outputStream = socket.getOutputStream()) {

                        boolean clientConnected = true;
                        while (clientConnected && !Thread.interrupted()) {
                            // 读取客户端指令
                            BufferedReader clientReader = new BufferedReader(new InputStreamReader(inputStream));
                            String instruction = clientReader.readLine();

                            if ("FILE_REQUEST".equalsIgnoreCase(instruction)) {
                                // 提示服务端接受或拒绝
                                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                                System.out.print("客户端请求文件传输，是否接受 (ACCEPT/REFUSE)? ");
                                String response = keyboard.readLine();

                                // 发送响应
                                PrintWriter writer = new PrintWriter(outputStream, true);
                                writer.println(response);

                                if ("ACCEPT".equalsIgnoreCase(response)) {
                                    while (clientConnected) {
                                        // 接收文件名
                                        String fileName = clientReader.readLine();
                                        if ("EXIT".equalsIgnoreCase(fileName)) {
                                            break;
                                        }

                                        if ("ERROR: 文件不存在".equalsIgnoreCase(fileName)) {
                                            System.out.println("文件不存在");
                                            continue;
                                        }

                                        // 创建文件以保存接收到的数据
                                        FileOutputStream fileOutputStream = new FileOutputStream(
                                                "received_" + fileName);

                                        // 接收文件内容
                                        byte[] buffer = new byte[4096];
                                        int bytesRead;
                                        boolean isEOF = false;
                                        while (!isEOF && (bytesRead = inputStream.read(buffer)) != -1) {
                                            // 检查是否是 EOF 标记
                                            String dataSegment = new String(buffer, 0, bytesRead);
                                            int eofIndex = dataSegment.indexOf("EOF");
                                            if (eofIndex != -1) {
                                                // 移除 EOF 标记并保存剩余数据
                                                fileOutputStream.write(buffer, 0, eofIndex);
                                                isEOF = true;
                                            } else {
                                                fileOutputStream.write(buffer, 0, bytesRead);
                                            }
                                        }

                                        fileOutputStream.close();
                                        System.out.println("文件 " + fileName + " 已接收！");
                                    }
                                }
                            }
                            if ("EXIT".equalsIgnoreCase(instruction)) {
                                clientConnected = false;
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("客户端意外退出：" + e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}