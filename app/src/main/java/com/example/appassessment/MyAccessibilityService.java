package com.example.appassessment;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "AccessibilityService";
    int depth = 0;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo nodeInfo = event.getSource();

        PackageManager packageManager = this.getPackageManager();
        String packageName = event.getPackageName().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        String currentTime = sdf.format(System.currentTimeMillis());

        int eventType = event.getEventType();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);

            if (!(applicationLabel.equals("TouchWiz home") ||
                    applicationLabel.equals("System UI") || applicationLabel.equals("AssessmentApp"))) {
                if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                    saveTextValue(nodeInfo, applicationLabel, packageName, currentTime);
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "onAccessibilityEvent: Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt: Something went wrong");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);

    }

    private void saveTextValue(AccessibilityNodeInfo nodeInfo, CharSequence applicationLabel,
                               String packageName, String currentTime) {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        if (nodeInfo == null) return;

        String textValue = "";

        if (nodeInfo.getClassName().toString().contentEquals("android.widget.TextView"))
            textValue += nodeInfo.getText();

        if (!(textValue.equals("") || textValue.equals("null"))) {
            // Save value in SQLite DB
            db.addTextContent(new Model(applicationLabel.toString(), packageName, textValue, currentTime));
        }

        if (nodeInfo.getChildCount() < 1) return;
        depth++;

        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            saveTextValue(nodeInfo.getChild(i), applicationLabel, packageName, currentTime);
        }
        depth--;
    }
}
