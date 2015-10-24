(function(win){
	var enterPay = {};
	enterPay.init = function(){
		enterPay.enterId = $('#payForm input[name=enterId]').val();
		if(!enterPay.enterId){
			$.messager.alert('警告','企业不存在请关闭窗口，刷新数据后重新选择');
			return;
		}
		getShopBusData();
		initBlessSeatGrid();
		initTabletGrid();
		initExpensItemGrid();
		initBuyedBSGrid();
	};
	
	win.enterPay = enterPay;
	
	$(function(){
		enterPay.init();
	});
})(window);

/**
 * 提交表单
 */
function submitPayForm(){
	if(checkFormIsEmpty()){
		$.messager.alert('','请选择需要捐赠的项目后再提交！');
		return;
	}
	$.messager.confirm('提示','确然要提交吗？',function(flag){
		if(!flag){
			return;
		}
		$.messager.progress({
			text:'数据处理中...'
		});
		$('#payForm').form('submit',{
			success:function(data){
				$.messager.progress('close');
				data = $.parseJSON(data);
				if(data.success){
					if(confirm('捐赠成功，是否打印缴费清单')){
						var result = data.attribute;
						var url = window.app.host + "/download/payInfo.action?payRecId=" + result.payRecId;
						$.openExcelPreview(url,{});
					}
					location.reload();
				}
				else{
					$.messager.alert('',data.msg);
				}
			}
		});
	});
}

/**
 * 检查是否选择了捐赠项目，如果没有则返回true，否则返回false,即检查表达是否为空
 * @returns
 */
function checkFormIsEmpty(){
	var tbNames = ['BsBuyList','tabletBuyList','itemBuyList'];
	for(var i = 0; i < tbNames.length; i ++){
		var tbody = $('#'+ tbNames[i] + ' tbody')[0];
		var tr = $(tbody).children('tr');
		if(tr.length > 0){
			return false;
		}
	}
	return true;
}

/**
 * 删除表格的行
 * @param obj
 */
function deletTR(obj){
	$(obj).parent().parent().remove();
}

function subtotal(obj,price,targetName){
	var tr = $(obj).parent().parent();
	var total = price * $(obj).val();
	var tds = $(tr).children('td');
	for(var i = 0; i < tds.length; i ++){
		var target = $(tds[i]).children('input[name='+targetName+']');
		if(target.length > 0){
			$(target).val(total);
			//subAllTotal();
			return;
		}
	}
}

function subAllTotal(){
	var totals = ['itemTotalPrice','tabletTotalPrice','blessSeatPrices'];
	var total = 0;
	for(var i = 0; i < totals.length; i ++){
		var inputs = $('#payForm input[name='+totals[i]+']');
		$(inputs).each(function(index,ele){
			var val = parseFloat($(ele).val());
			total += val;
		});
	}
	$('#allTotalPrice').html('合计：' + total);
}

/*=====================福位操作开始=========================*/
/**
 * 初始化福位列表
 */
function initBlessSeatGrid(){
	$('#blessSeatGrid').datagrid({
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
		},{
			field:'remark',
			title:'备注',
			width:50,
			align:'center',
			sortable:true
		}]],
		toolbar:'#bsGridTB',
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
		fitColumns : true,
		rownumbers : true,
		pageSize:20,
		striped : true,
		pagination : true
	});
}
/**
 * 显示福位选择窗口
 */
function showBSWindow(){
	$('#blessSeatWindow').dialog({
		width:900,
		height:400,
		modal:true
	});
	
	var bsIdInputs = $('#payForm input[name=blessSeatIds]');
	var queryParams = {};
	
	if(bsIdInputs.length > 0){
		var withoutIds = '';
		for(var i=0;i < bsIdInputs.length; i ++){
			withoutIds += $(bsIdInputs[i]).val() + ',';
		}
		
		queryParams.withoutIds = withoutIds.substring(0, withoutIds.length - 1);

	}

	$('#blessSeatGrid').datagrid({
		queryParams:queryParams
	});
	
	$('#BSGridSearchBox').searchbox('clear');
	$('#blessSeatGrid').datagrid('load');
}

/**
 * 搜索福位
 */
function doBSGridSearch(){
	var searchkey = $('#BSGridSearchBox').searchbox('getValue');
	var queryParams = $('#blessSeatGrid').datagrid('options').queryParams;
	if(searchkey){
		queryParams.searchKey = searchkey;
	}
	else{
		queryParams.searchKey = '';
	}
	$('#blessSeatGrid').datagrid({
		queryParams:queryParams
	});
	$('#blessSeatGrid').datagrid('load');
}


