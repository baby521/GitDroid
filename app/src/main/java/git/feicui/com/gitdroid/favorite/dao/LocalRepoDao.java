package git.feicui.com.gitdroid.favorite.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import git.feicui.com.gitdroid.favorite.model.LocalRepo;

/**
 * Created by Administrator on 16-9-1.
 */

//本地仓库Dao文件
public class LocalRepoDao {

    private Dao<LocalRepo,Long> dao;

    public LocalRepoDao(DBHelp dbHelp){
        try {
            dao = dbHelp.getDao(LocalRepo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加和更新数据
     */

    public void createOrUpdate(LocalRepo localRepo){
        try {
            dao.createOrUpdate(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 添加和更新数据
     */
    public void createOrUpdate(List<LocalRepo> list){
        for (LocalRepo localRepo:list){
            createOrUpdate(localRepo);
        }
    }

    /**
     * 删除
     */
    public void delete(LocalRepo localRepo){
        try {
            dao.delete(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 查询--根据本地仓库的外键（仓库类别表）的id，相应就查到不同类别的仓库信息
     */
    public List<LocalRepo> queryForGroupId(int groupId){
        try {
            return dao.queryForEq(LocalRepo.COLUMN_GROUP_ID,groupId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询未分类的仓库数据
     * @return
     */
    public List<LocalRepo> queryNoGroup(){
        try {
            return dao.queryBuilder().where().isNull(LocalRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有
     * @return
     */
    public List<LocalRepo> queryAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
