package vi.org.imxmppchattest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.Collection;

import vi.org.imxmppchattest.ConnectServer;
import vi.org.imxmppchattest.R;
import vi.org.imxmppchattest.adapter.FriendListAdapter;
import vi.org.imxmppchattest.model.Friends;
import vi.org.imxmppchattest.model.FriendsGroup;
import vi.org.imxmppchattest.util.Constant;

/**
 * Created by Vicky on 2015/12/2.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */
public class FriendListActivity extends Activity implements AdapterView.OnItemClickListener{

    private ListView contacts;
    private FriendListAdapter adapter;
    private ArrayList<FriendsGroup> groupArrayList;
    private ArrayList<Friends> friendsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        contacts = (ListView)findViewById(R.id.contacts_lv);
        getFriends();
        adapter = new FriendListAdapter(this,friendsArrayList);
        contacts.setAdapter(adapter);
        contacts.setOnItemClickListener(this);

    }

    /**
     * 拿到好友列表
     */
    private void getFriends()
    {
        Roster roster = ConnectServer.xmppConnection.getRoster();
        Collection<RosterGroup> groups = roster.getGroups();
        groupArrayList = new ArrayList<FriendsGroup>();
        for(RosterGroup group:groups)
        {
            //外层group
            FriendsGroup friendsGroup = new FriendsGroup();
            friendsGroup.setGroupName(group.getName());

            Collection<RosterEntry> entries = group.getEntries();
            friendsArrayList = new ArrayList<Friends>();
            for(RosterEntry entry:entries)
            {
                //The user and the contact have subscriptions to each other's presence (also called a "mutual subscription").
                if(entry.getType().name().equals("both"))
                {
                    Log.i("userId",entry.getUser());//guest@192.168.10.111
                    Presence presence = roster.getPresence(entry.getUser());
                    Friends friends = new Friends();
                    friends.setName(entry.getUser().split("@")[0]);
                    //getStatus 是心情，不知道怎么回事一直是null,presence.setStatus("")再试试
                    /*if(!TextUtils.isEmpty(presence.getStatus()))
                    {
                        Log.i("mode",presence.getStatus());
                        friends.setStatus(presence.getStatus());
                    }
                    else {
                        friends.setStatus("none");
                    }*/
                    //获取在线模式
                    /*
                    chat,
                    available,
                    away,
                    xa,
                    dnd;
                     */
                    //不直到在何种情况下是null，会崩溃
                    if(presence.getMode() != null)
                    {
                        Log.i("mode",presence.getMode().toString());
                        friends.setStatus(presence.getMode().toString());
                    }
                    else {
                        friends.setStatus("none");
                    }
                    //获取是否在线
                    if(presence.isAvailable())
                    {
                        friends.setMood("online");
                    }
                    else {
                        friends.setMood("offline");
                    }
                    //这里面其实有个entry.getName()
                    friendsArrayList.add(friends);
                }
            }
            friendsGroup.setFriends(friendsArrayList);
            groupArrayList.add(friendsGroup);
        }
    }

    /**
     * 启动聊天模式
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(FriendListActivity.this,"id: "+friendsArrayList.get(position).getName(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(FriendListActivity.this,ChatActivity.class);
        intent.putExtra(Constant.fromId,friendsArrayList.get(position).getName());
        startActivity(intent);
    }
}
