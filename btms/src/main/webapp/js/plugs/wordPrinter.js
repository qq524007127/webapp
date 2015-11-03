/**
 * Created by ShenYunjie on 2015/11/3.
 */

/**
 * 将福位捐赠信息显示到word文档中并打印实现套打
 * @param bsRecord
 */

var wdapp,range,wddoc;

function viewToWord(data,templateUrl){

    try{
        //获取Word 过程
        //请设置IE的可信任站点
        wdapp = new ActiveXObject("Word.Application");
    }catch(e){
        alert("无法调用Office对象，请确保您使用的是IE浏览器且已安装了Office并已将本系统的站点名加入到IE的信任站点列表中！");
        wdapp = null;
        return;
    }

    wdapp.Documents.Open(templateUrl);

    wddoc = wdapp.ActiveDocument;

    range = wdapp.Range;

    initBookmarkValue("BS_CODE",data.blessSeat.bsCode); //福位编号
    initBookmarkValue("MEM_NAME",data.mem.memberName); //会员名称
    initBookmarkValue("AREA_CODE",data.blessSeat.shelf.shelfArea.areaName); //区域名称
    initBookmarkValue("SHELF_CODE",data.blessSeat.shelf.shelfCode); //所在福位架的编号
    initBookmarkValue("BS_ROW",data.blessSeat.shelfRow); //福在福位架的层（行）
    initBookmarkValue("BS_NUM",data.blessSeat.shelfColumn); //福在福位架的号（列）
    var tmp = new Date();
    initBookmarkValue("YEAR",tmp.getFullYear()); //打印年
    initBookmarkValue("MOTH",tmp.getMonth()+1); //打印月
    initBookmarkValue("DAY",tmp.getDate()); //打印日
    initBookmarkValue("MEM_NAME1",data.mem.memberName); //会员名称
    initBookmarkValue("MEM_SEX",data.mem.memberSex); //会员性别
    initBookmarkValue("MEM_BIRTHDAY",data.mem.memberBirthday.substring(0,7)); //会员生日
    initBookmarkValue("MEM_ID_NUM",data.mem.memberIdentNum); //会员身份证号
    initBookmarkValue("PAY_DATE",data.bsRecCreateDate.substring(0,10)); //捐赠日期

    //wdapp.ActiveDocument.ActiveWindow.View.Type = 3;

    wdapp.visible = true;//word模板是否可见


    //wddoc.saveAs("c:\\apply_temp.doc"); //保存临时文件word

    //wdapp.Application.Printout();//调用自动打印功能
    //wddoc.PrintPreview();

    //wdapp.quit();
    //wdapp = null;
}

function initBookmarkValue(bookmark,value){
    range = wddoc.Bookmarks(bookmark).Range;    ////word模板中标签为manager对象
    range.Text = value; //给书签对象赋值（追加）
}