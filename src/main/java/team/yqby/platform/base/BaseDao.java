package team.yqby.platform.base;

import java.util.List;
import java.util.Map;

/***
 * 进行相应的数据库操作相关类
 * @author tony
 *
 * @param <T> 相应的实体操作
 */
public interface BaseDao<T>{
	
	/***
	 * 添加数据
	 * @param t 数据对象 泛型
	 * @return 返回添加的数据
	 */
	public T add(T t);
	
	/***
	 * 修改
	 * @param t 修改的数据
	 * @return 返回修改的数据
	 */
	public T update(T t);
	
	/***
	 * 根据编号进行删除
	 * @param id 编号
	 */
	public void deleteById(Long id);
	
	/****
	 * 根据编号得到相应的数据
	 * @param id  编号
	 * @return 返回查询的结果
	 */
	public T getById(Long id);
	
	/***
	 * 进行相应的查询
	 * @param map 查询的条件封装为一个map
	 * @return 返回查询的结果
	 */
	public List<T> findAll(Map<Object, Object> map);
	
}
