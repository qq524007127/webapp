(function (win, $) {
    $(function () {
        initSummaryGrid();
        initGridToolbar();
    });

    function initGridToolbar() {

        var startDateCtr = $('#startDate');
        var endDateCtr = $('#endDate');
        var searchBox = $('#grid_searchbox');

        $('#downLoadBtn').click(function () {
            url = win.app.baseUrl + '/download/summary_summaryFile.action?';
            var start = startDateCtr.datebox('getValue');
            var end = endDateCtr.datebox('getValue');
            url += 'startDate=' + (start ? start : '');
            url += '&endDate=' + (end ? end : '');
            win.open(url);
        });

        $('#printBtn').click(function () {
            url = '/download/summary_summaryFile.action?';
            var start = startDateCtr.datebox('getValue');
            var end = endDateCtr.datebox('getValue');
            url += 'startDate=' + (start ? start : '');
            url += '&endDate=' + (end ? end : '');
            url = window.app.host + url;
            $.openExcelPreview(url, {});
        });

        $('#grid_searchbox').searchbox({
            searcher: function (value, name) {
                doGridSearch();
            },
            width: 240,
            menu: '#searchbox_menu',
            prompt: '输入经办人姓名搜索'
        });

        $('#searchBtn').click(function () {
            doGridSearch();
        });

        $('#resetSearchBtn').click(function () {
            startDateCtr.datebox('clear');
            endDateCtr.datebox('clear');
            searchBox.searchbox('reset');
            datagridSearch(null);
        });
    }

    /**
     * 执行搜索
     */
    function doGridSearch() {
        var param = {};
        var start = $('#startDate').datebox('getValue');
        var end = $('#endDate').datebox('getValue');
        var _searchValue = $('#grid_searchbox').searchbox('getValue');
        if (_searchValue) {
            param.searchName = $('#grid_searchbox').searchbox('getName');
            param.searchValue = _searchValue;
        }
        if (start) {
            param.startDate = start;
        }
        if (end) {
            param.endDate = end;
        }
        datagridSearch(param);
    }

    /**
     * 打开打印汇总表窗口
     *
     */
    function openPrintWindow(url) {
        $('#printWindow').window({
            title: '汇总表打印',
            iconCls: 'icon-print',
            fit: true,
            modal: true,
            maximizable: false,
            minimizable: false,
            draggable: false,
            collapsible: false,
            content: '<iframe width=100% height=100% frameborder=0 src="' + url + '">',
            onClose: function () {
                $('#printWindow').html('');
            }
        });
    }


    function datagridSearch(param) {
        if (param) {
            $('#summaryGrid').datagrid('load', param);
            return;
        }
        $('#summaryGrid').datagrid('load', {});
    }

    /**
     * 初始化汇总列表
     */
    function initSummaryGrid() {
        $('#summaryGrid').datagrid({
            url: 'api/salesSummary_grid.action',
            columns: [[{
                title:'',
                colspan:2
            },{
                title: "福位统计",
                colspan: 4
            }, {
                title: "牌位统计",
                colspan: 2
            }, {
                title: "管理费统计",
                colspan: 2
            }, {
                title: "会员费统计",
                colspan: 2
            }, {
                title: "其它费用统计",
                colspan: 2
            },{
                title:'',
                colspan:1
            }], [{
                field: 'createDate',
                title: '统计日期',
                width: 10,
                sortable: true,
                align: 'center'
            }, {
                field: 'saler',
                title: "经办人",
                align: 'center',
                width: 10,
                formatter:function(value){
                    if(value){
                        return value.salerName;
                    }
                    return '/';
                }
            },{
                field: 'bsBuyCount',
                title: '捐赠数量',
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'bsBuyTotalPrice',
                title: '捐赠金额',
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'bsLeaseCount',
                title: '租赁数量',
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'bsLeaseTotalPrice',
                title: "租赁金额",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'tblBuyCount',
                title: "捐赠数量",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'tblTotalPrice',
                title: "捐赠金额",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'memberCount',
                title: "缴费数量",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'memberTotalPrice',
                title: "缴费金额",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'mngRecCount',
                title: "缴费数量",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'mngTotalPrice',
                title: "缴费金额",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'itemCount',
                title: "数量",
                align: 'center',
                sortable: true,
                width: 10
            }, {
                field: 'itemTotalPrice',
                title: "金额",
                align: 'center',
                sortable: true,
                width: 10
            },{
                field:'total',
                title:'小计',
                width:10,
                align:'center',
                sortable:true
            }]],
            toolbar: '#toolbarPanel',
            fit: true,
            pageSize: 20,
            title: '数据汇总表',
            fitColumns: true,
            rownumbers: true,
            pagination: true,
            striped: true
        });
    }
})(window, $);