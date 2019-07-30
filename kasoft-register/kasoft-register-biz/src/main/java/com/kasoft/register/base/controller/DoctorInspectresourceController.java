package com.kasoft.register.base.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kasoft.register.base.api.entity.DoctorInspectresource;
import com.kasoft.register.base.api.vo.InspSourcesVO;
import com.kasoft.register.base.service.DoctorInspectresourceService;
import com.kasoft.register.base.utils.KrbConstants;
import com.pig4cloud.pigx.common.core.constant.ReturnMsgConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 检查资源
 *
 * @author kylin
 * @date 2019-07-27 10:32:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/doctorinspectresource")
@Api(value = "doctorinspectresource", tags = "基础-检查资源")
public class DoctorInspectresourceController {

    private final DoctorInspectresourceService doctorInspectresourceService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param doctorInspectresource 检查资源
     * @return R
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getDoctorInspectresourcePage(Page page, DoctorInspectresource doctorInspectresource) {
        return R.ok(doctorInspectresourceService.page(page, new QueryWrapper<DoctorInspectresource>()
			.like(StrUtil.isNotBlank(doctorInspectresource.getInspItemName()), "insp_item_name", doctorInspectresource.getInspItemName())
			.ge(ObjectUtil.isNotNull(doctorInspectresource.getStartTime()), "start_time", doctorInspectresource.getStartTime())
			.le(ObjectUtil.isNotNull(doctorInspectresource.getEndTime()), "end_time", doctorInspectresource.getEndTime())
		));
    }

    /**
     * 列表查询
     * @param doctorInspectresource 检查资源
     * @return R
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    public R getDoctorInspectresourceList(DoctorInspectresource doctorInspectresource) {
        return R.ok(doctorInspectresourceService.list(new QueryWrapper<DoctorInspectresource>()
			.like(StrUtil.isNotBlank(doctorInspectresource.getInspItemName()), "insp_item_name", doctorInspectresource.getInspItemName())
		));
    }

    /**
     * 分组查询头部信息
     * @param args 入参
     * @return R
     */
    @ApiOperation(value = "分组查询头部信息", notes = "分组查询头部信息")
    @GetMapping("/list/group")
    public R getDoctorInspectresourceGroupList(InspSourcesVO args) {
        return R.ok(doctorInspectresourceService.list(new QueryWrapper<DoctorInspectresource>()
			.select("SUM(quantity) as quantity,insp_item_date,insp_item_week,insp_item_ap")
			.between("insp_item_date", args.getStartDate(), args.getEndDate())
				.groupBy("insp_item_date,insp_item_week,insp_item_ap")
		),  ReturnMsgConstants.QUERY_SUCCESS);
    }

    /**
     * 分组查询详情信息
     * @param args 入参
     * @return R
     */
    @ApiOperation(value = "分组查询详情信息", notes = "分组查询详情信息")
    @GetMapping("/detail/group")
    public R getDoctorInspectresourceGroupDetail(InspSourcesVO args) {
        return R.ok(doctorInspectresourceService.list(new QueryWrapper<DoctorInspectresource>()
				.select("SUM(quantity) as quantity,insp_item_date,insp_item_week,insp_item_ap,period")
				.eq("insp_item_date", args.getQueryDate())
				.eq("insp_item_ap", args.getInspItemAp())
				.groupBy("insp_item_date,insp_item_week,insp_item_ap,period")
		), ReturnMsgConstants.QUERY_SUCCESS);
    }

	/**
	 * 查询检查资源字典
	 * @return R
	 */
	@ApiOperation(value = "查询检查资源字典", notes = "查询检查资源字典")
	@GetMapping("/dict")
	@Cacheable(value = KrbConstants.ED_INSPECTION_RESOURCE_DICT, unless = "#result == null")
	public R getInspectionResourceDict() {
		return R.ok(doctorInspectresourceService.list(new QueryWrapper<>()));
	}

    /**
     * 通过id查询检查资源
     * @param inspResourceId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{inspResourceId}")
    public R getById(@PathVariable("inspResourceId") Long inspResourceId) {
        return R.ok(doctorInspectresourceService.getById(inspResourceId));
    }

    /**
     * 新增检查资源
     * @param doctorInspectresource 检查资源
     * @return R
     */
    @ApiOperation(value = "新增检查资源", notes = "新增检查资源")
    @SysLog("新增检查资源")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('base_doctorinspectresource_add')")
	@CacheEvict(value = {KrbConstants.ED_INSPECTION_RESOURCE_DICT}, allEntries = true)
	public R save(@RequestBody DoctorInspectresource doctorInspectresource) {
        return R.ok(doctorInspectresourceService.save(doctorInspectresource));
    }

    /**
     * 修改检查资源
     * @param doctorInspectresource 检查资源
     * @return R
     */
    @ApiOperation(value = "修改检查资源", notes = "修改检查资源")
    @SysLog("修改检查资源")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('base_doctorinspectresource_edit')")
	@CacheEvict(value = {KrbConstants.ED_INSPECTION_RESOURCE_DICT}, allEntries = true)
	public R updateById(@RequestBody DoctorInspectresource doctorInspectresource) {
        return R.ok(doctorInspectresourceService.updateById(doctorInspectresource));
    }

    /**
     * 通过id删除检查资源
     * @param inspResourceId id
     * @return R
     */
    @ApiOperation(value = "通过id删除检查资源", notes = "通过id删除检查资源")
    @SysLog("通过id删除检查资源")
    @DeleteMapping("/{inspResourceId}")
    @PreAuthorize("@pms.hasPermission('base_doctorinspectresource_del')")
	@CacheEvict(value = {KrbConstants.ED_INSPECTION_RESOURCE_DICT}, allEntries = true)
	public R removeById(@PathVariable String inspResourceId) {
        return R.ok(doctorInspectresourceService.removeById(inspResourceId));
    }

}
