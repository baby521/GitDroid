package git.feicui.com.gitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import git.feicui.com.gitdroid.favorite.model.LocalRepo;
import git.feicui.com.gitdroid.favorite.model.RepoGroup;

/**
 * Created by Administrator on 16-9-1.
 */
public class DBHelp extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "repo_favorite.db";
    private static final int VERSION = 2;

    private static DBHelp dbHelp;
    private Context context;

    public static synchronized DBHelp getInstance(Context context){
        if(dbHelp == null){
            dbHelp = new DBHelp(context.getApplicationContext());
        }
        return dbHelp;
    }

    private DBHelp(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //数据库表的创建
        try {
            //创建类别表（单纯的创建出来，里面是空的，没有数据）
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);
            TableUtils.createTableIfNotExists(connectionSource, LocalRepo.class);
            //将本地的数据填充到数据库表中
            new RepoGroupDao(this).createOrUpdate(RepoGroup.getDefaultGroup(context));
            new LocalRepoDao(this).createOrUpdate(LocalRepo.getDefaultLocalRepo(context));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //数据库进行更新,先删除表，再重新创建
        try {
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            TableUtils.dropTable(connectionSource,LocalRepo.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
