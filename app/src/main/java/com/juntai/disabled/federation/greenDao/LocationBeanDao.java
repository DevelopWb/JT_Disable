package com.juntai.disabled.federation.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.juntai.disabled.federation.bean.history_track.LocationBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCATION_BEAN".
*/
public class LocationBeanDao extends AbstractDao<LocationBean, Long> {

    public static final String TABLENAME = "LOCATION_BEAN";

    /**
     * Properties of entity LocationBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Address = new Property(1, String.class, "address", false, "ADDRESS");
        public final static Property PosType = new Property(2, String.class, "posType", false, "POS_TYPE");
        public final static Property Longitude = new Property(3, String.class, "longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(4, String.class, "latitude", false, "LATITUDE");
        public final static Property GmtCreate = new Property(5, String.class, "gmtCreate", false, "GMT_CREATE");
    }


    public LocationBeanDao(DaoConfig config) {
        super(config);
    }
    
    public LocationBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCATION_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ADDRESS\" TEXT," + // 1: address
                "\"POS_TYPE\" TEXT," + // 2: posType
                "\"LONGITUDE\" TEXT," + // 3: longitude
                "\"LATITUDE\" TEXT," + // 4: latitude
                "\"GMT_CREATE\" TEXT UNIQUE );"); // 5: gmtCreate
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCATION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocationBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(2, address);
        }
 
        String posType = entity.getPosType();
        if (posType != null) {
            stmt.bindString(3, posType);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(4, longitude);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(5, latitude);
        }
 
        String gmtCreate = entity.getGmtCreate();
        if (gmtCreate != null) {
            stmt.bindString(6, gmtCreate);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocationBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(2, address);
        }
 
        String posType = entity.getPosType();
        if (posType != null) {
            stmt.bindString(3, posType);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(4, longitude);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(5, latitude);
        }
 
        String gmtCreate = entity.getGmtCreate();
        if (gmtCreate != null) {
            stmt.bindString(6, gmtCreate);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocationBean readEntity(Cursor cursor, int offset) {
        LocationBean entity = new LocationBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // address
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // posType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // longitude
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // latitude
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // gmtCreate
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocationBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAddress(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPosType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLongitude(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLatitude(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGmtCreate(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocationBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocationBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocationBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
