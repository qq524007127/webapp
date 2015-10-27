package com.sunjee.btms.common;

import com.sunjee.component.bean.BaseBean;

public class Constant extends BaseBean {

    private static final long serialVersionUID = -8038749523743108851L;

    public final static String DEFUALT_DATE_FORMAT = "yyyy-MM-dd";    //默认日期格式
    public final static String DEFUALT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";    //默认日期时间格式
    public final static int DEFUALT_DOWNLOAD_BUFFER_SIZE = 1024 * 5;    //下载默认缓存大小5K
    public final static String EXCEL_SUFFIX = ".xls";

    /**
     * 用户初始密码
     */
    public final static String INIT_PASSWORD = "123456";

    /**
     * 收费类型相关
     **/

    public final static int COST_GROUP = 0;
    /**
     * 普通收费项目
     */
    public final static int COMMON_COST_TYPE = 0;
    /**
     * 会员费
     */
    public final static int MEMBER_COST_TYPE = 1;
    /**
     * 福位管理费类型
     */
    public final static int MANAGE_COST_TYPE = 2;
    /**
     * 福位租赁费
     */
    public final static int BS_LEASE_COST_TYPE = 3;

    /**End**/


    /** 系统资源文件相关 **/
    /**
     * 资源文件根目录
     */
    public final static String RESOURCE_PATH = "/resource/";
    /**
     * 临时文件夹
     */
    public final static String TEMP_PATH = RESOURCE_PATH + "temp/";
    /**
     * 资源文件根目录
     */
    public final static String TEMPLATE_PATH = RESOURCE_PATH + "template/";
    /**
     * 数据汇总表模板
     */
    public final static String SUMMARY_TEMPLATE_NAME = TEMPLATE_PATH + "summary.tpl";
    /**
     * 按经办人统计汇总表模板
     */
    public final static String SALES_SUMMARY_TEMPLATE_NAME = TEMPLATE_PATH + "salesSummary.tpl";
    /**
     * 福位汇总表模板
     */
    public final static String BLESSSEAT_SUMMARY_TEMPLATE_NAME = TEMPLATE_PATH + "bsSummary.tpl";
    /**
     * 牌位汇总表模板
     */
    public final static String TABLET_SUMMARY_TEMPLATE_NAME = TEMPLATE_PATH + "tabletSummary.tpl";
    /**
     * 其它费用汇总表模板
     */
    public final static String ITEM_SUMMARY_TEMPLATE_NAME = TEMPLATE_PATH + "itemSummary.tpl";

    /**
     * 收费清单模板
     */
    public final static String PAYINFO_TEMPLATE_NAME = TEMPLATE_PATH + "payInfo.tpl";

    /**
     * 预售统计模板
     */
    public final static String PRESELL_TEMPLATE_NAME = TEMPLATE_PATH + "presell_summary.tpl";

    /**End**/

}
