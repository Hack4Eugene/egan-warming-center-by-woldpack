package yilungao.gmail.com.eganwarmingcenter;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Message extends AppCompatActivity{



//    public static Messaging newInstance() {
//        Messaging messagingFrag = new Messaging();
//        Bundle args = new Bundle();
//        messagingFrag.setArguments(args);
//        return messagingFrag;
//    }
//
//    public static Fragment getInstance(int position) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("pos", position);
//        Messaging messagingFragment = new Messaging();
//        messagingFragment.setArguments(bundle);
//        return messagingFragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        messagingView = findViewById(R.id.messagingMain);
        setContentView(R.layout.message);
    }


}