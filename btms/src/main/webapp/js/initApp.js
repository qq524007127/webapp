function submitInitShelf(){
	$.messager.progress({
		text:'处理中...'
	});
	$('#initShelfForm').form('submit',{
		success:function (data){
			data = $.parseJSON(data);
			$.messager.progress('close');
			$.messager.alert('',data.msg);
		}
	});
}

function initShelfByArea(){
	$.messager.progress({
		text:'处理中...'
	});
	$('#initShelfByAreaForm').form('submit',{
		success:function (data){
			data = $.parseJSON(data);
			$.messager.progress('close');
			$.messager.alert('',data.msg);
		}
	});
}
