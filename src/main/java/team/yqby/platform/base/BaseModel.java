package team.yqby.platform.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/***
 * 相应的实体的公共字段
 * @author tony
 *
 */
@Data
public class BaseModel implements Serializable{
	
	private static final long serialVersionUID = 3575279395428621287L;

	private Long id;  //相应的编号
	
	private Long createBy;
	
	private String createByName;
	
	private Date createTime;
	
	
	private Long updateBy;
	
	private String updateByName;
	
	private Date updateTime;
	
	
}
