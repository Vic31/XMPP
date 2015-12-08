package vi.org.imxmppchattest.model;

/**
 * Created by Vicky on 2015/12/8.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */

import java.io.Serializable;

/**
 * 显示消息内容类
 */
public class ChatMsgShow implements Serializable{

    private int msgId;//id
    private String fromUser;//别人
    private String toUser;//自己
    private String type;//信息类型
    private String content;//信息内容
    private int isComing;//0表接收的消息，1表发送的消息
    private String date;//时间

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsComing() {
        return isComing;
    }

    public void setIsComing(int isComing) {
        this.isComing = isComing;
    }
}
