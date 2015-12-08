package vi.org.imxmppchattest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vi.org.imxmppchattest.ConnectServer;
import vi.org.imxmppchattest.R;
import vi.org.imxmppchattest.adapter.ChatAdapter;
import vi.org.imxmppchattest.model.ChatMsgShow;
import vi.org.imxmppchattest.util.Constant;
import vi.org.imxmppchattest.util.PreferencesUtils;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    //发送消息按钮
    private Button sendMsg;
    //左侧加号功能和表情功能
    private ImageView addFunction,faceFunction;
    //输入发送的消息内容
    private EditText inputMsg;
    //显示消息列表
    private ListView chatListView;

    //消息List
    private ArrayList<ChatMsgShow> chatList;
    private ChatAdapter chatAdapter;
    //消息接收、发送时间
    private SimpleDateFormat dateFormat;
    //消息接收广播
    private NewMsgReceiver newMsgReceiver;

    //
    private String toId;
    private String fromId;


    private void initIdData()
    {
        //myself
        toId = getIntent().getStringExtra(Constant.toId);
        //other
        fromId = PreferencesUtils.getSharePreStr(this,"fromId");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout);
        initIdData();

        sendMsg = (Button)findViewById(R.id.send_btn);
        sendMsg.setOnClickListener(this);
        addFunction = (ImageView)findViewById(R.id.add_function);
        faceFunction = (ImageView)findViewById(R.id.face_function);
        inputMsg = (EditText)findViewById(R.id.input_msg);
        inputMsg.setOnClickListener(this);
        chatListView = (ListView)findViewById(R.id.chat_lv);

        dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        initReceiver();
        initAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                if(!inputMsg.getText().toString().equals("") && inputMsg.getText()!=null)
                {
                    sendTextMsg(inputMsg.getText().toString());
                }
                break;
            case R.id.input_msg:

                break;
            default:

                break;
        }
    }

    private void initAdapter()
    {
        chatList = new ArrayList<ChatMsgShow>();
        chatAdapter = new ChatAdapter(this,chatList);
        chatListView.setAdapter(chatAdapter);
    }

    /**
     * 初始化接收信息函数
     */
    private void initReceiver()
    {
        newMsgReceiver = new NewMsgReceiver();
        IntentFilter intentFilter = new IntentFilter(Constant.ACTION_MSG_RECEIVER);
        registerReceiver(newMsgReceiver,intentFilter);
    }

    private class NewMsgReceiver extends BroadcastReceiver{
        public NewMsgReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //取到接收到的数据后，添加到chatMsgList，更新listview

        }
    }

    /**
     * 发送消息内容函数
     * @param content  填写的消息内容
     */
    private void sendTextMsg(String content)
    {
        ChatMsgShow msg = new ChatMsgShow();
        String time = dateFormat.format(new Date());
        msg.setFromUser(fromId);
        msg.setToUser(toId);
        msg.setDate(time);
        msg.setContent(content);
        msg.setIsComing(1);
        msg.setType(Constant.MSG_TYPE_TEXT);

        chatList.add(msg);
        chatAdapter.notifyDataSetChanged();

        final String message = fromId + Constant.SPLIT + toId + Constant.SPLIT + Constant.MSG_TYPE_TEXT
                + Constant.SPLIT + content + Constant.SPLIT + time;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ConnectServer.xmppConnection == null || !ConnectServer.xmppConnection.isConnected()) {
                        throw new XMPPException();
                    }
                    ChatManager chatManager = ConnectServer.xmppConnection.getChatManager();
                    Chat chat = chatManager.createChat(fromId+"@"+ Constant.HOST,null);
                    if(chat != null)
                    {
                        chat.sendMessage(message);
                        Log.i("chatAc","msg sent.");
                    }
                }
                catch (XMPPException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
        inputMsg.setText("");
        Intent intent = new Intent(Constant.ACTION_MSG_RECEIVER);
        sendBroadcast(intent);
    }
}
