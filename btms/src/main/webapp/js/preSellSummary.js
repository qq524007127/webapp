(function(win,$){
	$(function(){
		initGrid();
		initGridToolbar();
	});
	
	function initGrid() {
		$('#preSellSummaryGrid').datagrid({
			url:'api/preSellSummary_grid.action',
			columns : [ [ {
				title : '',
				colspan : 1
			}, {
				title : "预售统计",
				colspan : 2
			}, {
				title : "预售补单统计",
				colspan : 4
			},{
				title : '',
				colspan : 1
			} ], [ {
				field : 'createDate',
				title : '统计日期',
				width : 15,
				sortable : true,
				align : 'center'
			}, {
				field : 'psCount',
				title : '预售数量',
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'psTotal',
				title : '预售金额(PR)',
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'cashCount',
				title : '补单数量',
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'shouldCharge',
				title : "应收金额(S)",
				align : 'center',
				sortable : true,
				width : 10
			}, {
				field : 'psCharge',
				title : "预售定金(P)",
				align : 'center',
				sortable : true,
				width : 10,
				formatter:function(value){
					return '<span style="color:red;">'+value+'</span>';
				}
			},{ 
				field : 'realCharge', 
				title : "实收金额(R=S-P)", 
				sortable : true,
				align:'center',
				width:10 
			}, {
				field : 'total',
				title : "小计(PR+R)",
				align : 'center',
				sortable : true,
				width : 10
			} ] ],
			toolbar : '#girdToolbarPanel',
			fit : true,
			pageSize:20,
			title : '预售汇总表',
			fitColumns : true,
			rownumbers : true,
			pagination : true,
			striped : true
		});
	}
	
	function initGridToolbar() {
		$('#downloadBtn').click(function(){
			var url = win.app.baseUrl + '/download/preSellSummary.action?';
			url += 'startDate=' + $('#startDate').datebox('getValue');
			url += '&endDate=' + $('#endDate').datebox('getValue');
			win.open(url);
		});
		
		$('#printBtn').click(function(){
			var url = win.app.host + '/download/preSellSummary.action?';
			url += 'startDate=' + $('#startDate').datebox('getValue');
			url += '&endDate=' + $('#endDate').datebox('getValue');
			$.openExcelPreview(url,{});
		});
		
		$('#searchBtn').click(function(){
			var param = {};
			param.startDate = $('#startDate').datebox('getValue');
			param.endDate = $('#endDate').datebox('getValue');
			$('#preSellSummaryGrid').datagrid('load',param);
		});
	}
})(window,jQuery);