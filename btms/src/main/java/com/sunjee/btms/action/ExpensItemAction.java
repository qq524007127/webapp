package com.sunjee.btms.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.ExpensItem;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.ExpensItemService;

@Controller("expensItemAction")
@Scope("prototype")
public class ExpensItemAction extends BaseAction<ExpensItem> implements
		ModelDriven<ExpensItem> {

	private static final long serialVersionUID = 6422352276373560865L;

	private ExpensItemService expensItemService;

	private ExpensItem expensItem;

	public ExpensItemService getExpensItemService() {
		return expensItemService;
	}

	@Resource(name = "expensItemService")
	public void setExpensItemService(ExpensItemService expensItemService) {
		this.expensItemService = expensItemService;
	}

	public ExpensItem getExpensItem() {
		return expensItem;
	}

	public void setExpensItem(ExpensItem expensItem) {
		this.expensItem = expensItem;
	}

	public String grid() throws Exception {
		Map<String, Object> whereParams = getWhereParams("itemName");
		Map<String, SortType> sortParams = getSortParams();
		setDataGrid(this.expensItemService.getDataGrid(getPager(), whereParams, sortParams));
		return success();
	}
	
	public String add() throws Exception {
		this.expensItemService.add(expensItem);
		return success();
	}
	
	public String edit() throws Exception {
		this.expensItemService.update(expensItem);
		return success();
	}
	
	@Override
	public ExpensItem getModel() {
		if (this.expensItem == null) {
			this.expensItem = new ExpensItem();
		}
		return this.expensItem;
	}

}
