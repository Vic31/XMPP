package vi.org.imxmppchattest.InterfaceListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import vi.org.imxmppchattest.model.ChatMsgShow;
import vi.org.imxmppchattest.util.Constant;

/**
 * Created by Vicky on 2015/12/9.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 *
 * 接收消息监听
 */
public class MyMessageListener implements MessageListener{

    private Context context;
    public MyMessageListener(Context context)
    {
        this.context = context;
    }

    @Override
    public void processMessage(Chat chat, Message message) {

        String msgBody = message.getBody();
        if(TextUtils.isEmpty(msgBody))
        {
            return;
        }
        String[] msgs = msgBody.split(Constant.SPLIT);
        //接收者
        String to = msgs[0];
        //发送者
        String from = msgs[1];
        String msgType = msgs[2];
        String msgContent = msgs[3];
        String msgTime = msgs[4];
        if(msgType.equals(Constant.MSG_TYPE_TEXT))
        {
            ChatMsgShow messageShow = new ChatMsgShow();
            messageShow.setType(msgType);
            //接收的消息
            messageShow.setIsComing(0);
            messageShow.setContent(msgContent);
            messageShow.setDate(msgTime);
            messageShow.setFromUser(from);
            messageShow.setToUser(to);
            sendMessageBroadcast(messageShow);
        }
    }

    private void sendMessageBroadcast(ChatMsgShow message)
    {
        Intent intent = new Intent(Constant.ACTION_MSG_RECEIVER);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MESSAGE_RECEIVE,message);
        intent.putExtra(Constant.MESSAGE_RECEIVE,bundle);
        context.sendBroadcast(intent);
    }
}