/**
 * 选择福位（普通捐赠），执行此回调方法
 */
function checkBlessSeat(){
	var rows = $('#blessSeatGrid').datagrid('getChecked');
	if(rows.length < 1){
		$.messager.alert('','请选择需要捐赠的福位');
		return;
	}
	
	var ids = '';
	for(var i=0;i < rows.length; i ++){
		ids += rows[i].bsId + ",";
	}
	
	ids = ids.substring(0, ids.length - 1);
	$.ajax({
		url:'api/enterprisePay_addShopBusOnBuy.action',
		type:'POST',
		data:{
			enterId:enterPay.enterId,
			ids:ids
		},
		success:function(data){
			data = $.parseJSON(data);
			if(data.success){
				$('#blessSeatWindow').dialog('close');
				getShopBusData();
			}
		}
	});
}

/**
 * 选择福位（租赁），执行此回调方法
 */
function checkBlessSeatOnLease(){
	var rows = $('#blessSeatGrid').datagrid('getChecked');
	if(rows.length < 1){
		$.messager.alert('','请选择需要捐赠的福位');
		return;
	}
	
	var ids = '';
	for(var i=0;i < rows.length; i ++){
		ids += rows[i].bsId + ",";
	}
	
	ids = ids.substring(0, ids.length - 1);
	$.ajax({
		url:'api/enterprisePay_addShopBusOnLease.action',
		type:'POST',
		data:{
			enterId:enterPay.enterId,
			ids:ids
		},
		success:function(data){
			data = $.parseJSON(data);
			if(data.success){
				$('#blessSeatWindow').dialog('close');
				getShopBusData();
			}
		}
	});
}

/**
 * 获取未付款的数据
 */
function getShopBusData(){

	/**
	 * 先清空数据再重新加载
	 */
	var tBody = $("#BsBuyList>tbody");
	var trs = $(tBody[0]).children('tr');
	$(trs).each(function(index,val){
		$(val).remove();
	});
	
	$.ajax({
		url:'api/enterprisePay_unPayedList.action',
		type:'POST',
		data:{
			enterId:enterPay.enterId
		},
		success:function(rows){
			rows = $.parseJSON(rows);
			var template = '';
			for(var i = 0; i < rows.length; i ++){
				var row = rows[i];
				var bs = row.blessSeat;
				template += '<tr>';
				template += '<td><input type="hidden" value="'+row.bsRecId+'" name="bsRecIds">' + bs.bsCode + '</td>';
				template += '<td>' + bs.lev.levName + '</td>';
				if(row.donatType == "buy"){
					template += '<td><input type="hidden" name="blessSeatPrices" value="'+bs.lev.levPrice+'">' + bs.lev.levPrice + '</td>';
					template += '<td><input type="hidden" value="0" name="donatType">捐赠</td>';
					template += '<td><input type="hidden" name="donatLength" value=0>/</td>';
				}
				else{
					template += '<td><input type="hidden" name="blessSeatPrices" value="0">/</td>';
					template += '<td><input type="hidden" value="1" name="donatType">租赁</td>';
					template += '<td><input name="donatLength" value=1></td>';
				}
				template += '<td><a href="javascript:void(0)" onclick="delDataById(this,\''+row.bsRecId+'\')">[删除]</a></td>';
				template += '</tr>';
			}
			$(tBody).append(template);
		}
	});
}

function delDataById(obj,id){
	$.messager.progress({
		text:'处理中...'
	});
	$.ajax({
		url:'api/enterprisePay_deleteBSROnShopBus.action',
		type:'POST',
		data:{
			enterId:enterPay.enterId,
			id:id
		},
		success:function(data){
			$.messager.progress('close');
			data = $.parseJSON(data);
			if(data.success){
				getShopBusData();
			}
		}
	});
}
/*=====================福位操作结束=========================*/


/*=====================牌位操作开始=========================*/
/**
 * 初始化牌位列表
 */
function initTabletGrid(){
	$('#tabletGrid').datagrid({
		url:'api/memberPay_tabletGrid.action',
		columns:[[{
			field:'tabletId',
			title:'ID',
			width:10,
			checkbox:true
		},{
			field:'tabletName',
			title:'牌位名称',
			align:'center',
			width:20
		},{
			field:'tabletPrice',
			title:'牌位价格',
			width:15,
			align:'center',
			sortable:true
		}, {
			field : 'editable',
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
		},{
			field:'tabletRemark',
			title:'备注',
			align:'center',
			width:30
		}]],
		toolbar:'#tlGridTB',
		fit:true,
		border:false,
		fitColumns : true,
		pageSize:20,
		rownumbers : true,
		striped : true,
		pagination : true
	});
}

