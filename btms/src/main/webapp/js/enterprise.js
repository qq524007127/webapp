$(function(){
	$('#addSaler').combobox({
		url:'api/saler_allSalerList.action',
		valueField:'salerId',
		textField:'salerName',
		editable:false
	});
	$('#editSaler').combobox({
		url:'api/saler_allSalerList.action',
		valueField:'salerId',
		textField:'salerName',
		editable:false
	});
});
(function(win){
	var enter = {};
	enter.init = function(){
		enter.initSelfGrid();
		enter.initSearchBox();
	};
	enter.initSelfGrid = function(){
		$('#enterpriseGrid').datagrid({
			url : 'api/enterprise_grid.action',
			columns : [ [ {
				field : 'enterId',
				title : 'ID',
				width : 10,
				checkbox : true
			},{
				field : 'enterName',
				title : '企业名称',
				width: 15,
				align:'center'
			}, {
				field : 'enterAddress',
				title : '企业地址',
				width : 20,
				align : 'center'
			}, {
				field : 'busLisCode',
				title : '营业执照代码',
				width : 20,
				sortable:true,
				align : 'center'
			}, {
				field : 'card',
				title : '会员证号',
				width : 20,
				align : 'center',
				formatter:function(value){
					if(value){
						return value.cardCode;
					}
					return '<span style="color:red;">还未办理会员证</span>';
				}
			}, {
				field : 'legalPersonName',
				title : '法定代表人姓名',
				width : 20,
				align : 'center'
			}, {
				field : 'enterTell',
				title : '联系电话',
				width : 20,
				align : 'center'
			},{
				field:'saler',
				title:'经办人',
				width:20,
				align:'center',
				formatter:function(value){
					if(value){
						return value.salerName;
					}
					return '/';
				}
			}, {
				field : 'spareName',
				title : '备用联系人',
				width : 20,
				align : 'center'
			}, {
				field : 'spareTell',
				title : '备用联系电话',
				width : 20,
				align : 'center'
			}, {
				field : 'enterPermit',
				title : '是否有效',
				width : 20,
				align : 'center',
				sortable : true,
				formatter:function(val){
					if(val){
						return '有效';
					}
					return '<span style="color:red;">无效</span>';
				}
			}, {
				field : 'enterRemark',
				title : '备注',
				width : 30,
				align : 'center',
				formatter:function(value){
					if(value){
						return '<span title="'+value+'">'+value+'</span>';
					}
				}
			}, {
				field : 'aa',
				title : '操作',
				width : 30,
				align : 'center',
				formatter:function(value,row,index){
					var text = '';
					var enterId = row.enterId;
					text += '<a href="javascript:void(0)" onclick="openBuyedListWindow(\''+enterId+'\')">[捐赠详情]</a>';
					text += '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="openPayListWindow(\''+enterId+'\')">[缴费记录]</a>';
					return text;
				}
			}] ],
			rowStyler:function(index,row){
				/*if(!row.enterPermit){
					return 'background-color:red;';
				}*/
			},
			fit : true,
			title : '企业列表',
			fitColumns : true,
			pageSize:20,
			rownumbers : true,
			striped : true,
			pagination : true
		});
	};
	
	enter.initSearchBox = function(){
		$('#enterpriseSearchBox').searchbox({
			width:300,
			prompt:'输入关键字搜索',
			searcher:function(value,name){
				enter.doSearch(value,name);
			},
			menu:'#searchboxMenu'
		});
	};
	
	enter.doSearch = function(searchValue, searchName){
		var param = {};
		if(searchName && searchValue){
			param.searchName = searchName;
			param.searchValue = searchValue;
		}
		$('#enterpriseGrid').datagrid('options').queryParams = param;
		$('#enterpriseGrid').datagrid('load');
	};
	
	enter.showAddEnterWindow = function (){
		$('#addWindow').dialog({
			title:'添加企业',
			width:650,
			height:450,
			modal:true,
			iconCls:'icon-add',
			buttons:[{
				text:'添加',
				iconCls:'icon-ok',
				handler:function(){
					$('#addForm').form('submit',{
						success:function(data){
							data = $.parseJSON(data);
							$.messager.alert('',data.msg);
							if(data.success){
								$('#addWindow').dialog('close');
								$('#enterpriseGrid').datagrid('load');
							}
						}
					});
				}
			},{
				text:'重置',
				iconCls:'icon-cancel',
				handler:function(){
					$('#addForm').form('reset');
				}
			}]
		});
		$('#addForm').form('reset');
	};
	
	enter.showEidtInfoWindow = function(){
		var rows = $('#enterpriseGrid').datagrid('getChecked');
		if(rows.length != 1){
			$.messager.alert('','一次只能修改一条数据，请勿多选或少选！');
			return;
		}
		$('#editWindow').dialog({
			title:'企业信息修改',
			width:650,
			height:450,
			modal:true,
			iconCls:'icon-edit',
			buttons:[{
				text:'修改',
				iconCls:'icon-ok',
				handler:function(){
					$('#editForm').form('submit',{
						success:function(data){
							data = $.parseJSON(data);
							$.messager.alert('',data.msg);
							if(data.success){
								$('#editWindow').dialog('close');
								$('#enterpriseGrid').datagrid('reload');
							}
						}
					});
				}
			}]
		});
		$('#editForm').form('reset');
		$('#editForm').form('load',rows[0]);
		$('#editForm input[name=enterPermit]').prop('checked',rows[0].enterPermit);
		$('#editSaler').combobox('setValue',rows[0].saler.salerId);
	};
	
	win.enterprise = enter;
	
	$(function(){
		enter.init();
	});
})(window);

