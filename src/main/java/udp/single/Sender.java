package udp.single;

import java.net.*;
import java.util.Scanner;

public class Sender {
    //从键盘获取输入，如果是stop则停止，否则进行udp发送
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        //服务器IP---如果需要广播，则填写255.255.255.255
        InetAddress address = InetAddress.getByName("127.0.0.1");
        //服务器端口号
        int port = 10000;
        //Java提供的进行UDP发送的类
        DatagramSocket send = new DatagramSocket();
        while (true){
            String s = sc.nextLine();
            if("stop".equals(s)){
                break;
            }
            byte[] bytes = s.getBytes();
            //创建要发送的报文
            DatagramPacket dp = new DatagramPacket(bytes,bytes.length,address,port);
            send.send(dp);
        }
        //发送结束之后要关闭
        send.close();
    }
}
