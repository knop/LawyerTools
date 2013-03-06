package com.team4.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.team4.utils.database.T4DBHelper;

public class CacheProvider extends ContentProvider{
	
	private static final String DATABASE_NAME = "cache.db";
	private final static int DB_VERSION = 1;

	private T4DBHelper mDBHelper;
	
	private static final int URI_ARCSYNC_CACHES = 0;
	private static final int URI_ARCSYNC_CACHES_ID = 1;
	private static final int URI_ARCSYNC_ON_SYNC = 2;
	private static final int URI_ARCSYNC_ON_SYNC_ID = 3;	
	
    public static final Uri CONTENT_URI = Uri.parse("content://powermobia.arcsync");
    public static final Uri CONTENT_URI_CACHE = Uri.parse("content://powermobia.arcsync/cache");
    public static final Uri CONTENT_URI_ON_SYNC = Uri.parse("content://powermobia.arcsync/onsync");  
    
    private static final UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);  
    static {  
//        sMatcher.addURI("powermobia.arcsync", CACHE_TABLE, URI_ARCSYNC_CACHES);  
//        sMatcher.addURI("powermobia.arcsync", CACHE_TABLE + "/#", URI_ARCSYNC_CACHES_ID);
//        sMatcher.addURI("powermobia.arcsync", ON_SYNC_TABLE, URI_ARCSYNC_ON_SYNC);  
//        sMatcher.addURI("powermobia.arcsync", ON_SYNC_TABLE + "/#", URI_ARCSYNC_ON_SYNC_ID);          
    }  
	
	@Override
	public boolean onCreate() {
		mDBHelper = new T4DBHelper(getContext(), DATABASE_NAME, DB_VERSION, new T4DBHelper.TableEntity());
		return true;
	}

	@Override
	public String getType(Uri uri) {
        int match = sMatcher.match(uri);
        switch (match) {
            case URI_ARCSYNC_CACHES:
            case URI_ARCSYNC_ON_SYNC:
                return "powermobia.arcsync.dir/cache";
            case URI_ARCSYNC_CACHES_ID:
            case URI_ARCSYNC_ON_SYNC_ID:
                return "powermobia.arcsync.item/cache";
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
//		int code = sMatcher.match(uri);
//		String table = null;
//		switch(code){
//		case URI_ARCSYNC_CACHES:
//			table = CACHE_TABLE;
//			break;
//		case URI_ARCSYNC_ON_SYNC:
//			table = ON_SYNC_TABLE;
//			break;			
//		default:
//			T4Log.v("Cannot insert into URL: " + uri);
//			return null;
//		}
//		
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
//        long rowId = db.insert(table, null, values);
//        if (rowId < 0) {
//        	T4Log.v("Failed to insert row into " + uri);
//        }
//        Uri newUri = ContentUris.withAppendedId(Uri.withAppendedPath(CONTENT_URI,File.separator+table), rowId);
//        //getContext().getContentResolver().notifyChange(newUri, null);
//		return newUri;
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//		String orderBy = null;
//		int code = sMatcher.match(uri);
//		switch(code){
//		case URI_ARCSYNC_CACHES:
//			qb.setTables(CACHE_TABLE);
//			orderBy = "is_dir desc, file_name";
//			break;
//		case URI_ARCSYNC_CACHES_ID:
//			qb.setTables(CACHE_TABLE);
//            qb.appendWhere("_id=");
//            qb.appendWhere(uri.getPathSegments().get(1));
//            orderBy = "is_dir desc, file_name";
//			break;
//		case URI_ARCSYNC_ON_SYNC:
//			qb.setTables(ON_SYNC_TABLE);
//			orderBy = "state desc, file_name";
//			break;
//		case URI_ARCSYNC_ON_SYNC_ID:
//			qb.setTables(ON_SYNC_TABLE);
//            qb.appendWhere("_id=");
//            qb.appendWhere(uri.getPathSegments().get(1));	
//            orderBy = "state desc, file_name";
//			break;			
//		default:
//			T4Log.v("Unknown URL " + uri);
//			return null;
//		}
//		SQLiteDatabase db = mDBHelper.getReadableDatabase();		
//		if(sortOrder != null && sortOrder.length()>0)
//			orderBy += ","+sortOrder;
//		return qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
//        int count;
//        long rowId = 0;
//        int match = sMatcher.match(uri);
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
//        switch (match) {
//        	case URI_ARCSYNC_CACHES:
//        		count = db.update(CACHE_TABLE, values, selection, selectionArgs);
//        		break;
//            case URI_ARCSYNC_CACHES_ID: {
//                String segment = uri.getPathSegments().get(1);
//                rowId = Long.parseLong(segment);
//                count = db.update(CACHE_TABLE, values, "_id=" + rowId, null);
//                break;
//            }
//            case URI_ARCSYNC_ON_SYNC:
//            	count = db.update(ON_SYNC_TABLE, values, selection, selectionArgs);
//            	break;
//            case URI_ARCSYNC_ON_SYNC_ID: {
//                String segment = uri.getPathSegments().get(1);
//                rowId = Long.parseLong(segment);
//                count = db.update(ON_SYNC_TABLE, values, "_id=" + rowId, null);
//            	break;
//            }
//            default: {
//            	T4Log.v("Cannot update URL: " + uri);
//            	return 0;
//            }
//        }
//        //getContext().getContentResolver().notifyChange(uri, null);
//        return count;
		return 0;
	}	
	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
//		SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count = 0;
//        long rowId = 0;
//        switch (sMatcher.match(uri)) {
//            case URI_ARCSYNC_CACHES:
//                count = db.delete(CACHE_TABLE, where, whereArgs);
//                break;
//            case URI_ARCSYNC_CACHES_ID: {
//                String segment = uri.getPathSegments().get(1);
//                rowId = Long.parseLong(segment);
//                if (TextUtils.isEmpty(where)) {
//                    where = "_id=" + rowId;
//                } else {
//                    where = "_id=" + rowId + " AND (" + where + ")";
//                }
//                count = db.delete(CACHE_TABLE, where, whereArgs);
//                break;
//            }
//            case URI_ARCSYNC_ON_SYNC:
//            	count = db.delete(ON_SYNC_TABLE, where, whereArgs);
//            	break;
//            case URI_ARCSYNC_ON_SYNC_ID: {
//                String segment = uri.getPathSegments().get(1);
//                rowId = Long.parseLong(segment);
//                if (TextUtils.isEmpty(where)) {
//                    where = "_id=" + rowId;
//                } else {
//                    where = "_id=" + rowId + " AND (" + where + ")";
//                }
//                count = db.delete(ON_SYNC_TABLE, where, whereArgs);
//            	break;                
//            }
//            default:
//            	T4Log.v("Cannot delete from URL: " + uri);
//                return 0;
//        }

        //getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}
}
