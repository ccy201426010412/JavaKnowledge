package messageQuene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MqClient {

    //生产消息
    public  void produce(String message) throws Exception {
        //该写法可省去自己关闭各种连接
        try (
                //本地的的BrokerServer.SERVICE_PORT 创建SOCKET
                Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            out.println("send:"+message);
            out.flush();
        }
    }

    //消费消息
    public  String consume() throws Exception {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            //先向消息队列发送命令
            out.println("get");
            out.flush();
            //再从消息队列获取一条消息
            String message = in.readLine();
            return message;
        }
    }

    public static void main(String[] args) throws Exception {
        MqClient client = new MqClient();
        Scanner sc=new Scanner(System.in);
        while(sc.hasNextLine())
        {
            String str = sc.nextLine();
            if(str.equals("get")){
                String message = client.consume();
                System.out.println("获取的消息为：" + message);
                continue;
            }else if(str.equals("stop")){
                break;
            }else {
                client.produce(str);
            }
            System.out.println("完成一次");
        }
    }

}
