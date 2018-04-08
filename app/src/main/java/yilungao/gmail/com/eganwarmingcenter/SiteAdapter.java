package yilungao.gmail.com.eganwarmingcenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SiteAdapter extends BaseAdapter {
    private ArrayList<Site> siteArray;
    private Context ctx;

    public SiteAdapter(Context ctx, ArrayList<Site> siteArray) {
        this.ctx = ctx;
        this.siteArray = siteArray;
    }

    @Override
    public int getCount() {
        return this.siteArray.size();
    }

    @Override
    public Site getItem(int position) {
        return this.siteArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<Site> newSiteArray){
        siteArray = newSiteArray;
    }

    /**
     * add a String Item in a List
     *
     * @param site
     */
    public void addItem(Site site) {
        this.siteArray.add(site);
        this.notifyDataSetChanged();
    }

    /**
     * Delete an Item from a List
     *
     * @param position
     */
    public void deleteItem(int position) {
        this.siteArray.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView;

        if (convertView == null) {
            newView = View.inflate(ctx, R.layout.list_item_site, null);
        } else {
            newView = convertView;
        }

//   Here is what you're looking for:
        ((TextView) newView.findViewById(R.id.fraction)).setText(getItem(position).numPeople + "/" + getItem(position).capacity);
//   End;
        ((TextView) newView.findViewById(R.id.siteName)).setText(getItem(position).siteName);



        if(getItem(position).activated){
            ((ImageView) newView.findViewById(R.id.redSquare)).setVisibility(View.INVISIBLE);
            ((ImageView) newView.findViewById(R.id.greenSquare)).setVisibility(View.VISIBLE);
        }
        else{
            ((ImageView) newView.findViewById(R.id.redSquare)).setVisibility(View.VISIBLE);
            ((ImageView) newView.findViewById(R.id.greenSquare)).setVisibility(View.INVISIBLE);
        }

        return newView;
    }

}
