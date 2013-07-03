/**
 * 删除数据确认
 * @param url 删除操作的url
 */
function confirmDelete(url)
{
	var url2 = window.location.href;//当前url
	if(window.confirm('提示：确认要删除本条数据吗？'))
	{
		$.ajax({
		   type: "POST",
		   url: url,
		   success: function(msg){
		     if(msg=="0"){
		    	 window.location.href = url2;
		     }else {
		    	 alert("删除失败!");
		     }
		   }
		});
	}
}

/**
 * 
 * @param url
 */
function update(url){
	var url2 = window.location.href;
	window.location.href = url+"&url="+url2.substring(url2.indexOf('/meeting')+8).replace(/\&/g,"%26");
}

/**
 * 清空表单内容
 * @param obj 要清空的表单对象
 */
function clearForm(obj){
	$(obj).find(':input').not(':button, :submit, :reset, :hidden').val('')
	.removeAttr('checked').removeAttr('selected');
}

/**
 * 去除前后空格
 * @returns
 */
String.prototype.trim = function() {   
    //return this.replace(/[(^\s+)(\s+$)]/g,"");//會把字符串中間的空白符也去掉   
    //return this.replace(/^\s+|\s+$/g,""); //   
    return this.replace(/^\s+/g,"").replace(/\s+$/g,"");   
};  