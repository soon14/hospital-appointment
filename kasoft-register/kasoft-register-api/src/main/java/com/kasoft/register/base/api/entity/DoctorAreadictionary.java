package com.kasoft.register.base.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 地区字典
 *
 * @author charlie
 * @date 2019-04-30 17:10:28
 */
@Data
@TableName("kasoft_doctor_areadictionary")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "地区")
public class DoctorAreadictionary extends Model<DoctorAreadictionary> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "area_id", type = IdType.INPUT)
	@ApiModelProperty(value = "主键")
	private String areaId;
	/**
	 * 省市区名称
	 */
	@ApiModelProperty(value = "名称")
	@NotBlank(message = "名称不能为空")
	private String name;
	/**
	 * 父级编号
	 */
	@ApiModelProperty(value = "父级编号")
	@NotBlank(message = "父级编号不能为空")
	private String parentAreaId;
	/**
	 * 类型 1：省 2：市 3：区
	 */
	@ApiModelProperty(value = "类型 1：省 2：市 3：区")
	@NotBlank(message = "类型不能为空")
	private String areaType;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 清理不使用的DTO参数
	 */
	public void clearNoUseDTO() {
		createTime = null;
		updateTime = null;
	}

}
