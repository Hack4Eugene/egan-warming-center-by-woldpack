package yilungao.gmail.com.eganwarmingcenter;

import android.graphics.Color;

import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.DataPoint;

import static java.lang.Math.abs;

public class mValueDependentColor implements ValueDependentColor<DataPoint> {
    private int max;

    public mValueDependentColor(int max) {
        this.max = max;
    }

    public int get(DataPoint data) {
        if (data.getY()/max <= 0.333333333333) {
            return Color.rgb((int) (255 * 3 * data.getY() / max), 255, 0);
        } else {
            return Color.rgb(255, (int) ((255 * 3 * ((((max - data.getY()) / max)))) / 2), 0);
        }
    }
}
