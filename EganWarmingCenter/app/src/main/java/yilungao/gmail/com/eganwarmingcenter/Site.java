package yilungao.gmail.com.eganwarmingcenter;

import android.os.Parcel;
import android.os.Parcelable;

public class Site implements Parcelable{

    public String siteName;
    public int numPeople;
    public int capacity;
    public boolean activated;
    public boolean children;
    public boolean adult;
    public boolean disability;
    public boolean pets;
    public int expectedWalkin;
    public String siteID;

    public Site(String siteID){
        this.siteID = siteID;
    }

    public Site(Parcel in){
        siteName = in.readString();
        numPeople = in.readInt();
        capacity = in.readInt();
    }
    public static final Creator<Site> CREATOR = new Creator<Site>() {
        @Override
        public Site createFromParcel(Parcel parcel) {
            return new Site(parcel);
        }

        @Override
        public Site[] newArray(int i) {
            return new Site[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(siteName);
        parcel.writeInt(numPeople);
        parcel.writeInt(capacity);
    }
}
