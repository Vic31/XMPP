package vi.org.imxmppchattest.util;

/**
 * Created by Vicky on 2015/12/8.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */
public class Constant {

    public static String HOST = "192.168.10.111";
    public static int port = 5222;

    //取别人的Id
    public static String fromId = "fromId";
    public static String pwd = "pwd";
    //自己
    public static String toId = "toId";

    //Broadcast action 接收消息器
    public static final String ACTION_MSG_RECEIVER = "vi.org.msg.receiver";

    public static final String MSG_TYPE_TEXT="msg_type_text";//文本消息
    public static final String MSG_TYPE_IMG="msg_type_img";//图片
    public static final String MSG_TYPE_VOICE="msg_type_voice";//语音
    public static final String MSG_TYPE_LOCATION="msg_type_location";//位置

    //message分隔符
    public static final String SPLIT = "卍";

    //接收消息bundle和intent的关键字
    public static final String MESSAGE_RECEIVE = "msg";

}
