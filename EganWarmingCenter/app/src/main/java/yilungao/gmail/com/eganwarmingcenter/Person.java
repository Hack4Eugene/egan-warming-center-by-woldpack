package yilungao.gmail.com.eganwarmingcenter;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    public String email, name;


    public Person(String e, String n) {
        email = e;
        name = n;
    }


    public Person(Parcel in) {
        email = in.readString();
        name = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
    }
}