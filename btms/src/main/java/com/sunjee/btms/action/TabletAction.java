package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.Tablet;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.TabletService;

@Controller("tabletAction")
@Scope("prototype")
public class TabletAction extends BaseAction<Tablet> implements
		ModelDriven<Tablet> {

	private static final long serialVersionUID = -7705719324275531442L;

	private TabletService tabletService;

	private Tablet tablet;

	public TabletService getTabletService() {
		return tabletService;
	}

	@Resource(name = "tabletService")
	public void setTabletService(TabletService tabletService) {
		this.tabletService = tabletService;
	}

	public Tablet getTablet() {
		return tablet;
	}

	public void setTablet(Tablet tablet) {
		this.tablet = tablet;
	}

	@Override
	public Tablet getModel() {
		if (this.tablet == null) {
			this.tablet = new Tablet();
		}
		return this.tablet;
	}

	public String grid() throws Exception {
		Map<String, Object> whereParams = getWhereParams();
		Map<String, SortType> sortParams = getSortParams("tabletPrice");
		setDataGrid(this.tabletService.getDataGrid(getPager(), whereParams, sortParams));;
		return success();
	}
	
	public String add() throws Exception {
		this.tabletService.add(tablet);
		return success();
	}
	
	public String edit() throws Exception {
		this.tabletService.update(tablet);
		return success();
	}
}
