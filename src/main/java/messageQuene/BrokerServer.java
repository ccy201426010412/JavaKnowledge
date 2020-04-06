package messageQuene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//开启服务器线程，以接收从客户端发送过来的信息
public class BrokerServer implements Runnable {

    //设置服务器端口
    public static int SERVICE_PORT = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

        )
        {
            String str;
            while ((str = in.readLine())!= null) {
                System.out.println("接收到原始数据：" + str);

                if (str.equals("get")) { //CONSUME 表示要消费一条消息
                    //从消息队列中消费一条消息
                    String message = Broker.consume();
                    out.println(message);
                    out.flush();
                } else if (str.contains("send:")){
                    Broker.produce(str);
                }else {
                    System.out.println("原始数据:"+str+"没有遵循协议,不提供相关服务");
                }
            }
            System.out.println("线程结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(SERVICE_PORT);
        while (true) {
            System.out.println("开始建立新线程");
            BrokerServer brokerServer = new BrokerServer(server.accept());
            new Thread(brokerServer).start();
            System.out.println("建立成功");
        }
    }
}