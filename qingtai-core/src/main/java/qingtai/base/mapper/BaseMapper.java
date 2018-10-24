package qingtai.base.mapper;

import org.springframework.stereotype.Repository;
import qingtai.annotation.db.DataSource;
import qingtai.config.db.DynamicDataSourceGlobal;

/**
 * qingtai.base.mapper
 * Created on 2017/10/19
 *
 * @author Lichaojie
 */
@Repository
public interface BaseMapper<T> {

    /**
     * 删除数据（物理删除，从数据库删除数据）
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     * @param record
     * @return
     */
    @DataSource(value = DynamicDataSourceGlobal.WRITE)
    int insert(T record);

    /**
     * 选择性插入（record中值为NULL的属性值不插入）
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 获取数据
     * @param id
     * @return
     */
    @DataSource(value = DynamicDataSourceGlobal.READ)
    T selectByPrimaryKey(Long id);

    /**
     * 选择性更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 逻辑删除，一般为更改此数据的标志位
     * @param id
     * @return
     */
    int deleteWithLogic(Long id);
}
