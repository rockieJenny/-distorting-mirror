package com.inmobi.commons.db;

public class ColumnData {
    private boolean a = false;
    private boolean b = false;
    private boolean c = false;
    private ColumnType d;

    public enum ColumnType {
        INTEGER,
        VARCHAR,
        REAL,
        TEXT
    }

    public ColumnType getDataType() {
        return this.d;
    }

    public void setDataType(ColumnType columnType) {
        this.d = columnType;
    }

    public boolean isPrimaryKey() {
        return this.a;
    }

    public void setPrimaryKey(boolean z) {
        this.a = z;
    }

    public boolean isAutoIncrement() {
        return this.b;
    }

    public void setAutoIncrement(boolean z) {
        this.b = z;
    }

    public boolean isMandatory() {
        return this.c;
    }

    public void setMandatory(boolean z) {
        this.c = z;
    }
}
