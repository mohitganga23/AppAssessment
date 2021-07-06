package com.example.appassessment;

public class Model {

    int id;
    String appName, packageName, text, timeStamp;

    public Model() {}

    public Model(String appName, String packageName, String text, String timeStamp) {
        this.appName = appName;
        this.packageName = packageName;
        this.text = text;
        this.timeStamp = timeStamp;
    }

    public Model(int id, String appName, String packageName, String text, String timeStamp) {
        this.id = id;
        this.appName = appName;
        this.packageName = packageName;
        this.text = text;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
