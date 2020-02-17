package com.example.calculatornew;

import org.litepal.crud.DataSupport;

public class ThemeTag extends DataSupport {
    public int id;
    public int moudleTag;
    public int selectTag;

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

    public int getSelectTag() {
        return selectTag;
    }

    public void setSelectTag(int selectTag) {
        this.selectTag=selectTag;
    }
}
