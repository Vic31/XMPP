package vi.org.imxmppchattest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vi.org.imxmppchattest.R;
import vi.org.imxmppchattest.model.ChatMsgShow;

/**
 * Created by Vicky on 2015/12/8.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */
public class ChatAdapter extends BaseAdapter {

    private ArrayList<ChatMsgShow> chatList;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewHolder viewHolder;

    public ChatAdapter(Context context, ArrayList<ChatMsgShow> chatList)
    {
        this.context = context;
        this.chatList = chatList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(chatList.size() > 0)
        {
            return chatList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(chatList != null) {
            return chatList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.activity_chat_layout_item,null);
            viewHolder = new ViewHolder();
            viewHolder.chatTime = (TextView)convertView.findViewById(R.id.chat_time);
            //接收的信息
            viewHolder.chatFromIcon = (ImageView)convertView.findViewById(R.id.chatfrom_icon);
            viewHolder.chatFromImage = (ImageView)convertView.findViewById(R.id.chatfrom_img);
            viewHolder.chatFromLocation = (ImageView)convertView.findViewById(R.id.chatfrom_location);
            viewHolder.chatFromText = (TextView)convertView.findViewById(R.id.chatfrom_content);
            //发送的信息
            viewHolder.chatToIcon = (ImageView)convertView.findViewById(R.id.chatto_icon);
            viewHolder.chatToImage = (ImageView)convertView.findViewById(R.id.chatto_img);
            viewHolder.chatToLocation = (ImageView)convertView.findViewById(R.id.chatto_location);
            viewHolder.chatToText = (TextView)convertView.findViewById(R.id.chatto_content);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.chatTime.setText(chatList.get(position).getTime());
        viewHolder.chatFromText.setText(chatList.get(position).getChatFromContent());

        viewHolder.chatToText.setText(chatList.get(position).getChatToContent());

        return convertView;
    }

    class ViewHolder{
        TextView chatTime;

        ImageView chatFromIcon;
        TextView chatFromText;
        ImageView chatFromImage;
        ImageView chatFromLocation;

        ImageView chatToIcon;
        TextView chatToText;
        ImageView chatToImage;
        ImageView chatToLocation;

    }
}
