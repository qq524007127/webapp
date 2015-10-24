(function(win){
	
	var enterBuyedInfo = {};
	
	enterBuyedInfo.init = function(enterId){
		enterBuyedInfo.enterId = enterId;
		$('#mainTabs').tabs({
			fit:true,
			border:false
		});
		$('#mainTabs').tabs('add',{
			title:'捐赠福位',
			selected:true,
			fit:true,
			content:'<iframe width=100% height=100% frameborder=0 src="admin/bsRecord.action?enterpriseId='+enterId+'">',
			border:false
		});
		$('#mainTabs').tabs('add',{
			title:'捐赠牌位',
			selected:false,
			fit:true,
			content:'<iframe width=100% height=100% frameborder=0 src="admin/tabletRecord.action?enterpriseId='+enterId+'">',
			border:false
		});
	};
	
	win.enterBuyedInfo = enterBuyedInfo;
})(window);