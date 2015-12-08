package vi.org.imxmppchattest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vi.org.imxmppchattest.R;
import vi.org.imxmppchattest.adapter.ChatAdapter;
import vi.org.imxmppchattest.model.ChatMsgShow;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private Button sendMsg;
    private ImageView addFunction,faceFunction;
    private EditText inputMsg;
    private ListView chatListView;

    private ArrayList<ChatMsgShow> chatList;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout);

        sendMsg = (Button)findViewById(R.id.send_btn);
        sendMsg.setOnClickListener(this);
        addFunction = (ImageView)findViewById(R.id.add_function);
        faceFunction = (ImageView)findViewById(R.id.face_function);
        inputMsg = (EditText)findViewById(R.id.input_msg);
        inputMsg.setOnClickListener(this);
        chatListView = (ListView)findViewById(R.id.chat_lv);

        initAdapter();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:

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

    }

    /**
     * 初始化接收信息函数
     */
    private void initReceiver()
    {

    }

    /**
     * 发送消息内容函数
     * @param content  填写的消息内容
     */
    private void sendMsg(String content)
    {

    }
}
