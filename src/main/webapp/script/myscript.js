function isValid(form){ 
  if(form.userid.value ==""){
    alert("用户编号不能为空！");
    return false;
  }
  else if(form.userid.value.length != 8){
    alert("用户编号为8位！");
    return false;
  }
  else if(form.passwd.value.length == 0){
    alert("请输入密码！");
    return false;
  }
  else {
	  form.submit();
    return true;
  }	
  
}
