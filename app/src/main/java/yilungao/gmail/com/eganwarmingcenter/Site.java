package yilungao.gmail.com.eganwarmingcenter;

import android.os.Parcel;
import android.os.Parcelable;

public class Site implements Parcelable{

    public String siteName;
    public int numPeople;
    public int capacity = 0;
    public boolean activated;
    public boolean children = false;
    public boolean adult = true;
    public boolean disability = false;
    public boolean pets = false;
    public int isEnabled;

    public int expectedWalkin;
    public String siteID;

    public Site(String siteID){
        this.siteID = siteID;
    }

    public Site(Parcel in){
        siteName = in.readString();
        numPeople = in.readInt();
        capacity = in.readInt();
        isEnabled = in.readInt();
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
        parcel.writeInt(isEnabled);
    }
}
