package yilungao.gmail.com.eganwarmingcenter;

import android.graphics.Color;

import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.DataPoint;

public class mValueDependentColor implements ValueDependentColor<DataPoint> {
    private int max;

    public mValueDependentColor(int max) {
        this.max = max;
    }

    public int get(DataPoint data) {
        return Color.rgb((int) (255 * data.getY() / max), (int) ((255 * (max - data.getY())) / max), 0);
    }
}
