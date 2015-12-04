package vi.org.imxmppchattest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //连接服务器按钮
    private Button connect,login,contacts;

    //用户名和密码
    private String name = "vic";
    private String pwd = "123456";

    //连接服务器类对象
    ConnectServer connectServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = (Button)findViewById(R.id.connect_btn);
        connect.setOnClickListener(this);
        login = (Button)findViewById(R.id.login_btn);
        login.setOnClickListener(this);
        contacts = (Button)findViewById(R.id.contacts_btn);
        contacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.connect_btn:
                connectServer = new ConnectServer();
                connectServer.ConnectServer();
                break;
            case R.id.login_btn:
                connectServer.login(name,pwd);
                break;
            case R.id.contacts_btn:
                Intent intent = new Intent(this,FriendListActivity.class);
                startActivity(intent);

                break;
        }
    }
}