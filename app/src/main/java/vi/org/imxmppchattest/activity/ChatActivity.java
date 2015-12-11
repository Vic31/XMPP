package vi.org.imxmppchattest.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vi.org.imxmppchattest.ConnectServer;
import vi.org.imxmppchattest.InterfaceListener.MyMessageListener;
import vi.org.imxmppchattest.R;
import vi.org.imxmppchattest.adapter.ChatAdapter;
import vi.org.imxmppchattest.model.ChatMsgShow;
import vi.org.imxmppchattest.util.Constant;
import vi.org.imxmppchattest.util.PreferencesUtils;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    //发送消息按钮
    private ImageView sendMsg;
    //左侧附加功能和表情功能
    private ImageView addFunction,faceFunction;
    //左侧附加布局
    private LinearLayout addFunctionLayout;
    //左侧附加布局的详细内容：
    private LinearLayout pictureLayout,locationLayout,otherLayout;
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

    //自己的Id和接收方的Id
    private String Me;
    private String You;

    //图片位置
    private Uri mPhotoPath;
    private Bitmap bmp;

    /**
     * 初始化Id信息
     */
    private void initIdData()
    {
        //myself
        You = getIntent().getStringExtra(Constant.fromId);
        //other
        Me = PreferencesUtils.getSharePreStr(this,Constant.toId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout);

        initIdData();
        initView();
        initReceiver();
        initAdapter();
        dateFormat = new SimpleDateFormat("MM-dd HH:mm");

        initPictureData();
    }

    /**
     * 初始化控件
     */
    private void initView()
    {
        sendMsg = (ImageView)findViewById(R.id.send_btn);
        sendMsg.setOnClickListener(this);
        addFunction = (ImageView)findViewById(R.id.add_function);
        addFunction.setOnClickListener(this);
        faceFunction = (ImageView)findViewById(R.id.face_function);
        faceFunction.setOnClickListener(this);
        inputMsg = (EditText)findViewById(R.id.input_msg);
        inputMsg.setOnClickListener(this);
        chatListView = (ListView)findViewById(R.id.chat_lv);

        addFunctionLayout = (LinearLayout)findViewById(R.id.add_function_view);
        pictureLayout = (LinearLayout)addFunctionLayout.findViewById(R.id.send_picture);
        pictureLayout.setOnClickListener(this);
        locationLayout = (LinearLayout)addFunctionLayout.findViewById(R.id.send_location);
        locationLayout.setOnClickListener(this);
    }

    private void initPictureData()
    {
        mPhotoPath = Uri.fromFile(new File(
                android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), "xmpp_camera.jpg"));

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
                if(addFunctionLayout.getVisibility()==View.VISIBLE)
                {
                    addFunctionLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.add_function:
                hideSoftInputView();
                if(addFunctionLayout.getVisibility()==View.GONE)
                {
                    addFunctionLayout.setVisibility(View.VISIBLE);
                }
                else {
                    addFunctionLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.face_function:

                break;
            case R.id.send_picture:
                chooseImage();
            default:

                break;
        }
    }

    /**
     * 选择何种方式图片：相机 图库
     */
    public void chooseImage()
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.select_dialog_item,getResources().getStringArray(R.array.select_photo)
        );
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_photo);
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    switch (item) {
                        case Constant.CAMERA:
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoPath);
                            startActivityForResult(intent, Constant.CAMERA);
                            break;
                        case Constant.GALLERY:
                            Intent intent2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent2.setType("image/*");
                            startActivityForResult(intent2, Constant.GALLERY);
                            break;

                        default:
                            break;
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 结果返回信息（暂时只在选择图片时用到）
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String prefix;
        if(resultCode != RESULT_CANCELED)
        {
            BitmapFactory.Options resample = new BitmapFactory.Options();;
            switch (requestCode)
            {
                case Constant.CAMERA:
                    String pathCamera = mPhotoPath.getPath();
                    try {
                        //bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mPhotoPath);
                        resample.inJustDecodeBounds = true;
                        bmp = BitmapFactory.decodeFile(pathCamera,resample);
                        resample.inJustDecodeBounds = false;
                        if(resample.outWidth > 1200 || resample.outHeight>800)
                        {
                            int heightS = resample.outHeight/200;
                            int widthS = resample.outWidth/400;
                            resample.inSampleSize = (heightS<widthS)?heightS:widthS;
                            bmp = BitmapFactory.decodeFile(pathCamera, resample);
                        }
                        //bmp = getSmallImage(bmp);
                        bmp = getCorrectOrientation(bmp, pathCamera);

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case Constant.GALLERY:
                    String picPath = null;
                    Uri selectImage = data.getData();
                    Log.i("path pic",selectImage.getPath());
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = ChatActivity.this.getContentResolver().query(selectImage, filePathColumn,
                            null, null, null);
                    if(null == cursor)
                    {
                        picPath = selectImage.getPath();
                    }
                    else
                    {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picPath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                    resample.inJustDecodeBounds = true;
                    bmp = BitmapFactory.decodeFile(picPath,resample);
                    resample.inJustDecodeBounds = false;
                    if(resample.outWidth > 3800 && resample.outHeight>3000)
                    {
                        Toast.makeText(this,R.string.pic_outbound,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int height = resample.outHeight;
                        int width = resample.outWidth;
                        if(resample.outHeight>800)
                        {
                            height = resample.outHeight/200;
                        }
                        if(resample.outWidth > 1000)
                        {
                            width = resample.outWidth/400;
                        }
                        resample.inSampleSize = (height<width)?height:width;
                        bmp = BitmapFactory.decodeFile(picPath, resample);
                        //bmp = getSmallImage(bmp);
                        bmp = getCorrectOrientation(bmp, picPath);

                        /*galleryCacheImage = Uri.fromFile(f).getPath();
                        try{
                            if (f.exists())
                                f.delete();
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }*/
                    }
                    break;
                default:
                    break;
            }

        }
    }

    /**
     * 得到图片正确的方向，无论照相机如何旋转
     * @param bmp
     * @param path
     * @return
     */
    public Bitmap getCorrectOrientation(Bitmap bmp,String path)
    {
        try
        {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,1);

            Matrix m = new Matrix();
            if(orientation == 3)
            {
                m.postRotate(180);
            }
            else if(orientation == 6)
            {
                m.postRotate(90);
            }
            else if(orientation == 8)
            {
                m.postRotate(270);
            }
            else{}
            bmp = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),
                    m,true);
            return bmp;
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return bmp;
    }

    /**
     * 压缩图片
     * @param bmp
     * @return
     */
    public Bitmap getSmallImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int size = baos.toByteArray().length/1024;
        Log.i("bmp size", size + "");
        //if picture's size > *KB
        while(baos.toByteArray().length/1024 > 100)
        {
            baos.reset();
            //compress 50%
            bmp.compress(Bitmap.CompressFormat.JPEG,50,baos);
            Log.i("bmp size in", baos.toByteArray().length/1024 + "");
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        bmp = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bmp;
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化适配器信息
     */
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
        registerReceiver(newMsgReceiver, intentFilter);
    }

    /**
     * 销毁Activity时，注销Receiver
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(newMsgReceiver);
        super.onDestroy();
    }

    /**
     * 新消息接收器Broadcast
     */
    private class NewMsgReceiver extends BroadcastReceiver{
        public NewMsgReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //取到接收到的数据后，添加到chatMsgList，更新listview
            Bundle bundle = intent.getBundleExtra(Constant.MESSAGE_RECEIVE);
            ChatMsgShow message;
            message = (ChatMsgShow)bundle.getSerializable(Constant.MESSAGE_RECEIVE);
            chatList.add(message);
            chatAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 集合数据到ChatMsgShow对象中，并返回给sendMsg函数
     * @param content
     * @return
     */
    private ChatMsgShow setMsgData(String content)
    {
        ChatMsgShow msg = new ChatMsgShow();
        String time = dateFormat.format(new Date());
        msg.setFromUser(You);
        msg.setToUser(Me);
        msg.setDate(time);
        msg.setContent(content);
        msg.setIsComing(1);
        return msg;
    }

    /**
     * 发送消息内容函数
     * @param content  填写的消息内容
     */
    private void sendTextMsg(String content)
    {
        ChatMsgShow msg = setMsgData(content);
        msg.setType(Constant.MSG_TYPE_TEXT);
        //将此条消息加到list数据中
        chatList.add(msg);
        chatAdapter.notifyDataSetChanged();
        //You:别人  Me:我
        final String message = You + Constant.SPLIT + Me + Constant.SPLIT + Constant.MSG_TYPE_TEXT
                + Constant.SPLIT + content + Constant.SPLIT + msg.getDate();
        sendMsg(message);
    }

    private void sendImageMsg(String content)
    {
        ChatMsgShow msg = setMsgData(content);
        msg.setType(Constant.MSG_TYPE_IMG);
        chatList.add(msg);
        chatAdapter.notifyDataSetChanged();
        final String message = You + Constant.SPLIT + Me + Constant.SPLIT + Constant.MSG_TYPE_IMG
                + Constant.SPLIT + content + Constant.SPLIT + msg.getDate();
        sendMsg(message);
    }

    /**
     * 发送信息
     * @param message
     */
    private void sendMsg(final String message)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                if (ConnectServer.xmppConnection == null || !ConnectServer.xmppConnection.isConnected()) {
                    throw new XMPPException();
                }
                ChatManager chatManager = ConnectServer.xmppConnection.getChatManager();
                Chat chat = chatManager.createChat(You + "@" + Constant.HOST, null);
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
    }
}
