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
