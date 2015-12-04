package vi.org.imxmppchattest;

import android.nfc.Tag;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Created by Vicky on 2015/12/2.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */
public class ConnectServer {
    private String TAG = "ConnectServer";

    private ConnectionConfiguration config;
    public static XMPPConnection xmppConnection;

    /**
     * 连接服务器
     */
    public void ConnectServer()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //服务器地址ip以及端口号：5222是默认
                config = new ConnectionConfiguration("192.168.10.111",5222);
                //是否启动安全验证
                config.setSASLAuthenticationEnabled(false);
                //创建connection连接
                try{
                    xmppConnection = new XMPPConnection(config);
                    xmppConnection.connect();
                    Log.i(TAG, "connect success");
                }
                catch (XMPPException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 登录
     * @param name 用户id
     * @param pwd  用户密码
     * @return
     */
    public void login(final String name,final String pwd)
    {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(xmppConnection != null)
                    {
                        xmppConnection.login(name, pwd);
                        Log.i(TAG,"login success");
                    }
                }
                catch (XMPPException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();*/
        try {
            if(xmppConnection != null)
            {
                xmppConnection.login(name, pwd);
                Log.i(TAG,"login success");
            }
        }
        catch (XMPPException e)
        {
            e.printStackTrace();
        }
    }
}
