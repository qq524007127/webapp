package com.sunjee.btms.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.sunjee.btms.bean.Saler;
import com.sunjee.btms.common.Constant;
import com.sunjee.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.sunjee.btms.bean.SalesSummary;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.service.SalesSummaryService;
import com.sunjee.util.DateUtil;
import com.sunjee.util.HqlNoEquals;

@Controller("salesSummaryAction")
@Scope("prototype")
public class SalesSummaryAction extends BaseAction<SalesSummary> implements
        ModelDriven<SalesSummary> {

    private static final long serialVersionUID = 181860791928887857L;

    //	按经办人汇总表名称
    private final static String SALER_SUMMARY_FILE_NAME = "经办人汇总表" + Constant.EXCEL_SUFFIX;

    @Resource(name = "salesSummaryService")
    private SalesSummaryService salesSummaryService;
    private SalesSummary salesSummary;
    private Date startDate;
    private Date endDate;

    private String fileName;

    public SalesSummaryService getSalesSummaryService() {
        return salesSummaryService;
    }

    public void setSalesSummaryService(SalesSummaryService salesSummaryService) {
        this.salesSummaryService = salesSummaryService;
    }

    public SalesSummary getSalesSummary() {
        return salesSummary;
    }

    public void setSalesSummary(SalesSummary salesSummary) {
        this.salesSummary = salesSummary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            startDate = DateUtil.getStartTimeOfDay(startDate);
        }
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            endDate = DateUtil.getEndTimeOfDay(endDate);
        }
        this.endDate = endDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        try {
            //将文件名称转换为Struts2的编码格式(struts2的编码格式为:"ISO8859-1")
            this.fileName = new String(fileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.fileName = UUID.randomUUID().toString();
        }
    }

    public String grid() throws Exception {
        Map<String, SortType> sortParams = getSortParams();
        Map<String, Object> whereParams = getWhereParams();
        if (!sortParams.containsKey("createDate")) {
            sortParams.put("createDate", SortType.desc);
        }
        initStartAndEndValue(whereParams);
        this.setDataGrid(this.salesSummaryService.getDataGrid(getPager(), whereParams, sortParams));
        return success();
    }

    public InputStream getSalesSummaryFile() throws Exception {

        Map<String, Object> whereParams = getWhereParams();
        initStartAndEndValue(whereParams);

        Map<String, SortType> sortParams = getSortParams(SortType.desc, "createDate");

        int rowIndex = 2;    //从第三行开始写数据(索引从0开始）

        List<SalesSummary> list = this.salesSummaryService.getAllByParams(null, whereParams, sortParams);
        ServletContext context = ServletActionContext.getServletContext();

        this.setFileName(SALER_SUMMARY_FILE_NAME);
        ExcelUtil excel = new ExcelUtil(Constant.DEFUALT_DATE_FORMAT);
        String fields[] = new String[]{"createDate", "saler","bsBuyCount", "bsBuyTotalPrice", "bsLeaseCount", "bsLeaseTotalPrice",
                "tblBuyCount", "tblTotalPrice", "memberCount", "memberTotalPrice", "mngRecCount", "mngTotalPrice", "itemCount",
                "itemTotalPrice", "total"};
        excel.addSetCellValueCallback("saler", new ExcelUtil.OnSetCellValueCallBack() {
            @Override
            public void doSetCellValue(Cell cell, Object obj) {
                if (obj instanceof Saler) {
                    cell.setCellValue(((Saler) obj).getSalerName());
                } else {
                    cell.setCellValue("/");
                }
            }
        });
        return excel.createExcel(context, Constant.SALES_SUMMARY_TEMPLATE_NAME, rowIndex, fields, list);
    }

    @Override
    public SalesSummary getModel() {
        if (this.salesSummary == null) {
            this.salesSummary = new SalesSummary();
        }
        return this.salesSummary;
    }

    private void initStartAndEndValue(Map<String, Object> whereParams) {
        if (startDate != null && endDate != null) {
            whereParams.put("createDate", new HqlNoEquals(startDate, endDate));
        } else if (startDate != null) {
            whereParams.put("createDate", new HqlNoEquals(startDate, HqlNoEquals.MORE_EQ));
        } else if (endDate != null) {
            whereParams.put("createDate", new HqlNoEquals(endDate, HqlNoEquals.LESS_EQ));
        }
    }
}
