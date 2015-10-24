package com.sunjee.btms.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Area;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Level;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.BlessSeatService;
import com.sunjee.btms.service.ShelfService;
import com.sunjee.util.HqlLikeType;
import com.sunjee.util.HqlNullType;
import com.sunjee.util.LikeType;

@Controller("blessSeatAction")
@Scope("prototype")
public class BlessSeatAction extends BaseAction<BlessSeat> implements
		ModelDriven<BlessSeat> {

	private static final long serialVersionUID = 3053794916587390844L;

	private BlessSeatService blessSeatService;
	private ShelfService shelfService;

	private BlessSeat blessSeat;
	private List<Area> areaList;

	private String ids;
	
	/*查询功能*/
	private String levelId;
	private String areaId;
	private String levedState;

	public BlessSeatService getBlessSeatService() {
		return blessSeatService;
	}

	@Resource(name = "blessSeatService")
	public void setBlessSeatService(BlessSeatService blessSeatService) {
		this.blessSeatService = blessSeatService;
	}

	public ShelfService getShelfService() {
		return shelfService;
	}

	@Resource(name = "shelfService")
	public void setShelfService(ShelfService shelfService) {
		this.shelfService = shelfService;
	}

	public BlessSeat getBlessSeat() {
		return blessSeat;
	}

	public void setBlessSeat(BlessSeat blessSeat) {
		this.blessSeat = blessSeat;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getLevedState() {
		return levedState;
	}

	public void setLevedState(String levedState) {
		this.levedState = levedState;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String grid() {
		Map<String, Object> whereParams = getWhereParams();
		
		/**
		 * 过滤掉福位架已禁用的福位
		 */
		whereParams.put("shelf.permit", true);
		
		if (!StringUtils.isEmpty(this.searchKey)) {
			whereParams.put("bsCode", new HqlLikeType(searchKey,
					LikeType.allLike));
		}
		if(!StringUtils.isEmpty(areaId)){
			whereParams.put("shelf.shelfArea.areaId", areaId);
		}
		if(!StringUtils.isEmpty(levelId)){
			whereParams.put("lev.levId", levelId);
		}
		if(!StringUtils.isEmpty(levedState)){
			int state = 0;
			try {
				state = Integer.parseInt(levedState);
			} catch (Exception e) {}
			switch (state) {
			case 1:	//已设置级别
				whereParams.put("lev", HqlNullType.isNotNull);
				break;
			case 2:	//未设置级别
				whereParams.put("lev", HqlNullType.isNull);
				break;

			default:
				break;
			}
		}
		Map<String, SortType> sortParams = getSortParams();
		Pager pager = getPager();
		this.setDataGrid(this.blessSeatService.getDataGrid(pager, whereParams, sortParams));
		return success();
	}

	/**
	 * 设置福位的级别
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateBSLevel() throws Exception {
		if (!StringUtils.isEmpty(ids) && !StringUtils.isEmpty(levelId)) {
			String bsIds[] = ids.split(",");
			Level level = new Level(levelId);
			this.blessSeatService.updateBlessSeatLeve(bsIds, level);
		}
		return success();
	}

	public String getAreas() throws Exception {
		this.areaList = shelfService.getAreaList();
		return success();
	}

	@Override
	public BlessSeat getModel() {
		if (blessSeat == null) {
			blessSeat = new BlessSeat();
		}
		return null;
	}

}
