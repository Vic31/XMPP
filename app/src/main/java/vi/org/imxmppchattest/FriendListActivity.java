package vi.org.imxmppchattest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vi.org.imxmppchattest.adapter.FriendListAdapter;
import vi.org.imxmppchattest.model.Friends;
import vi.org.imxmppchattest.model.FriendsGroup;

/**
 * Created by Vicky on 2015/12/2.
 * IMXmppChatTest
 * contact way: 317461087@qq.com
 */
public class FriendListActivity extends Activity{

    private ListView contacts;
    private FriendListAdapter adapter;
    private ArrayList<FriendsGroup> groupArrayList;
    private ArrayList<Friends> friendsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list_activity);

        contacts = (ListView)findViewById(R.id.contacts_lv);
        getFriends();
    }
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
                //互相为好友？
                if(entry.getType().name().equals("both"))
                {
                    Log.i("userId",entry.getUser());//guest@192.168.10.111
                    Presence presence = roster.getPresence(entry.getUser());
                    Friends friends = new Friends();
                    friends.setName(entry.getUser().split("@")[0]);
//                    entry.getStatus().toString(); 这不是状态
                    /*
                    chat,
                    available,
                    away,
                    xa,
                    dnd;
                     */
                    if(presence.getMode() != null)
                    {
                        Log.i("mode",presence.getMode().toString());
                        friends.setStatus(presence.getMode().toString());
                    }
                    else {
                        friends.setStatus("none");
                    }
                    //这里面其实有个entry.getName()
                    friendsArrayList.add(friends);
                }
            }
            friendsGroup.setFriends(friendsArrayList);
            groupArrayList.add(friendsGroup);
        }
        adapter = new FriendListAdapter(this,friendsArrayList);
        contacts.setAdapter(adapter);
    }
}
