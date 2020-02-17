package com.example.calculatornew;

import org.litepal.crud.DataSupport;

public class ThemeTag extends DataSupport {
    public int id;
    public int moudleTag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoudleTag() {
        return moudleTag;
    }

    public void setMoudleTag(int moudleTag) {
        this.moudleTag = moudleTag;
    }
}
