package com.attendance.xuanf.utils;

import com.attendance.xuanf.listener.IReceivedataCallBackListener;
import com.attendance.xuanf.receive.ReceiveReadDataThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能描述：服务器
 * 1、初始化对象流
 * 2、启动读取数据的线程类
 * 3、实现写对象操作方法
 */
public class SocketUtils {
    ReentrantLock lock = new ReentrantLock();

    private ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    ReceiveReadDataThread receiveReadDataThread = null;

    public SocketUtils(Socket socket, IReceivedataCallBackListener listener){
        try{
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            //启动读取数据的子线程
            receiveReadDataThread = new ReceiveReadDataThread(objectInputStream,listener);
            receiveReadDataThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 实现写对象操作
     * */
    public void writeObject(HashMap<String,Object> responseData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try{
                    objectOutputStream.writeObject(responseData);
                    objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }).start();
    }

    public void connectionClose() {
        receiveReadDataThread.interrupt();

    }
}
