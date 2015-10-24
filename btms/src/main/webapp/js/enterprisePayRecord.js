(function(win){
	var enterPay = {};
	enterPay.init = function(enterpriseId){
		enterPay.enterpriseId = enterpriseId;
		initPayRecordGrid();
	};
	/**
	 * 初始化缴费列表
	 */
	function initPayRecordGrid(){
		var url = 'api/enterprisePayRecord_grid.action';
		if(enterPay.enterpriseId){
			url += '?enterpriseId=' + enterPay.enterpriseId;
		}
		$('#payRecordGrid').datagrid({
			url:url,
			columns : [ [{
				field : 'payDate',
				title : '收款时间',
				width: 15,
				sortable:true,
				align:'center'
			}, {
				field : 'totalPrice',
				title : '收款金额',
				width : 20,
				sortable:true,
				align : 'center'
			}, {
				field : 'payUser',
				title : '收款人',
				width : 10,
				align : 'center',
				formatter:function(value){
					if(value){
						return value.userName;
					}
				}
			},  {
				field : 'payRecId',
				title : '操作',
				width : 10,
				align : 'center',
				formatter:function(value){
					if(!value){
						return;
					}
					value = "'" + value +"'";
					return '<a href="javascript:void(0)" onClick="openExcelPrview('+value+')">打印</a>';
				}
			}] ],
			loadFilter:function(data){
				for(var i = 0; i < data.rows.length; i ++){
					
				}
				return data;
			},
			onLoadError:function(){
				$.messager.alert('','加载数据出错了！');
			},
			singleSelect:true,
			fit : true,
			title : '缴费记录',
			fitColumns : true,
			rownumbers : true,
			pageSize:20,
			striped : true,
			pagination : true
		});
	}
	
	win.enterPay = enterPay;
})(window);

function openExcelPrview(recordId){
	var url = window.app.host + "/download/payInfo.action?payRecId=" + recordId;
	$.openExcelPreview(url,{});
};