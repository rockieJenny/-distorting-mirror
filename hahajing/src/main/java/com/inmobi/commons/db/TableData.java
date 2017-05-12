package com.inmobi.commons.db;

import java.util.LinkedHashMap;

public class TableData {
    private String a;
    private LinkedHashMap<String, ColumnData> b;

    public String getmTableName() {
        return this.a;
    }

    public void setmTableName(String str) {
        this.a = str;
    }

    public LinkedHashMap<String, ColumnData> getmColumns() {
        return this.b;
    }

    public void setmColumns(LinkedHashMap<String, ColumnData> linkedHashMap) {
        this.b = linkedHashMap;
    }
}
