package com.kmetabus.forwarder.vo;
import android.location.Location;

import java.util.Date;

public class ListItem {
    private String seq;
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String col5;
    private int imageResourceId;
    private Location loc;
    private boolean isNew;
    private Date today;
    public ListItem(String seq, String col1, String col2, String col3, String col4, String col5,   Location loc , boolean isnew, Date today ) {
        this.seq = seq;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
        this.loc = loc;
        this.isNew = isnew;
        this.today = today;
//System.out.println("dist ==> "+col4);

    }

    public String getSeq() {
        return seq;
    }
    public String getCol1() {
        return col1;
    }
    public String getCol2() {
        return col2;
    }
    public String getCol3() {
        return col3;
    }
    public String getCol4() {
        return col4;
    }
    public String getCol5() {
        return col5;
    }

    public Location getLoc() {
        return loc;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
    public boolean getIsNew() {
        return isNew;
    }
    public Date getToday() {
        return today;
    }
}
