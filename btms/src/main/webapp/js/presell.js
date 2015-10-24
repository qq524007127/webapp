(function($,win) {

	var config = {
			isMember:true
	};
	
	
	$(function() {
		var memberId = $('#memberId').val();
		var enterpriseId = $('#enterpriseId').val();
		var param = {};
		if(memberId){
			config.isMember = true;
			config.memberId = memberId;
			param.memberId = memberId;
		}
		else if(enterpriseId){
			config.isMember = false;
			config.enterpriseId = enterpriseId;
			param.enterpriseId = enterpriseId;
		}
		initGridToolbar();
		initPrsellGrid(param);
	});

	function initGridToolbar() {
		$('#presellGridSearchBox').searchbox({
			width : 300,
			prompt : '输入关键字搜索',
			menu : '#searchboxMenu',
			searcher : function(value, name) {
				var param = {};
				if (name && value) {
					param.searchName = name;
					param.searchValue = value;
				}
				$('#presellGrid').datagrid('load', param);
			}
		});

		$('#presellBtn').click(function() {
			$('#presellWindow').dialog('open');
			$('#addForm').form('reset');
		});

		$('#cashBtn').click(function(){
			var rows = $('#presellGrid').datagrid('getChecked');
			if (rows.length != 1) {
				$.messager.alert('', '一次只能操作一条数据,请勿都选或少选');
				return;
			}
			var ps = rows[0];
			if(ps.cash){
				$.messager.alert('', '勾选的订单已经补单，请勿重复操作！');
				return;
			}
			var psList = $('#prsellList');
			for(var i = 0; i < ps.psCount; i ++){
				var template = '<li><input name="bsIds" type="hidden"><input name="psItem" type="text" width=300 readonly="readonly"  onclick="chooseBs(this)"></li>';
				psList.append(template);
			}
			$('#cashDialog').dialog({
				width:300,
				height:400,
				title:'选择福位',
				iconCls:'icoc-ok',
				modal:true,
				buttons:[{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						var psItems = $('#cashForm input[name=psItem]');
						var bsItems = $('#cashForm input[name=bsIds]');
						for(var i = 0; i < psItems.length; i ++){
							var ele = psItems[i];
							if(!$(ele).val()){
								$.messager.alert('','第"' + (i + 1) + '"项还未选择福位，请选择福位后再提交！');
								return;
							}
						}
						if(psItems.length != bsItems.length){
							$.messager.alert('','出错了，请刷新后再试！');
							return;
						}
						for(var i = 0; i < bsItems.length; i ++){
							var ele = bsItems[i];
							if(!$(ele).val()){
								$.messager.alert('','出错了，请刷新后再试！');
								return;
							}
						}
						for(var i = 0; i < bsItems.length; i ++){
							for(var j = 0; j < bsItems.length; j ++){
								if(i != j && $(bsItems[i]).val() == $(bsItems[j]).val()){
									$.messager.alert('','第"' + (i + 1) + '"与"第"' + (j + 1) + '"的福位重复，福位不能重复请重新选择！');
									return;
								}
							}
						}
						$('#cashForm').form('submit',{
							success:function(data){
								data = $.parseJSON(data);
								$.messager.show({
									msg:data.msg
								});
								if(data.success){
									$('#cashDialog').dialog('close');
									$('#presellGrid').datagrid('reload');
								}
							}
						});
					}
				}],
				onOpen:function(){
					$('#cashForm').form('reset');
					$('#cashForm input[name=bsIds]').val('');
				},
				onClose:function(){
					psList.html('');
				}
			});
			$('#cashForm input[name=psId]').val(ps.psId);
		});
		
		$('#printBtn').click(function(){
			var rows = $('#presellGrid').datagrid('getChecked');
			if (rows.length != 1) {
				$.messager.alert('', '一次只能打印一条数据,请勿都选或少选');
				return;
			}
			var ps = rows[0];
			var url = window.app.host + "/download/preSellInfo.action?psId=" + ps.psId;
			$.openExcelPreview(url,{paperName:'zdy1'});
		});
		
		$('#printCashBtn').click(function(){
			var rows = $('#presellGrid').datagrid('getChecked');
			if (rows.length != 1) {
				$.messager.alert('', '一次只能打印一条数据,请勿都选或少选');
				return;
			}
			var ps = rows[0];
			if(!ps.cash){
				$.messager.alert('', '还未补单不能打印，请补单后再试');
				return;
			}
			var url = window.app.host + "/download/preSellCashInfo.action?psId=" + ps.psId;
			$.openExcelPreview(url,{paperName:'zdy1'});
		});
		
		$('#cancelBtn').click(function() {
			var rows = $('#presellGrid').datagrid('getChecked');
			if (rows.length < 1) {
				$.messager.alert('', '请选择数据');
				return;
			}
			var psIds = '';
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].cash){
					$.messager.alert('','订单号为："' + rows[i].orderCode + '"的预订单已经补单，不能取消。');
					return;
				}
				psIds += rows[i].psId + ",";
			}
			$.messager.confirm('警告', '订单取消后不可恢复，是否继续？', function(flag){
				if(flag){
					$.post('api/presell_cancel.action', {
						psIds : psIds
					}, function(data) {
						data = $.parseJSON(data);
						$.messager.show({
							msg : data.msg
						});
						if (data.success) {
							$('#presellGrid').datagrid('load');
						}
					}, 'text');
				}
			});
		});
	}
	
	function initPrsellGrid(param) {
		$('#presellGrid').datagrid({
			url : 'api/presell_grid.action',
			columns : [ [ {
				field : 'psId',
				title : 'ID',
				width : 10,
				checkbox : true
			}, {
				field : 'orderCode',
				title : '订单号',
				width : 20,
				align : 'center'
			}, {
				field : 'createDate',
				title : '预定时间',
				width : 30,
				align : 'center',
				sortable:true
			}, {
				field : 'psPrice',
				title : '预定单价',
				width : 15,
				align : 'center',
				sortable:true
			}, {
				field : 'psCount',
				title : '预定数量',
				width : 20,
				sortable : true,
				align : 'center',
				sortable:true
			}, {
				field : 'totalPrice',
				title : '预定总价',
				width : 20,
				sortable : true,
				align : 'center',
				sortable:true
			}, {
				field : 'cashDate',
				title : '补单时间',
				width : 30,
				align : 'center',
				sortable:true,
				formatter : function(value) {
					if (!value) {
						return '/';
					}
					return value;
				}
			}, {
				field : 'cash',
				title : '是否补单',
				width : 20,
				sortable : true,
				align : 'center',
				sortable:true,
				formatter : function(value) {
					if (value) {
						return '已补单';
					}
					return '<span style="color:red;">未补单</span>';
				}
			} ] ],
			toolbar : '#toolbarPanel',
			fit : true,
			title : '预定列表',
			fitColumns : true,
			pageSize : 20,
			queryParams:param,
			rownumbers : true,
			striped : true,
			pagination : true
		});
	}
	
	win.appConfig = config;
})(jQuery,window);

