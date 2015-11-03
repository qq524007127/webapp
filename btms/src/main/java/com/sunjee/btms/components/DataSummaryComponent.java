package com.sunjee.btms.components;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

import com.sunjee.btms.service.DataSummaryService;
import com.sunjee.btms.service.PreSellSummaryService;
import com.sunjee.btms.service.SalesSummaryService;
import com.sunjee.component.bean.BaseBean;
import com.sunjee.util.DateUtil;

@Component("dataSummaryComponent")
public class DataSummaryComponent extends BaseBean {

	private static final long serialVersionUID = -3598636128023063275L;
	
	public static final String EXECUTE_SUMMARY_TIME = " 23:59:50";	//每天数据汇总任务的执行时间

	private DataSummaryService dataSummaryService;
	private PreSellSummaryService preSellSummaryService;
	@Resource(name="salesSummaryService")
	private SalesSummaryService salesSummaryService;

	public DataSummaryService getDataSummaryService() {
		return dataSummaryService;
	}

	@Resource(name = "dataSummaryService")
	public void setDataSummaryService(DataSummaryService dataSummaryService) {
		this.dataSummaryService = dataSummaryService;
	}

	public PreSellSummaryService getPreSellSummaryService() {
		return preSellSummaryService;
	}

	@Resource(name = "preSellSummaryService")
	public void setPreSellSummaryService(
			PreSellSummaryService preSellSummaryService) {
		this.preSellSummaryService = preSellSummaryService;
	}

	@PostConstruct
	public void doTimedTask() {
		try {
			new Thread(new Runnable(){
				@Override
				public void run() {
					System.out.println("正在汇总数据...");
					dataSummaryService.addSummaryOnBefore();
					preSellSummaryService.initSumOfBefore();
					salesSummaryService.initSummaryOnBefore(new Date());
					System.out.println("数据汇总完成...");
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date summaryTaskDateTime = DateUtil.parseDateTime(DateUtil.getCurrentDate() + EXECUTE_SUMMARY_TIME);
		executeTimedSummaryTask(summaryTaskDateTime);
	}
	
	/**
	 * 定时统计任务
	 */
	private void executeTimedSummaryTask(final Date taskDateTime){
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("定时任务开始...：" + DateUtil.getCurrentDateTime());
				Date now = new Date();
				try {
					dataSummaryService.initSummaryByDate(now, true);
					preSellSummaryService.initSummaryByDate(now, true);
					salesSummaryService.initSummaryByDate(now, true);
				} catch (Exception e) {
					System.out.println("定时汇总任务出现异常:" + e.getMessage());
					e.printStackTrace();
					
				}
				System.out.println("定时任务结束：" + DateUtil.getCurrentDateTime());
				executeTimedSummaryTask(getNextTaskDateTime());
			}
		}, taskDateTime);
	}
	
	/**
	 * 获取下次汇总任务的执行时间
	 * @return
	 */
	private Date getNextTaskDateTime(){
		Date nextTaskDateTime = DateUtil.parseDateTime(DateUtil.getCurrentDate() + EXECUTE_SUMMARY_TIME);
		nextTaskDateTime = DateUtils.addDays(nextTaskDateTime, 1);
		return nextTaskDateTime;
	}
}
