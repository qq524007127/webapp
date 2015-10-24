(function(win){
	
	var memberBuyedInfo = {};
	
	memberBuyedInfo.init = function(memberId){
		memberBuyedInfo.memberId = memberId;
		$('#mainTabs').tabs({
			fit:true,
			border:false
		});
		$('#mainTabs').tabs('add',{
			title:'捐赠福位',
			selected:true,
			fit:true,
			content:'<iframe width=100% height=100% frameborder=0 src="admin/bsRecord.action?memberId='+memberId+'">',
			border:false
		});
		$('#mainTabs').tabs('add',{
			title:'捐赠牌位',
			selected:false,
			fit:true,
			content:'<iframe width=100% height=100% frameborder=0 src="admin/tabletRecord.action?memberId='+memberId+'">',
			border:false
		});
	};
	
	win.memberBuyedInfo = memberBuyedInfo;
})(window);