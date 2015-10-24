(function(win, $) {
	$(function() {
		initSummaryGrid();
		initGridToolbar();
	});

	function initGridToolbar() {

		var startDateCtr = $('#startDate');
		var endDateCtr = $('#endDate');

		$('#downLoadBtn').click(function() {
			url = win.app.baseUrl + '/download/summary_itemSummaryFile.action?';
			var start = startDateCtr.datebox('getValue');
			var end = endDateCtr.datebox('getValue');
			url += 'startDate=' + (start ? start : '');
			url += '&endDate=' + (end ? end : '');
			win.open(url);
		});

		$('#printBtn').click(function() {
			url = '/download/summary_itemSummaryFile.action?';
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
			$('#itemSummaryGrid').datagrid('load', param);
			return;
		}
		$('#itemSummaryGrid').datagrid('load', {});
	}

	/**
	 * 初始化汇总列表
	 */
	function initSummaryGrid() {
		$('#itemSummaryGrid').datagrid({
			url : 'api/dataSummary_grid.action',
			columns : [ [{
				title : '',
				colspan : 1
			}, {
				title : "管理费统计",
				colspan : 2
			}, {
				title : "会员费统计",
				colspan : 2
			}, {
				title : "其它费用统计",
				colspan : 2
			} ], [ {
				field : 'createDate',
				title : '统计日期',
				width : 10,
				sortable : true,
				align : 'center'
			}, {
				field : 'memberCount',
				title : "缴费数量",
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'memberTotalPrice',
				title : "缴费金额",
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'mngRecCount',
				title : "缴费数量",
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'mngTotalPrice',
				title : "缴费金额",
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'itemCount',
				title : "数量",
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'itemTotalPrice',
				title : "金额",
				align : 'center',
				sortable : true,
				width : 10
			} ] ],
			toolbar : '#toolbarPanel',
			fit : true,
			title : '其它收费汇总表',
			fitColumns : true,
			pageSize:20,
			rownumbers : true,
			pagination : true,
			striped : true
		});
	}
})(window, $);