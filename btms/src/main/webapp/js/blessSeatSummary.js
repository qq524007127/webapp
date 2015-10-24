(function(win, $) {
	$(function() {
		initSummaryGrid();
		initGridToolbar();
	});

	function initGridToolbar() {

		var startDateCtr = $('#startDate');
		var endDateCtr = $('#endDate');

		$('#downLoadBtn').click(function() {
			url = win.app.baseUrl + '/download/summary_bsSummaryFile.action?';
			var start = startDateCtr.datebox('getValue');
			var end = endDateCtr.datebox('getValue');
			url += 'startDate=' + (start ? start : '');
			url += '&endDate=' + (end ? end : '');
			win.open(url);
		});

		$('#printBtn').click(function() {
			url = '/download/summary_bsSummaryFile.action?';
			var start = startDateCtr.datebox('getValue');
			var end = endDateCtr.datebox('getValue');
			url += 'startDate=' + (start ? start : '');
			url += '&endDate=' + (end ? end : '');
			url = window.app.host + url;
			$.openExcelPreview(url,{});
		});

		$('#searchBtn').click(function() {
			var param = {};
			var start = startDateCtr.datebox('getValue');
			var end = endDateCtr.datebox('getValue');
			if (start) {
				param.startDate = start;
			}
			if (end) {
				param.endDate = end;
			}
			datagridSearch(param);
		});

		$('#resetSearchBtn').click(function() {
			startDateCtr.datebox('clear');
			endDateCtr.datebox('clear');
			datagridSearch({});
		});
	}
	
	function datagridSearch(param) {
		if (param) {
			$('#blessSeatSummaryGrid').datagrid('load', param);
			return;
		}
		$('#blessSeatSummaryGrid').datagrid('load', {});
	}

	/**
	 * 初始化汇总列表
	 */
	function initSummaryGrid() {
		$('#blessSeatSummaryGrid').datagrid({
			url : 'api/dataSummary_grid.action',
			columns : [ [ {
				field : 'createDate',
				title : '统计日期',
				width : 10,
				sortable : true,
				align : 'center'
			}, {
				field : 'bsBuyCount',
				title : '捐赠数量',
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'bsBuyTotalPrice',
				title : '捐赠金额',
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'bsLeaseCount',
				title : '租赁数量',
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'bsLeaseTotalPrice',
				title : "租赁金额",
				align : 'center',
				sortable : true,
				width : 10
			},{ 
				field : 'bsRemain', 
				title : "剩余数量", 
				sortable : true,
				align:'center',
				width:10 
			} ] ],
			toolbar : '#toolbarPanel',
			fit : true,
			title : '福位汇总表',
			pageSize:20,
			fitColumns : true,
			rownumbers : true,
			pagination : true,
			striped : true
		});
	}
})(window, $);