/**
 * 显示牌位列表窗口
 */
function showTLWindow(){
	$('#tabletWindow').dialog({
		width:900,
		height:400,
		modal:true
	});
	var tlIdsInput = $('#payForm input[name=tabletIds]');
	var queryParams = {};
	if(tlIdsInput.length > 0){
		var withoutIds = '';
		for(var i = 0; i < tlIdsInput.length; i ++){
			withoutIds += $(tlIdsInput[i]).val() + ',';
		}
		queryParams.withoutIds = withoutIds.substring(0, withoutIds.length - 1);
	}
	$('#tabletGrid').datagrid({
		queryParams:queryParams
	});
	$('#TLGridSearchBox').searchbox('clear');
	$('#tabletGrid').datagrid('load');
}

/**
 * 牌位搜索
 */
function doTLGridSearch(){
	var searchKey = $('#TLGridSearchBox').searchbox('getValue');
	var queryParams  = $('#tabletGrid').datagrid('options').queryParams;
	if(searchKey){
		queryParams.searchKey = searchKey;
	}
	else{
		queryParams.searchKey = '';
	}
	$('#tabletGrid').datagrid({
		queryParams:queryParams
	});
	$('#tabletGrid').datagrid('load');
}

/**
 * 选择牌位
 */
function checkTablet(){
	var rows = $('#tabletGrid').datagrid('getChecked');
	if(rows.length < 1){
		$.messager.alert('','请选择需要捐赠的牌位');
		return;
	}
	$('#tabletWindow').dialog('close');
	
	var tBody = $("#tabletBuyList>tbody");
	var template = '';
	for(var i = 0; i < rows.length; i ++){
		var row = rows[i];
		template += '<tr>';
		template += '<td><input type="hidden" value="'+row.tabletId+'" name="tabletIds">' + row.tabletName + '</td>';
		template += '<td><input type="hidden" value="'+row.tabletPrice+'" name="tabletPrices">' + row.tabletPrice + '</td>';
		if(row.editable){
			template += '<td><input name="tabletBuyLongTime" value=1 onchange="subtotal(this,'+row.tabletPrice+',\'tabletTotalPrice\');"></td>';
		}
		else{
			template += '<td><input type="hidden" name="tabletBuyLongTime" value=1>1</td>';
		}
		template += '<td><input disabled=false name="tabletTotalPrice" value="'+row.tabletPrice+'"></td>';
		template += '<td><a href="javascript:void(0)" onclick="deletTR(this)">[删除]</a></td>';
		template += '</tr>';
	}
	$(tBody).append(template);
}
/*=====================牌位操作结束=========================*/


/*=====================其它收费项目操作开始=========================*/
/**
 * 初始化其它收费项目列表
 */
function initExpensItemGrid(){
	$('#expensItemGrid').datagrid({
		url : 'api/memberPay_expensItemGrid.action',
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
			width : 15,
			align : 'center',
			sortable:true,
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
			field : 'itemRemark',
			title : '备注',
			width : 30,
			align : 'center'
		}]],
		toolbar:'#itemGridTB',
		fit : true,
		fitColumns : true,
		rownumbers : true,
		striped : true,
		pageSize:20,
		pagination : true
	});
}

/**
 * 显示其他收费项目列表窗口
 * @param costType	0:普通费用，1:会员费
 */
function showItemWindow(costType){
	$('#expensItemWindow').dialog({
		width:900,
		height:400,
		modal:true
	});
	
	if(!costType){
		costType = 0;
	}
	$('#expensItemGrid').datagrid('options').queryParams.costType = costType;
	
	$('#ItemSearchBox').searchbox('clear');
	$('#expensItemGrid').datagrid('load');
}

/**
 * 搜索其它收费项目
 */
function doItemGridSearch(){
	var searchKey = $('#ItemSearchBox').searchbox('getValue');
	//var params = $('#expensItemGrid').datagrid('options').queryParams;
	if(!searchKey){
		searchKey = '';
	}
	$('#expensItemGrid').datagrid('options').queryParams.searchKey = searchKey;
	$('#expensItemGrid').datagrid('load');
}

/**
 * 选择其它收费项目
 */