var _initedBsGrid = false;

function submitForm() {
	var url = '';
	if(appConfig.isMember){
		url = 'api/presell_addByMember.action';
	}
	else{
		url = 'api/presell_addByEnterprise.action';
	}
	$('#addForm').form('submit',{
		url:url,
		success : function(data) {
			data = $.parseJSON(data);
			$.messager.show({
				msg : data.msg
			});
			if (data.success) {
				$('#presellWindow').dialog('close');
				$('#presellGrid').datagrid('load');
			}
		}
	});
}

function sumTotalPrice() {
	var count = $('#psCount').numberbox('getValue');
	var price = $('#psPrice').numberbox('getValue');
	$('#totalPrice').numberbox('setValue',count*price);
}

function chooseBs(obj){
	
	var psInput = $(obj);
	var bsIdInput = psInput.prev();

	executCheckBs(function(bs){
		if(bs){
			psInput.val(bs.bsCode);
			bsIdInput.val(bs.bsId);;
		}
	});
}

function executCheckBs(callback){
	$('#blessSeatGridWin').dialog('open');
	initEnableBsGrid(callback);
}

function initEnableBsGrid(callback){
	if(!_initedBsGrid){
		initBsGrid();
	}
	else {
		$('#bsGrid').datagrid('load',{});
	}

	$('#checkBsBtn').click(function(){
		var rows = $('#bsGrid').datagrid('getChecked');
		if(rows.length < 1){
			$.messager.alert('','请勾选要选择的福位');
			return;
		}
		if(callback){
			callback(rows[0]);
			callback = undefined;
		}

		$('#blessSeatGridWin').dialog('close');
	});
	
	$('#bsgridSearchbox').searchbox({
		width:300,
		prompt:'输入关键字搜索...',
		menu:'#bsgridsearchboxMenu',
		searcher:function(value,name){
			var queryParams = $('#bsGrid').datagrid('options').queryParams;
			if(value){
				queryParams.searchKey = value;
			}
			else{
				queryParams.searchKey = '';
			}
			$('#bsGrid').datagrid({
				queryParams:queryParams
			});
			$('#bsGrid').datagrid('load');
		}
	});
	$('#bsgridSearchbox').searchbox('clear');
}

function initBsGrid() {
	$('#bsGrid').datagrid({
		url:'api/memberPay_blessSeatGrid.action',
		columns:[[{
			field:'bsId',
			title:'ID',
			width:10,
			checkbox:true
		},{
			field:'bsCode',
			title:'福位编号',
			width:20,
			align:'center',
			sortable:true
		},{
			field:'lev',
			title:'所属级别',
			width:15,
			align:'center',
			sortable:true,
			formatter:function(value){
				if(value){
					return value.levName + "/" + value.levPrice;
				}
			}
		},{
			field:'managExpense',
			title:'管理费',
			width:15,
			align:'center',
			sortable:true
		}]],
		toolbar:'#bsgridToolbarPanel',
		onBeforeLoad:function(params){
			if(params.sort){
				switch (params.sort) {
				case 'lev':
					params.sort = 'lev.levPrice';
					break;
				default:
					break;
				}
			}
		},
		fit:true,
		border:false,
		singleSelect:true,
		fitColumns : true,
		rownumbers : true,
		pageSize:20,
		striped : true,
		pagination : true
	});
	
	_initedBsGrid = true;
}