function showAddWindow(){
	enterprise.showAddEnterWindow();
}

function showEditWindow(){
	enterprise.showEidtInfoWindow();
}

/**
 * 企业捐赠(企业缴费)
 */
function shwoPayWindow(){
	var rows = $('#enterpriseGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('提示','一次只能操作一条数据，请勿多选或少选！');
		return;
	}
	
	if(!rows[0].enterPermit){
		$.messager.alert('提示','您选择的企业已无效，不能捐赠，请重新选择');
		return;
	}
	
	var href = 'admin/enterprisePay.action?enterId=' + rows[0].enterId;
	$('#enterPayWindow').window({
		title:'企业捐赠',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		draggable:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#enterPayWindow').html('');
		}
	});
}

/**
 * 企业捐赠记录
 */
function openBuyedListWindow(enterpriseId){

	if(!enterpriseId){
		$.messager.alert('警告','出错了，请联系管理员！');
		return;
	}
	
	var href = 'admin/enterpriseBuyedInfo.action?enterpriseId=' + enterpriseId;
	$('#buyedListWindow').window({
		title:'企业捐赠',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		draggable:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#buyedListWindow').html('');
		}
	});
}

/**
 * 企业缴费记录
 */
function openPayListWindow(enterpriseId){

	if(!enterpriseId){
		$.messager.alert('警告','出错了，请联系管理员！');
		return;
	}
	
	var href = 'admin/enterprisePayRecord.action?enterpriseId=' + enterpriseId;
	$('#payedListWindow').window({
		title:'企业缴费记录',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		draggable:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#payedListWindow').html('');
		}
	});
}

/**
 * 企业退会
 */
function exit(){
	var rows = $('#enterpriseGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('提示','一次只能选择一条数据请勿多选或少选！');
		return;
	}
	$.messager.confirm('警告', '会员退会后，此会员捐赠的福位和牌位将不再属于此会员,其捐赠福位对应的使用者也将被删除，数据将不可恢复。是否继续？', function(flag){
		if(flag){
			$.ajax({
				url:'api/enterprise_disable.action',
				type:'POST',
				data:{
					enterId:rows[0].enterId
				},
				success:function(data){
					data = $.parseJSON(data);
					$.messager.alert('',data.msg);
					if(data.success){
						$('#enterpriseGrid').datagrid('reload');
					}
				}
			});
		}
	});
}

function showPreSellWindow() {
	var rows = $('#enterpriseGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('提示','一次选择一家企业，请勿多选或少选！');
		return;
	}
	if(!rows[0].enterPermit){
		$.messager.alert('警告','你选择的企业无效，不能进行福位预定');
		return;
	}
	var href = 'admin/presell.action?enterpriseId=' + rows[0].enterId;
	$('#preSellWindow').window({
		title:'福位预售',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		draggable:false,
		collapsible:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#preSellWindow').html('');
		}
	});
}
