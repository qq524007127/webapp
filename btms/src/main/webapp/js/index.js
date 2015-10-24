$(function(){
	setInterval(function(){
		$('#timeContainer').html(curDateTime());
	}, 1000);
});

function curDateTime(){  
    var d = new Date();   
    var year = d.getFullYear();   
    var month = d.getMonth()+1;   
    var date = d.getDate();   
    //var day = d.getDay();   
    var hours = d.getHours();   
    var minutes = d.getMinutes();   
    var seconds = d.getSeconds();   
   // var ms = d.getMilliseconds();     
    var curDateTime= year;  
    if(month>9)  
    curDateTime = curDateTime +"-"+month;  
    else  
    curDateTime = curDateTime +"-0"+month;  
    if(date>9)  
    curDateTime = curDateTime +"-"+date;  
    else  
    curDateTime = curDateTime +"-0"+date;  
    if(hours>9)  
    curDateTime = curDateTime +" "+hours;  
    else  
    curDateTime = curDateTime +" 0"+hours;  
    if(minutes>9)  
    curDateTime = curDateTime +":"+minutes;  
    else  
    curDateTime = curDateTime +":0"+minutes;  
    if(seconds>9)  
    curDateTime = curDateTime +":"+seconds;  
    else  
    curDateTime = curDateTime +":0"+seconds;  
    return curDateTime;   
}

function onMenuItemClick(title,pageUrl){
	if($('#mainTabs').tabs('exists',title)){
		$('#mainTabs').tabs('select',title);
		return;
	}
	$('#mainTabs').tabs('add',{
		title:title,
		content:'<iframe width=100% height=99% frameborder=0 src="'+pageUrl+'">',
		closable:true,
		style:{
			padding:5
		}
	});
};

function exitApp(){
	$.messager.confirm('警告', '确定要退出吗？', function(flag){
		if(flag){
			location.href = window.app.baseUrl + '/admin/userLoginOut.action';
		}
	});
}