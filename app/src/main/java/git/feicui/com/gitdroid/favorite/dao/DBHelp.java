package git.feicui.com.gitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import git.feicui.com.gitdroid.favorite.model.RepoGroup;

/**
 * Created by Administrator on 16-9-1.
 */
public class DBHelp extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "repo_favorite.db";
    private static final int VERSION = 1;
    public DBHelp(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //数据库表的创建
        try {
            //创建类别表（单纯的创建出来，里面是空的，没有数据）
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //数据库进行更新,先删除表，再重新创建
        try {
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
