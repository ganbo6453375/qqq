package com.lkw.myapplication.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by aaa on 15-5-5.
 */
@Table(name="SearchSave")
public class SearchSave {
    @Column(column = "id")
    private int id;
    @Column(column = "text")

    private String text;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
