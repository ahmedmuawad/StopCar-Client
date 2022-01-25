package com.stopgroup.stopcar.client.activity.chat_libs;
/**
 * Created by malavan on 05/08/17.
 */
public class Message {
    public String id = "";
    public String kind;
    public String content;
    public String time;
    public String seen;
    public String who;
    public Message() {
    }


    public boolean isCall() {
        return kind != null && (kind.equals("video_call") || kind.equals("voice_call"));
    }
}
