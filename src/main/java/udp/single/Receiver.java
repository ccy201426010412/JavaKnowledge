package udp.single;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Receiver {
    //从端口10000进行监听，并获取得到的对象
    public static void main(String[] args) throws Exception {
        int port = 10000;
        //建立监听端口的类
        DatagramSocket receive = new DatagramSocket(port);
        //创建用于接收的报文包，大小最大为bytes，太小会导致只能接收报文中的一部分
        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes,bytes.length);
        //一直监听
        while (true){
            //进行接收
            receive.receive(dp);
            byte[] data = dp.getData();
            System.out.println(new String(data,0,data.length));
        }
    }
}
