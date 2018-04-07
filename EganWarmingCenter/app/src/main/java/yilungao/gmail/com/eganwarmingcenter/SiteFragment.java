package yilungao.gmail.com.eganwarmingcenter;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class SiteFragment extends Fragment {

    int position;
    private Runnable mTimer;
    private BarGraphSeries<DataPoint> series;
    private final Handler mHandler = new Handler();

    public String sitename;
    public int num_people;
    public int capacity = 10;
    public boolean activated;
    public boolean children;
    public boolean adult;
    public boolean disability;
    public boolean pets;

    public static SiteFragment newInstance() {
        SiteFragment tabFrag = new SiteFragment();
        Bundle args = new Bundle();
        tabFrag.setArguments(args);
        return tabFrag;
    }

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        SiteFragment siteFragment = new SiteFragment();
        siteFragment.setArguments(bundle);
        return siteFragment;
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

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(.5, 1)
        });
        series.setValueDependentColor(new mValueDependentColor(capacity));
        graph.addSeries(series);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(1.0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(10.0);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
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