package com.hand;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/8/31.
 */
@Entity
@Table(name = "store", schema = "", catalog = "sakila")
public class StoreEntity {
    private byte storeId;
    private Timestamp lastUpdate;

    @Id
    @Column(name = "store_id", nullable = false, insertable = true, updatable = true)
    public byte getStoreId() {
        return storeId;
    }

    public void setStoreId(byte storeId) {
        this.storeId = storeId;
    }

    @Basic
    @Column(name = "last_update", nullable = false, insertable = true, updatable = true)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreEntity that = (StoreEntity) o;

        if (storeId != that.storeId) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) storeId;
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }
}
