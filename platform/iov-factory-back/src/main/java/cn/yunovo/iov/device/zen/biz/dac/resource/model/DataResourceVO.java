package cn.yunovo.iov.device.zen.biz.dac.resource.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 展示对象：SampleVO，Sample一般为网页名称<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
@ApiModel(value="数据权限：资源，主体，规则关系表", description="数据权限：资源，主体，规则关系表查询对象")
public class DataResourceVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 数据表中的主键
	 */
	@ApiModelProperty(value = "数据表中的主键", name="dataId")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer dataId;
	
	/**
	 * 数据提供的表,值为主表
	 */
	@ApiModelProperty(value = "数据提供的表,值为主表", name="dataProvider")
	@NotBlank(message="[dataProvider]为必填项")
	@Size(min = 0, max = 128, message = "[dataProvider]长度在[0,128]之间")
	private String dataProvider;
	
	/**
	 * 数据创建者ID
	 */
	@ApiModelProperty(value = "数据创建者ID", name="creatorId")
	@NotBlank(message="[creatorId]为必填项")
	@Size(min = 0, max = 64, message = "[creatorId]长度在[0,64]之间")
	private String creatorId;
	
	/**
	 * 来源创建者ID
	 */
	@ApiModelProperty(value = "来源创建者ID", name="sourceCreatorId")
	@Size(min = 0, max = 64, message = "[sourceCreatorId]长度在[0,64]之间")
	private String sourceCreatorId;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateTime", hidden = true)
	private String updateTime;
	
}
