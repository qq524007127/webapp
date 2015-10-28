(function(win){
	var bsRecord = {};
	bsRecord.initOnMember = function(memberId){
		bsRecord.memberId = memberId;
		var url = 'api/bsRecord_grid.action';
		if(bsRecord.memberId){
			url += '?memberId=' + bsRecord.memberId;
		}
		bsRecord.init(url);
	};
	bsRecord.initOnEnterprise = function(enterId){
		bsRecord.enterId = enterId;
		var url = 'api/bsRecord_grid.action';
		if(bsRecord.enterId){
			url += '?enterpriseId=' + bsRecord.enterId;
		}
		bsRecord.init(url);
	};
	bsRecord.init = function(url){
		initDataGrid(url);
	};
	
	/**
	 * 退回捐赠的福位
	 */
	bsRecord.removeBSRecord = function(rows){
		if(rows.length < 1){
			$.messager.alert('','操作数据不能为空');
			return;
		}
		var ids = '';
		for(var i = 0; i < rows.length; i ++){
			var row = rows[i];
			if(!row.payed || row.donatType == 'lease'){
				$.messager.alert('','只能退捐赠普通捐赠且已经付款的福位');
				return;
			}
			ids += row.bsRecId + ',';
		}
		$.ajax({
			url:'api/bsRecord_exitBuyed.action',
			type:'POST',
			data:{
				ids:ids
			},
			success:function(data){
				data = $.parseJSON(data);
				$.messager.alert('',data.msg);
				if(data.success){
					$('#bsRecordGrid').datagrid('load');
				}
			}
		});
	};
	
	/**
	 * 删除未付款的项目
	 */
	bsRecord.removeUnPayed = function (rows){
		if(rows.length < 1){
			$.messager.alert('','操作数据不能为空');
			return;
		}
		var ids = '';
		for(var i = 0; i < rows.length; i ++){
			var row = rows[i];
			if(row.payed){
				$.messager.alert('','只能删除未付款项目,已付款项目不能删除！');
				return;
			}
			ids += row.bsRecId + ',';
		}
		$.ajax({
			url:'api/bsRecord_removeUnPayedItems.action',
			type:'POST',
			data:{
				ids:ids
			},
			success:function(data){
				data = $.parseJSON(data);
				$.messager.alert('',data.msg);
				if(data.success){
					$('#bsRecordGrid').datagrid('load');
				}
			}
		});
	};
	
	/**
	 * 初始化捐赠福位列表
	 */
	function initDataGrid(url){
		$('#bsRecordGrid').datagrid({
			url:url,
			columns : [ [ {
				field : 'bsRecId',
				title : 'ID',
				width : 10,
				checkbox : true
			},{
				field : 'bsCode',
				title : '福位编号',
				width: 15,
				sortable:true,
				align:'center'
			}, {
				field : 'bsPrice',
				title : '价格',
				width : 20,
				sortable:true,
				align : 'center',
				formatter:function(value){
					if(value){
						return value;
					}
				}
			}, {
				field : 'bsRecToltalPrice',
				title : '捐赠总价',
				width : 10,
				align : 'center',
				formatter:function(value,row,index){
					if(row.donatType == 'buy'){
						return value;
					}
					return '/';
				}
			}, {
				field : 'bsRecCreateDate',
				title : '捐赠(租赁)时间',
				width : 30,
				sortable:true,
				align : 'center'
			}, {
				field : 'donatLength',
				title : '租赁年限',
				width : 20,
				align : 'center',
				formatter:function(value,row,index){
					if(row.donatType == 'buy'){
						return '/';
					}
					return value;
				}
			}, {
				field : 'donatOverdue',
				title : '到期时间',
				width : 20,
				align : 'center',
				formatter:function(value,row,index){
					if(row.donatType == 'buy'){
						return '/';
					}
					return value;
				}
			}, {
				field : 'donatType',
				title : '捐赠方式',
				width : 20,
				sortable:true,
				align : 'center',
				formatter:function(value){
					if(value == 'lease'){
						return '租赁';
					}
					return '捐赠';
				}
			}, {
				field : 'payed',
				title : '是否已付款',
				width : 20,
				align : 'center',
				formatter:function(value){
					if(value){
						return '已付款';
					}
					return '未付款';
				}
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
			toolbar : [ {
				text:'打印捐赠证书',
				iconCls:'icon-print',
				handler:function(){
					var rows = $('#bsRecordGrid').datagrid('getChecked');
					if(rows.length != 1){
						$.messager.alert('','一次只能打印一条数据请勿多选或少选！');
						return;
					}
					
					var _bs = rows[0];
					
					if(bsRecord.memberId){
						printByMemberId(bsRecord.memberId,_bs.bsRecId);
					}
					else if(bsRecord.enterId){
						printByEnterId(bsRecord.enterId,_bs.bsRecId);
					}
					
				}
			},'-',{
				text : '退捐',
				iconCls : 'icon-remove',
				handler : function() {
					var rows = $('#bsRecordGrid').datagrid('getChecked');
					if(rows.length < 1){
						$.messager.alert('','请选择需要操作的数据');
						return;
					}
					$.messager.confirm('警告','确定要退捐赠吗？',function(flag){
						if(flag){
							bsRecord.removeBSRecord(rows);
						}
					});
				}
			},'-',{
				text : '删除未付款项目',
				iconCls : 'icon-cancel',
				handler : function() {
					var rows = $('#bsRecordGrid').datagrid('getChecked');
					if(rows.length < 1){
						$.messager.alert('','请选择需要操作的数据');
						return;
					}
					$.messager.confirm('警告','确定要删除选中的数据吗？',function(flag){
						if(flag){
							bsRecord.removeUnPayed(rows);
						}
					});
				}
			}],
			rowStyler: function(index,row){
				if (!row.payed){
					return 'color:red;';
				}
				if (!row.state.flag){
					return 'color:white; background-color:red';
				}
			},
			loadFilter:function(data){
				for(var i = 0; i < data.rows.length; i ++){
					data.rows[i].bsCode = data.rows[i].blessSeat.bsCode;
					data.rows[i].bsPrice = data.rows[i].blessSeat.lev.levPrice;
				}
				return data;
			},
			onLoadError:function(){
				$.messager.alert('','加载数据出错了！');
			},
			onBeforeLoad:function(param){
				if(param.sort){
					switch (param.sort) {
					case 'bsCode':
						param.sort = 'blessSeat.bsCode';
						break;
					case 'bsPrice':
						param.sort = 'blessSeat.lev.levPrice';
						break;
					default:
						break;
					}
				}
			},
			fit : true,
			title : '捐赠福位列表',
			fitColumns : true,
			pageSize:20,
			rownumbers : true,
			striped : true,
			pagination : true
		});
	}
	
	/**
	 * 根据会员ID及捐赠记录ID打印捐赠证书
	 * @param memberId 会员ID;bsRecId 捐赠记录ID
	 * 
	 */
	function printByMemberId(memberId,bsRecId){
		var url = getUrl(bsRecId) + '&memberId=' + memberId;
	}
	
	function printByEnterId(enterId,bsRecId){
		var url = getUrl(bsRecId) + '&enterpriseId=' + enterId;
	}

	function getUrl(recId){
		var url = win.app.baseUrl + '/download/bsRecord_getBSRecordFile.action?bsRecId' + recId;
		return url;
	}
	
	win.bsRecord = bsRecord;
})(window);