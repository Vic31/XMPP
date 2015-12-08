package vi.org.imxmppchattest.model;

/**
 * Created by Vicky on 2015/12/8.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */

/**
 * 显示消息内容类
 */
public class ChatMsgShow {
    private String time;

    private String chatFromIcon;
    private String chatFromImg;
    private String chatFromLocation;
    private String chatFromContent;

    private String chatToIcon;
    private String chatToImg;
    private String chatToLocation;
    private String chatToContent;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChatFromIcon() {
        return chatFromIcon;
    }

    public void setChatFromIcon(String chatFromIcon) {
        this.chatFromIcon = chatFromIcon;
    }

    public String getChatFromImg() {
        return chatFromImg;
    }

    public void setChatFromImg(String chatFromImg) {
        this.chatFromImg = chatFromImg;
    }

    public String getChatFromLocation() {
        return chatFromLocation;
    }

    public void setChatFromLocation(String chatFromLocation) {
        this.chatFromLocation = chatFromLocation;
    }

    public String getChatFromContent() {
        return chatFromContent;
    }

    public void setChatFromContent(String chatFromContent) {
        this.chatFromContent = chatFromContent;
    }

    public String getChatToIcon() {
        return chatToIcon;
    }

    public void setChatToIcon(String chatToIcon) {
        this.chatToIcon = chatToIcon;
    }

    public String getChatToImg() {
        return chatToImg;
    }

    public void setChatToImg(String chatToImg) {
        this.chatToImg = chatToImg;
    }

    public String getChatToLocation() {
        return chatToLocation;
    }

    public void setChatToLocation(String chatToLocation) {
        this.chatToLocation = chatToLocation;
    }

    public String getChatToContent() {
        return chatToContent;
    }

    public void setChatToContent(String chatToContent) {
        this.chatToContent = chatToContent;
    }
}
