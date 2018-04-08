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
        if (data.getY()/max <= 0.5) {
            return Color.rgb((int) (50 + 205 * 2 * data.getY() / max), 255, 50);
        } else  if (data.getY() <= max){
            return Color.rgb(255, (int) (50 + 205 * 2 * ((((max - data.getY()) / max)))), 50);
        } else return Color.rgb(0, 0, 0);
    }
}
