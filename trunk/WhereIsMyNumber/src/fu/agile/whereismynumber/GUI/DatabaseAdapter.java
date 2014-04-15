package fu.agile.whereismynumber.GUI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// 
public class DatabaseAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_HIGH_SCORE = "high_score";
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "highscoredata";
	// Bang luu tru diem high score
	private static final String HIGHSCORE_TABLE = "highscore";
	// Bang luu tru cai dat
	private static final String SETTING_TABLE = "SETTING_TABLE";

	private final Context context;
	private DatabaseHelper databaseHelper;

	private SQLiteDatabase db;

	public DatabaseAdapter(Context context) {
		this.context = context;

	}

	// Define DatabaseHelper to store Data
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final int DATABASE_VERSION = 1;

		// Dinh nghia 1 chuoi khi tao bang chua du lieu
		private static final String CREATE_HIGHSCORE_TABLE = "create table "
				+ HIGHSCORE_TABLE + "_id integer primary key autoincrement, "
				+ "highscore text not null);";

		// Xac dinh bang , ten bang, va phien ban luu tru du lieu
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		// Khoi tao mot bang chua dua lieu
		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(CREATE_HIGHSCORE_TABLE);
		}

		// Update mot bang chua du lieu , du lieu moi se duoc ghi de len du lieu
		// cu
		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion,
				int newVersion) {
			Log.w(TAG, "Upgrading database from version" + oldVersion + "to "
					+ newVersion + ",which will destroy all old data");
			database.execSQL("DROP TABLE IF EXITS todo");
			onCreate(database);
		}
	}

	// Mo mot bang du lieu de cho phep doc va ghi du lieu moi vao
	public DatabaseAdapter open() throws SQLException {
		db = databaseHelper.getWritableDatabase();
		return this;
	}

	// Dong bang chua du lieu
	public void close() {
		databaseHelper.close();
	}

	// Chen vao them 1 record vao trong bang
	public long insertRecord(long newHighScore) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_HIGH_SCORE, newHighScore);
		return db.insert(HIGHSCORE_TABLE, null, initialValues);
	}

	// Update 1 record vao trong bang
	public boolean updateRecord(long rowId, String newHighScore) {
		ContentValues args = new ContentValues();
		args.put(KEY_HIGH_SCORE, newHighScore);
		return db.update(HIGHSCORE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// Xac dinh xem 1 record da ton tai chua de update hoac insert
	public void insertOrUpdateRecord(String newHighScore) {
		String INSERT_OR_UPDATE_RECORD = "INSERT OR REPLACE INTO "
				+ HIGHSCORE_TABLE + "(" + KEY_ROWID + "," + KEY_HIGH_SCORE
				+ ")" + "VALUES(1," + "'" + newHighScore + "');";
		db.execSQL(INSERT_OR_UPDATE_RECORD);
	}

	// Xoa 1 record chua trong bang
	public boolean deleteRecord(long rowId) {
		return db.delete(HIGHSCORE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// Lay du lieu cua 1 record co trong bang
	public Cursor getAllRecords() {
		return db.query(HIGHSCORE_TABLE, new String[] { KEY_ROWID,
				KEY_HIGH_SCORE }, null, null, null, null, null);
	}

	public Cursor getRecord(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, HIGHSCORE_TABLE, new String[] {
				KEY_ROWID, KEY_HIGH_SCORE }, KEY_ROWID + "=" + rowId, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

}
