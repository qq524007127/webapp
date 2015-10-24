$(function() {
	initTabletGrid();
	initAddCostCombobox();
	initEditCostCombobox();
	initSearchbox();
});

/**
 * 初始化福位列表
 */
function initTabletGrid(){
	$('#itemGrid').datagrid({
		url : 'api/expensItem_grid.action',
		columns:[[{
			field:'itemId',
			width:10,
			checkbox:true
		},{
			field : 'itemName',
			title : '项目名称',
			align: 'center',
			width: 20
		}, {
			field : 'itemPrice',
			title : '价格',
			width : 15,
			align : 'center',
			sortable:true
			
		}, {
			field : 'costType',
			title : '费用类型',
			width : 20,
			align : 'center',
			formatter:function(value){
				return app.getCostTypeName(value);
			}
			
		}, {
			field : 'editAble',
			title : '年限是否可编辑',
			width : 20,
			align : 'center',
			sortable:true,
			formatter:function(value){
				if(value){
					return '可编辑';
				}
				return '不可编辑';
			}
		}, {
			field : 'permit',
			title : '是否有效',
			width : 20,
			align : 'center',
			sortable:true,
			formatter:function(value){
				if(value){
					return '有效';
				}
				return '<span style="color:red;">无效</span>';
			}
		}, {
			field : 'itemRemark',
			title : '备注',
			width : 30,
			align : 'center'
		}]],
		toolbar:'#toolbarPanel',
		fit : true,
		title : '收费项目列表',
		pageSize:20,
		fitColumns : true,
		rownumbers : true,
		striped : true,
		pagination : true
	});
}

function initAddCostCombobox(){
	$('#addForm select[name=costType]').combobox({
		url:'api/constant_costTypeList.action',
		valueField:'key',
		textField:'value',
		width:130,
		panelHeight:100,
		required:true,
		editable:false
	});
}
function initEditCostCombobox(){
	$('#editForm select[name=costType]').combobox({
		url:'api/constant_costTypeList.action',
		valueField:'key',
		textField:'value',
		width:130,
		panelHeight:100,
		required:true,
		editable:false
	});
}

function initSearchbox(){
	$('#searchbox').searchbox({
		width:300,
		prompt:'输入关键字搜索',
		menu:'#searchboxMenu',
		searcher:function(value,name){
			var param = {};
			if($.trim(value) && $.trim(name)){
				param.searchName = name;
				param.searchValue = value;
			}
			$('#itemGrid').datagrid('load',param);
		}
	});
}

/**
 * 添加牌位
 */
function doAdd(){
	$('#addWindow').dialog({
		title:'添加收费项目',
		width:500,
		height:280,
		modal:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#addForm').form('submit',{
					success:function(data){
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#addWindow').dialog('close');
							$('#itemGrid').datagrid('load');
						}
					}
				});
			}
		}]
	});
	$('#addForm').form('clear');
	$('#addForm select[name=costType]').combobox('select',0);
	$('#addForm input[name=permit]').prop('checked',true);
	$('#addForm input[name=editAble]').prop('checked',true);
}

/**
 * 修改牌位
 */
function doEdit(){
	var rows = $('#itemGrid').datagrid('getChecked');
	if(rows.length != 1){
		$.messager.alert('','只能对一条数据进行修改，请勿多选或少选！');
		return;
	}
	$('#editWindow').dialog({
		title:'修改收费项目',
		width:500,
		height:280,
		modal:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#editForm').form('submit',{
					success:function(data){
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#editWindow').dialog('close');
							$('#itemGrid').datagrid('load');
						}
					}
				});
			}
		}]
	});
	$('#editForm').form('clear');
	$('#editForm').form('load',rows[0]);
	$('#editForm input[name=permit]').prop('checked',rows[0].permit);
	$('#addForm input[name=editAble]').prop('checked',rows[0].editAble);
}

/**
 * 执行搜索
 */
function doSearch(){
	var searchKey = $('#searchBox').searchbox('getValue');

	var queryParams = {};
	if(searchKey){
		queryParams.searchKey = searchKey;
	}
	
	$('#itemGrid').datagrid('load',queryParams);
}