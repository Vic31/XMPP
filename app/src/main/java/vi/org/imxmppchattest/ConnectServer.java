package vi.org.imxmppchattest;

import android.nfc.Tag;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import vi.org.imxmppchattest.util.Constant;
import vi.org.imxmppchattest.util.PreferencesUtils;

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
                config = new ConnectionConfiguration(Constant.HOST,Constant.port);
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
        try {
            if(xmppConnection != null)
            {
                xmppConnection.login(name, pwd);
                //type,status,priority,mode
                //在qqdemo里面取到的是“online”这个参数
                Presence presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.dnd);
                xmppConnection.sendPacket(presence);
            }
        }
        catch (XMPPException e)
        {
            e.printStackTrace();
        }
        if(xmppConnection.isAuthenticated()) {
            Log.i(TAG, "login success");
        }
        else {
            Log.i(TAG, "login failed");
        }
    }

    /**
     * 注销当前用户
     *
     * @param connection
     * @return
     */
    public static boolean deleteAccount(XMPPConnection connection)
    {
        try {
            connection.getAccountManager().deleteAccount();
            Log.i("logout","logout success");
            return true;
        }catch (XMPPException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}

