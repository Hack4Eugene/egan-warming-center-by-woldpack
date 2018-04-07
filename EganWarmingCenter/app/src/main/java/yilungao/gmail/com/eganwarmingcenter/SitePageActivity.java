package yilungao.gmail.com.eganwarmingcenter;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class SitePageActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_page);

        sitename = getIntent().getStringExtra("SITENAME");

        GraphView graph = (GraphView) findViewById(R.id.graph);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {
                series.resetData(fetchNumPeople());
                mHandler.postDelayed(this, 300);
            }
        };
        mHandler.postDelayed(mTimer, 300);

    }

    private DataPoint[] fetchNumPeople() {
        DataPoint[] values = new DataPoint[1];
        DataPoint v = new DataPoint(.5, 9);
        values[0] = v;

        return values;
    }

}
