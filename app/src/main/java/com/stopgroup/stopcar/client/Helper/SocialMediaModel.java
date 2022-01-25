package com.stopgroup.stopcar.client.Helper;

import android.os.Parcel;
import android.os.Parcelable;

public class SocialMediaModel implements Parcelable {

    private String privacy;
    private String help;
    private String address;
    private String facebook;
    private String twitter;
    private String phone;
    private String email;

    public SocialMediaModel() {

    }

    protected SocialMediaModel(Parcel in) {
        privacy = in.readString();
        help = in.readString();
        address = in.readString();
        facebook = in.readString();
        twitter = in.readString();
        phone = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(privacy);
        dest.writeString(help);
        dest.writeString(address);
        dest.writeString(facebook);
        dest.writeString(twitter);
        dest.writeString(phone);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SocialMediaModel> CREATOR = new Creator<SocialMediaModel>() {
        @Override
        public SocialMediaModel createFromParcel(Parcel in) {
            return new SocialMediaModel(in);
        }

        @Override
        public SocialMediaModel[] newArray(int size) {
            return new SocialMediaModel[size];
        }
    };

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