function checkExpensItem(){
	var rows = $('#expensItemGrid').datagrid('getChecked');
	if(rows.length < 1){
		$.messager.alert('','请选择数据');
		return;
	}
	$('#expensItemWindow').dialog('close');
	var tBody = $("#itemBuyList>tbody");
	var template = '';
	for(var i = 0; i < rows.length; i ++){
		var row = rows[i];
		template += '<tr>';
		template += '<input type="hidden" name="fgIds" value="0">';
		template += '<td><input type="hidden" value="'+row.itemId+'" name="itemIds">' + row.itemName;
		template += '<input type="hidden" name="itemNames" value="'+row.itemName+'"><input name="costTypes" type="hidden" value="'+row.costType+'"></td>';
		template += '<td><input type="hidden" value="'+row.itemPrice+'" name="itemPrices">' + row.itemPrice + '</td>';
		if(row.editAble){
			template += '<td><input name="itemBuyLongTime" value=1 onchange="subtotal(this,'+row.itemPrice+',\'itemTotalPrice\');"></td>';
		}
		else{
			template += '<td><input type="hidden" name="itemBuyLongTime" value=1>/</td>';
		}
		template += '<td><input disabled=false name="itemTotalPrice" value="'+row.itemPrice+'"></td>';
		template += '<td><a href="javascript:void(0)" onclick="deletTR(this)">[删除]</a></td>';
		template += '</tr>';
	}
	$(tBody).append(template);
}

/*=====================其它收费项目操作结束=========================*/

/*=====================福位管理费操作开始=========================*/
/**
 * 初始化会员已捐赠的有效福位
 */
function initBuyedBSGrid(){
	
	$('#owerBSGrid').datagrid({
		url : 'api/enterprisePay_buyedBSGrid.action',
		columns:[[{
			field:'bsId',
			width:10,
			checkbox:true
		},{
			field : 'bsCode',
			title : '编号',
			align: 'center',
			width: 20
		}, {
			field : 'lev',
			title : '价格',
			width : 15,
			align : 'center',
			formatter:function(value){
				if(value){
					return value.levPrice;
				}
			}
			
		}, {
			field : 'managExpense',
			title : '管理费',
			width : 15,
			align : 'center',
			sortable:true
		}, {
			field : 'remark',
			title : '备注',
			width : 30,
			align : 'center'
		}]],
		toolbar:'#owerBSGridTB',
		fit : true,
		fitColumns : true,
		rownumbers : true,
		pageSize:20,
		queryParams:{
			enterId:enterPay.enterId
		},
		striped : true,
		pagination : true
	});
}

/**
 * 显示已捐赠福位列表窗口
 */
function showBuyedWindow(){
	$('#owerBSWindow').dialog({
		width:900,
		height:400,
		modal:true
	});
	$('#owerBSGridSearchBox').searchbox('clear');
	var param = {};
	param.enterId = enterPay.enterId;
	$('#owerBSGrid').datagrid({
		queryParams : param
	});
	$('#owerBSGrid').datagrid('load');
}

function doOwerBSGridSearch(){
	var search = $('#owerBSGridSearchBox').searchbox('getValue');
	if(search){
		var param = $('#owerBSGrid').datagrid('options').queryParams;
		param.searchKey = search;
		$('#owerBSGrid').datagrid('load',param);
	}
}

/**
 *添加福位管理费
 */
function addBSMngCost(){
	var rows = $('#owerBSGrid').datagrid('getChecked');
	if(rows.length < 1){
		$.messager.alert('','请选择数据');
		return;
	}
	
	$('#owerBSWindow').dialog('close');
	
	var tBody = $("#itemBuyList>tbody");
	var template = '';
	for(var i = 0; i < rows.length; i ++){
		var row = rows[i];
		template += '<tr>';
		template += '<input type="hidden" name="fgIds" value="'+row.bsId+'">';
		template += '<td><input type="hidden" value="0" name="itemIds">福位管理费/' + row.bsCode ;
		template += '<input type="hidden" name="itemNames" value="福位管理费('+row.bsCode+')"><input name="costTypes" type="hidden" value="2"></td>';
		template += '<td><input type="hidden" value="'+row.managExpense+'" name="itemPrices">' + row.managExpense + '</td>';
		template += '<td><input name="itemBuyLongTime" value=1 onchange="subtotal(this,'+row.managExpense+',\'itemTotalPrice\');"></td>';
		template += '<td><input disabled=false name="itemTotalPrice" value="'+row.managExpense+'"></td>';
		template += '<td><a href="javascript:void(0)" onclick="deletTR(this)">[删除]</a></td>';
		template += '</tr>';
	}
	$(tBody).append(template);
}

/*=====================福位管理费操作结束=========================*/