package com.example.app_nhac.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BaiHatOffline implements Parcelable {

    private int idbaihat;
    private String tenbaihat;
    private String casi;
    private String linkbaihat;
    private String loibaihat;

    public BaiHatOffline(int idbaihat, String tenbaihat, String casi, String linkbaihat, String loibaihat) {
        this.idbaihat = idbaihat;
        this.tenbaihat = tenbaihat;
        this.casi = casi;
        this.linkbaihat = linkbaihat;
        this.loibaihat = loibaihat;
    }

    protected BaiHatOffline(Parcel in) {
        idbaihat = in.readInt();
        tenbaihat = in.readString();
        casi = in.readString();
        linkbaihat = in.readString();
        loibaihat = in.readString();
    }

    public static final Creator<BaiHatOffline> CREATOR = new Creator<BaiHatOffline>() {
        @Override
        public BaiHatOffline createFromParcel(Parcel in) {
            return new BaiHatOffline(in);
        }

        @Override
        public BaiHatOffline[] newArray(int size) {
            return new BaiHatOffline[size];
        }
    };

    public int getIdbaihat() {
        return idbaihat;
    }

    public void setIdbaihat(int idbaihat) {
        this.idbaihat = idbaihat;
    }

    public String getTenbaihat() {
        return tenbaihat;
    }

    public void setTenbaihat(String tenbaihat) {
        this.tenbaihat = tenbaihat;
    }

    public String getCasi() {
        return casi;
    }

    public void setCasi(String casi) {
        this.casi = casi;
    }

    public String getLinkbaihat() {
        return linkbaihat;
    }

    public void setLinkbaihat(String linkbaihat) {
        this.linkbaihat = linkbaihat;
    }

    public String getLoibaihat() {
        return loibaihat;
    }

    public void setLoibaihat(String loibaihat) {
        this.loibaihat = loibaihat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idbaihat);
        dest.writeString(tenbaihat);
        dest.writeString(casi);
        dest.writeString(linkbaihat);
        dest.writeString(loibaihat);
    }
}
