package com.attendance.xuanf.listener;

/**
 * 功能描述：
 * 当读取到客户端的数据，回调接口立即起作用，将数据返回给控制器
 */
public interface IReceivedataCallBackListener {

    //定义一个回调方法
    //参数：传输网络数据
    public void receviceCallBackListener(Object obj);
}
