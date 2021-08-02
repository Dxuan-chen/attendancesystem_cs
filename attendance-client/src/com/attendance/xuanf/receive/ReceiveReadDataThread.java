package com.attendance.xuanf.receive;

import com.attendance.xuanf.listener.IReceivedataCallBackListener;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 功能描述：
 * 不断的监听读取服务器发送过来的数据，一读取到数据，立即回调监听方法
 * @author 陈铉锋
 * @date 2021/7/29
 */
public class ReceiveReadDataThread extends Thread {

    private ObjectInputStream objectInputStream;
    private IReceivedataCallBackListener listener;

    public ReceiveReadDataThread(ObjectInputStream objectInputStream, IReceivedataCallBackListener listener){
        this.objectInputStream = objectInputStream;
        this.listener = listener;
    }

    /*
     * 子线程执行的主体
     * */
    @Override
    public void run() {

        while (true) {
            try {
                if (this.isInterrupted()){
                    this.interrupt();
                    break;
                }
                //读取客户端发送过来的数据
                Object readObject = objectInputStream.readObject();
                //调用回调方法，通过控制器接受此数据
                listener.receviceCallBackListener(readObject);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
