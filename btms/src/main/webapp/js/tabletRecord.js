(function(win){
	var tabletRecord = {};
	
	tabletRecord.initOnMember = function(memberId){
		tabletRecord.memberId = memberId;
		var url = 'api/tabletRecord_grid.action';
		if(tabletRecord.memberId){
			url += '?memberId=' + tabletRecord.memberId;
		}
		tabletRecord.init(url);
	};
	tabletRecord.initOnEnterprise = function(enterId){
		tabletRecord.enterId = enterId;
		var url = 'api/tabletRecord_grid.action';
		if(tabletRecord.enterId){
			url += '?enterpriseId=' + tabletRecord.enterId;
		}
		tabletRecord.init(url);
	};
	
	tabletRecord.init = function(url){
		initDataGrid(url);
	};
	
	/**
	 * 初始化捐赠牌位列表
	 */
	function initDataGrid(url){
		$('#tabletRecordGrid').datagrid({
			url:url,
			columns : [ [ {
				field : 'tlRecId',
				title : 'ID',
				width : 10,
				checkbox : true
			},{
				field : 'tabletName',
				title : '牌位名称',
				width: 15,
				align:'center'
			}, {
				field : 'tabletPrice',
				title : '价格',
				width : 20,
				sortable:true,
				align : 'center'
			}, {
				field : 'tlRecCreateDate',
				title : '捐赠时间',
				width : 10,
				sortable:true,
				align : 'center'
			}, {
				field : 'tlRecLength',
				title : '捐赠年限',
				width : 30,
				sortable:true,
				align : 'center'
			}, {
				field : 'tlRecOverdue',
				title : '到期时间',
				width : 20,
				sortable:true,
				align : 'center'
			}, {
				field : 'state',
				title : '状态',
				width : 20,
				align : 'center',
				formatter:function(value){
					if(value){
						return value.text;
					}
				}
			}] ],
			rowStyler: function(index,row){
				var state = row.state;
				if (state && !state.flag){
					return 'color:white; background-color:red';
				}
			},
			loadFilter:function(data){
				for(var i = 0; i < data.rows.length; i ++){
					data.rows[i].tabletName = data.rows[i].tablet.tabletName;
					data.rows[i].tabletPrice = data.rows[i].tablet.tabletPrice;
				}
				return data;
			},
			onLoadError:function(){
				$.messager.alert('','加载数据出错了！');
			},
			onBeforeLoad:function(param){
				if(param.sort){
					switch (param.sort) {
					case 'tabletPrice':
						param.sort = 'tablet.tabletPrice';
						break;
					default:
						break;
					}
				}
			},
			fit : true,
			title : '捐赠牌位列表',
			fitColumns : true,
			rownumbers : true,
			striped : true,
			pageSize:20,
			pagination : true
		});
	}
	
	win.tabletRecord = tabletRecord;
})(window);