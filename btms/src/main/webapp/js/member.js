$(function() {
	initBlessSeatGrid();
	initSearchBox();
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

/**
 * 初始化福位列表
 */
function initBlessSeatGrid(){
	$('#memberGrid').datagrid({
		url : 'api/member_grid.action',
		columns:[[{
			field:'memberId',
			width:10,
			checkbox:true
		},{
			field : 'memberPassword',
			title : '登陆密码',
			align: 'center',
			hidden:true,
			width: 10
		},{
			field : 'memberName',
			title : '会员姓名',
			align: 'center',
			sortable:true,
			width: 10
		}, {
			field : 'memberIdentNum',
			title : '身份证号',
			width : 20,
			align : 'center',
			sortable:true
		}, {
			field : 'memberCard',
			title : '会员证号',
			width : 15,
			align : 'center',
			formatter:function(value){
				if(value){
					return value.cardCode;
				}
				return '<span style="color:red;">还未办理会员证</span>';
			}
		}, {
			field : 'memberSex',
			title : '性别',
			width : 10,
			align : 'center',
			sortable:true
		}, {
			field : 'memberTell',
			title : '联系电话',
			width : 15,
			align : 'center'
		},{
			field:'saler',
			title:'经办人',
			width:10,
			align:'center',
			formatter:function(value){
				if(value){
					return value.salerName;
				}
				return '/';
			}
		}, {
			field : 'memberPermit',
			title : '是否有效',
			width : 10,
			sortable:true,
			align : 'center',
			formatter:function(value){
				if(value){
					return '有效';
				}
				return '<span style="color:red;">无效</span>';
			}
		}, {
			field : 'memberRemark',
			title : '备注',
			width : 30,
			align : 'center',
			formatter:function(value){
				return '<span title="'+value+'">'+value+'</span>';
			}
		}, {
			field : 'bsRecordSet',
			title : '操作',
			width : 30,
			align : 'center',
			formatter:function(value,row,index){
				var text = '';
				text += '<a href="javascript:void(0)" onclick="openBuyedListWindow(\''+row.memberId+'\')">[捐赠详情]</a>';
				text += '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="openPayListWindow(\''+row.memberId+'\')">[缴费记录]</a>';
				text += '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="openRelationWindow(\''+row.memberId+'\')">[社会关系]</a>';
				return text;
			}
		}]],
		fit : true,
		title : '会员列表',
		fitColumns : true,
		pageSize:20,
		rownumbers : true,
		striped : true,
		pagination : true
	});
}

/**
 * 初始化查询条件
 */
function initSearchBox(){
	$('#memberGirdSearchbox').searchbox({
		searcher:function(value,name){
			var param = {};
			if(name&&value){
				param.searchName = name;
				param.searchValue = value;
			}
			$('#memberGrid').datagrid('options').queryParams = param;
			$('#memberGrid').datagrid('load');
		}
	});
}

/**
 * 添加会员
 */
function showAddWindow(){
	$('#addWindow').dialog({
		title:'添加会员',
		iconCls:'icon-add',
		width:700,
		height:550,
		modal:true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				$('#addForm').form('submit',{
					success:function(data){
						data=$.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#addWindow').dialog('close');
							$('#memberGrid').datagrid('load');
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
}

/**
 * 修改会员信息
 */
function showEidtWindow(){
	var rows = $('#memberGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('','一次只能操作一条数据，请勿多选或少选');
		return;
	}
	var member = rows[0];
	$('#editWindow').dialog({
		title:'编辑会员信息',
		iconCls:'icon-edit',
		width:700,
		height:550,
		modal:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#editForm').form('submit',{
					success:function(data){
						data=$.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#editWindow').dialog('close');
							$('#memberGrid').datagrid('reload');
						}
					}
				});
			}
		},{
			text:'重置',
			iconCls:'icon-cancel',
			handler:function(){
				$('#editForm').form('reset');
			}
		}]
	});
	$('#editForm').form('reset');
	$('#editForm').form('load',member);
	$('#editSaler').combobox('setValue',member.saler.salerId);
}

/**
 * 会员捐赠(会员缴费)
 */
function shwoPayWindow(){
	var rows = $('#memberGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('提示','一次只能捐赠一个会员，请勿多选或少选！');
		return;
	}
	if(!rows[0].memberPermit){
		$.messager.alert('警告','你选择的会员无效，不能捐赠');
		return;
	}
	var href = 'admin/memberPay.action?memberId=' + rows[0].memberId;
	$('#memberPayWindow').window({
		title:'会员捐赠',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		draggable:false,
		collapsible:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#memberPayWindow').html('');
		}
	});
}

/**
 * 打开已捐赠福位或牌位窗口
 * @param memberId	会员ID
 */
function openBuyedListWindow(memberId){
	if(!memberId){
		alert('出错了，请联系管理员');
		return;
	}
	var href = 'admin/memberBuyedInfo.action?memberId=' + memberId;
	$('#buyedListWindow').window({
		title:'已捐赠项目',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		draggable:false,
		collapsible:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#buyedListWindow').html('');
		}
	});
}

/**
 * 打开会员缴费记录窗口
 * @param memberId	会员ID
 */
function openPayListWindow(memberId){
	if(!memberId){
		alert('出错了，请联系管理员');
		return;
	}
	var href = 'admin/memberPayRecord.action?memberId=' + memberId;
	$('#payedListWindow').window({
		title:'缴费记录',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		draggable:false,
		collapsible:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#payedListWindow').html('');
		}
	});
}

/**
 * 打开会员社会关系窗口
 * @param memberId	会员ID
 */
function openRelationWindow(memberId){
	if(!memberId){
		alert('出错了，请联系管理员');
		return;
	}
	var href = 'admin/relation.action?memberId=' + memberId;
	$('#relationWindow').window({
		title:'社会关系',
		fit:true,
		modal:true,
		maximizable:false,
		minimizable:false,
		draggable:false,
		collapsible:false,
		content:'<iframe width=100% height=100% frameborder=0 src="'+href+'">',
		onClose:function(){
			$('#relationWindow').html('');
		}
	});
}

/**
 * 会员退会(此功能请谨慎使用)
 */
function memberExite(){
	var rows = $('#memberGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('提示','一次只能选择一条数据请勿多选或少选！');
		return;
	}
	$.messager.confirm('警告', '会员退会后，此会员捐赠的福位和牌位将不再属于此会员,其捐赠福位对应的使用者也将被删除，数据将不可恢复。是否继续？', function(flag){
		if(flag){
			$.ajax({
				url:'api/member_disable.action',
				type:'POST',
				data:{
					memberId:rows[0].memberId
				},
				success:function(data){
					data = $.parseJSON(data);
					$.messager.alert('',data.msg);
					if(data.success){
						$('#memberGrid').datagrid('reload');
					}
				}
			});
		}
	});
}

function showPreSellWindow() {
	var rows = $('#memberGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('提示','一次选择一个会员，请勿多选或少选！');
		return;
	}
	if(!rows[0].memberPermit){
		$.messager.alert('警告','你选择的会员无效，不能进行福位预定，若要预定请先启用此会员');
		return;
	}
	var href = 'admin/presell.action?memberId=' + rows[0].memberId;
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