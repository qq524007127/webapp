$(function() {
	initBlessSeatGrid();
	initSearchComponents();
});

/**
 * 初始化福位列表
 */
function initBlessSeatGrid(){
	$('#blessSeatGrid').datagrid({
		url : 'api/blessSeat_grid.action',
		columns:[[{
			field:'bsId',
			width:10,
			checkbox:true
		},{
			field : 'bsCode',
			title : '编号',
			align: 'center',
			sortable:true,
			width: 10
		}, {
			field : 'shelfCode',
			title : '福位架编号',
			width : 10,
			align : 'center',
			sortable:true
			
		}, {
			field : 'shelfArea',
			title : '所在区域',
			width : 10,
			align : 'center',
			sortable:true
		}, {
			field : 'lev',
			title : '级别(价格)',
			width : 10,
			align : 'center',
			formatter:function(value){
				if(value){
					return value.levName + '/' + value.levPrice;
				}
			}
		}, {
			field : 'managExpense',
			title : '管理费',
			width : 10,
			sortable:true,
			align : 'center'
		}, {
			field : 'shelfRow',
			title : '所在行',
			width : 10,
			sortable:true,
			align : 'center'
		}, {
			field : 'shelfColumn',
			title : '所在列',
			width : 10,
			sortable:true,
			align : 'center'
		}/*, {
			field : 'aa',
			title : '是否已捐赠',
			width : 10,
			align : 'center'
		}*/, {
			field : 'deader',
			title : '是否已使用',
			width : 10,
			align : 'center',
			formatter:function(value){
				if(!value){
					return '未使用';
				}
				return '<span style="color:red;">已使用</span>';
			}
		}, {
			field : 'remark',
			title : '备注',
			width : 50,
			align : 'center'
		}]],
		fit : true,
		title : '福位列表',
		fitColumns : true,
		pageSize:20,
		rownumbers : true,
		striped : true,
		pagination : true,
		loadFilter:function(data){
			var rows = [];
			for(var i = 0; i < data.rows.length; i ++){
				var row = data.rows[i];
				row.shelfCode = row.shelf.shelfCode;
				row.shelfArea = row.shelf.shelfArea.areaName;
				rows.push(row);
			}
			data = {'total':data.total,'rows':rows};
			return data;
		},
		onBeforeLoad:function(params){
			if(params.sort){
				switch (params.sort) {
				case 'shelfArea':
					params.sort = 'shelf.shelfArea.areaName';
					break;
				case 'shelfCode':
					params.sort = 'shelf.shelfCode';
					break;
				case 'lev':
					params.sort = 'lev.levPrice';
					break;
				default:
					break;
				}
			}
		}
	});
}

function initSearchBox(){
	$('#searchBox').searchbox({
		width:300,
		prompt:'输入关键字搜索',
		menu:'#searchBoxMenu',
		searcher:function(value,name){
			var param = {};
			if($.trim(value) && $.trim(name)){
				param.searchName = name;
				param.searchValue = value;
			}
			$('#blessSeatGrid').datagrid('load',param);
		}
	});
}

/**
 * 初始化查询条件
 */
function initSearchComponents(){
	initSearchBox();
	/*$('#areaCombobox').combobox({
		url:'api/getAreas.action',
		valueField:'areaId',
		textField:'areaName',
		editable:false,
		width:80,
		panelHeight:150
	});
	$('#levelCombobox').combobox({
		url:'api/getLevels.action',
		valueField:'levId',
		textField:'levName',
		editable:false,
		panelHeight:150,
		panelWidth:150,
		formatter:function(value){
			return value.levName + '/' + value.levPrice;
		}
	});*/
	
	$('#setLevelForm [name=levelId]').combobox({
		url:'api/getLevels.action',
		valueField:'levId',
		textField:'levName',
		editable:false,
		width:100,
		panelHeight:150,
		panelWidth:150,
		required:true,
		formatter:function(value){
			return value.levName + '/' + value.levPrice;
		}
	});
}

/**
 * 设置福位级别
 */
function setBleassSeatLevel(){
	var rows = $('#blessSeatGrid').datagrid('getChecked');
	if(rows.length < 1){
		$.messager.alert('','请选择需要操作的数据');
		return;
	}
	var bsIds = '';
	for(var i = 0; i < rows.length; i ++){
		bsIds += rows[i].bsId + ',';
	}
	bsIds = bsIds.substring(0, bsIds.length -1);
	$('#setLevelForm').form('clear');
	$('#setLevelForm').form('load',{
		'ids':bsIds
	});
	$('#setLevelWindow').dialog({
		title:'设置福位级别',
		width:250,
		height:130,
		modal:true,
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				$('#setLevelForm').form('submit',{
					success:function(data){
						data = $.parseJSON(data);
						$.messager.alert('',data.msg);
						if(data.success){
							$('#setLevelWindow').dialog('close');
							$('#blessSeatGrid').datagrid('reload');
						}
					}
				});
			}
		}]
	});
}

/**
 * 执行搜索
 */
function doSearch(){
	var areaId = $('#areaCombobox').combobox('getValue');
	var levelId = $('#levelCombobox').combobox('getValue');
	var levedState = $('#leveledCombobox').combobox('getValue');
	var searchKey = $('#searchBox').searchbox('getValue');
	
	var queryParams = {};
	queryParams.levedState = levedState;
	if(searchKey && searchKey.trim()){
		queryParams.searchKey = searchKey;
	}
	if(areaId){
		queryParams.areaId = areaId;
	}
	if(levelId){
		queryParams.levelId = levelId;
	}
	
	$('#blessSeatGrid').datagrid('load',queryParams);
}

function clearSearch(){
	$('#searchForm').form('reset');
	$('#blessSeatGrid').datagrid('load',{
		queryParams:{}
	});
}
