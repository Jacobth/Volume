package com.chalmergps.jacobth.gpsvolumecalc;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * Created by jacobth on 2016-05-08.
 */
public class TabGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private TabHost tabHost;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public TabGestureDetector(TabHost tabHost) {
        this.tabHost = tabHost;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;

            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
               if(tabHost.getCurrentTab() < tabHost.getTabWidget().getTabCount()) {
                   tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
                }
            }
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(tabHost.getCurrentTab() > 0) {
                    tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
                }
            }
        } catch (Exception e) {
            // empty
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
}
