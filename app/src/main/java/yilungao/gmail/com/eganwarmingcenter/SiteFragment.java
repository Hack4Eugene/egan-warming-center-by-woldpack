package yilungao.gmail.com.eganwarmingcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class SiteFragment extends Fragment {

    int position;
    private Runnable mTimer;
    private BarGraphSeries<DataPoint> series;
    private final Handler mHandler = new Handler();
    private DatabaseReference mDatabase;
    private ArrayList<Site> siteArray = new ArrayList<Site>();

    public String sitename;
    public int num_people;
    public int capacity;
    public boolean activated;
    public boolean children;
    public boolean adult;
    public boolean disability;
    public boolean pets;
    public ListView siteListView;
    private ImageButton addSiteBtn;

    public static SiteFragment newInstance() {
        SiteFragment tabFrag = new SiteFragment();
        Bundle args = new Bundle();
        tabFrag.setArguments(args);
        return tabFrag;
    }

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        SiteFragment site = new SiteFragment();
        site.setArguments(bundle);
        return site;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.site_layout, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        siteListView = view.findViewById(R.id.siteListView);
        final SiteAdapter mAdapter = new SiteAdapter(getActivity(), siteArray);
        siteListView.setAdapter(mAdapter);

        siteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String siteID = mAdapter.getItem(i).siteID;

                Intent intent = new Intent(getActivity(), SitePageActivity.class);
                intent.putExtra("SITEID", siteID);
                startActivity(intent);
            }
        });

        addSiteBtn = view.findViewById(R.id.addSiteBtn);

        addSiteBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSiteActivity.class);
                startActivity(intent);
            }
        });


        mDatabase.child("sites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                siteArray = new ArrayList<Site>();
                for (DataSnapshot siteDataSnapshot: dataSnapshot.getChildren()) {
                    Site updatedSite = new Site((String) siteDataSnapshot.getKey());
                    updatedSite.siteName = (String) siteDataSnapshot.child("name").getValue();
                    updatedSite.capacity = siteDataSnapshot.child("capacity").getValue(Integer.class);
                    updatedSite.numPeople = siteDataSnapshot.child("num_people").getValue(Integer.class);
                    updatedSite.activated = (boolean) siteDataSnapshot.child("active").getValue();
                    siteArray.add(updatedSite);
                }
                mAdapter.updateData(siteArray);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}