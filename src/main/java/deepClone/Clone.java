package deepClone;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.io.*;

public class Clone {
    public static void main(String[] args){
        Person a = new Person();
        a.a = 1;
        Test test = new Test();
        test.person = a;
        test.name = "ccy";

        //手写进行深拷贝
        Test first = new Test();
        first.name = test.name;
        Person firatPerson = new Person();
        firatPerson.a = test.person.a;
        first.person = firatPerson;

        //利用序列化
        Test mm = (Test)SerializationUtils.clone(test);
        try {
            // 写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(test);
            obs.close();

            // 分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            // 返回生成的新对象
            Test second = (Test) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //利用fastjson
        String json = JSON.toJSONString(test);
        Test three = JSON.parseObject(json, Test.class);
        System.out.println(three == test);
    }
}

class Test implements Serializable {
    String name;
    Person person;
}

class Person implements Serializable{
    int a;
}