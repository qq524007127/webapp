package com.sunjee.btms.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Level;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.service.LevelServcie;

@Controller("levelAction")
@Scope("prototype")
public class LevelAction extends BaseAction<Level> implements ModelDriven<Level> {

	private static final long serialVersionUID = -7922273402647103059L;

	private LevelServcie levelService;

	private Level level;
	private List<Level> levList;

	public LevelServcie getLevelService() {
		return levelService;
	}

	@Resource(name = "levelService")
	public void setLevelService(LevelServcie levelService) {
		this.levelService = levelService;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public List<Level> getLevList() {
		return levList;
	}

	public void setLevList(List<Level> levList) {
		this.levList = levList;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String grid() throws Exception {
		this.setDataGrid(this.levelService.getDataGrid(new Pager(page, rows), null, getSortParams()));
		return success();
	}
	
	public String add() throws Exception {
		this.levelService.add(level);
		return success();
	}
	
	public String edit() throws Exception {
		this.levelService.update(level);
		return success();
	}
	
	public String getLevels() throws Exception {
		this.levList = this.levelService.getAllByParams(null, null, null);
		return success();
	}

	@Override
	public Level getModel() {
		if (this.level == null) {
			this.level = new Level();
		}
		return this.level;
	}

}
