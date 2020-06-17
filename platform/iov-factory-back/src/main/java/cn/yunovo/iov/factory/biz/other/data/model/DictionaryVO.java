package cn.yunovo.iov.factory.biz.other.data.model;


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
@ApiModel(value="", description="查询对象")
public class DictionaryVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 词KEY
	 */
	@ApiModelProperty(value = "词KEY", name="wordKey")
	@NotBlank(message="[wordKey]为必填项")
	@Size(min = 0, max = 64, message = "[wordKey]长度在[0,64]之间")
	private String wordKey;
	
	/**
	 * 词值
	 */
	@ApiModelProperty(value = "词值", name="wordValue")
	@NotBlank(message="[wordValue]为必填项")
	@Size(min = 0, max = 128, message = "[wordValue]长度在[0,128]之间")
	private String wordValue;
	
	/**
	 * 词类型
	 */
	@ApiModelProperty(value = "词类型", name="wordType")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer wordType;
	
}
