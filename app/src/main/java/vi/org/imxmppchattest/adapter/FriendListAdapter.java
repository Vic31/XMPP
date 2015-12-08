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
import vi.org.imxmppchattest.model.Friends;

/**
 * Created by Vicky on 2015/12/2.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */
public class FriendListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Friends> friendsArrayList;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public FriendListAdapter(Context context,ArrayList<Friends> friendsArrayList)
    {
        this.context = context;
        this.friendsArrayList = friendsArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(friendsArrayList == null)
        {
            return 0;
        }
        return friendsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if(friendsArrayList == null)
        {
            return null;
        }
        return friendsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.friend_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.friendImage  = (ImageView)convertView.findViewById(R.id.friend_image);
            viewHolder.name = (TextView)convertView.findViewById(R.id.friend_name);
            viewHolder.status = (TextView)convertView.findViewById(R.id.friend_status);
            viewHolder.mood = (TextView)convertView.findViewById(R.id.friend_mood);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.friendImage.setImageResource(R.mipmap.ic_launcher);
        viewHolder.name.setText(friendsArrayList.get(position).getName());
        viewHolder.status.setText(friendsArrayList.get(position).getMood());
        viewHolder.mood.setText(friendsArrayList.get(position).getStatus());
        return convertView;
    }

    class ViewHolder{
        ImageView friendImage;
        TextView name;
        TextView status;
        TextView mood;
    }
}
