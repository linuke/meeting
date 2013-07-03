//全局变量
var arrays = new Array()
arrays['string_compare_vals'] = new Array(' like ',' = ')
arrays['string_compare_texts'] = new Array('包含','等于')
arrays['number_compare_vals'] = new Array(' = ',' > ',' < ',' >= ',' <= ')
arrays['number_compare_texts'] = new Array('等于','大于','小于','大于等于','小于等于')
arrays['integer_compare_vals'] = new Array(' = ',' > ',' < ',' >= ',' <= ')
arrays['integer_compare_texts'] = new Array('等于','大于','小于','大于等于','小于等于')
arrays['date_compare_vals'] = new Array(' = ',' > ',' < ',' >= ',' <= ')
arrays['date_compare_texts'] = new Array('等于','大于','小于','大于等于','小于等于')
//浏览器相关
var isNav4,isIE4
var range = ""
var styleObj = ""
if(navigator.appVersion.charAt(0) == "4"){
	if(navigator.appName == "Netscape"){
		isNav4 = true
		insideWindowWidth = window.innerWidth
	}else{
		isIE4 = true
		range = "all."
		styleObj = ".style"
	}
}
//根据字符串或引用得到对象
function getObject(obj){
  var theObj
  if(typeof obj == "string") {
    theObj = eval("document." + range + obj)
  }else{
    theObj = obj
  }
  return theObj

}
//根据一个Select的值去改变另一个Select的Options值
//(需要在全局中设定Arrays["???_val"]和Arrays["???_text"])
function changeWith(select0,select1){
	var tmp = select0.value
	steOpt(select1,Arrays[tmp + "_val"],Arrays[tmp + "_text"])
}

//Select控件的数组操作
function setOptValues(select,array){
	for (var i=0; i < array.length; i++ ){	
		if(!select.options[i])
			select.options[i] = new Option()
		select.options[i].value = array[i]
  }
  //清空多余的options
	for (var i = select.options.length-1; i >= array.length; i--){
		select.options[i] = null;
  }	
}
//=======================================
function setOptTexts(select,array){
	for (var i=0; i < array.length; i++ ){
		if(!select.options[i])
			select.options[i] = new Option()
		select.options[i].text = array[i]
  }
  //清空多余的options
	for (var i = select.options.length-1; i >= array.length; i--){
			select.options[i] = null;
  }	
}
//=======================================
function addOpt(select,val,text){
	for (var i=0; i < select.options.length; i++ ){
		if(select.options[i].value == val)
			return false;
  	}
  	select.options[i] = new Option()
  	select.options[i].value = val
	select.options[i].text = text
	return true;
}
function removeOpt(select,str){
	for (var i=0; i < select.options.length; i++ ){
		if(select.options[i].value == str){
			for (var j=i; j < select.options.length - 1; j++ ){
				select.options[j].value = select.options[j+1].value
				select.options[j].text = select.options[j+1].text
			}
			select.options[j] = null;
			break;
		}
  	}
}
//=======================================
function setOpt(select,valArr,textArr){
	setOptValues(select,valArr)
	setOptTexts(select,textArr)
	if(select.options.length > 0)
		select.options[0].selected = true;
}
//选择多项Check
function selectAllitems(form, head, selectedStatus) {
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i]
    if (element.name.indexOf(head.name + '_') >= 0) {
      element.checked = selectedStatus
    }
  }
}
//把多选项记录下来
function mem(form, head){
	head.value = '';
	for (var i = 0; i < form.elements.length; i++) {
		var element = form.elements[i]
		if(element.name.indexOf(head.name + '_') >= 0 && element.checked){
			head.value = head.value + element.value + ',';
	  	}
   	}//for		
}
//全form数据检验
function checkAll(form){
	for (var i = 0; i < form.elements.length; i++) {
    		var element = form.elements[i]
	    	if(element.check){
	    		if(!eval(element.check))
	    			return false;
	    	}
   	}//for
}

//页面相关数据联动
function changeAll(form){
	for (var i = 0; i < form.elements.length; i++) {
	    	var element = form.elements[i]
	    	if(element.change){
	    		eval(element.change)
	    	}
   	}//for
	
}

function sqlStr(value){
	var intStart = 0
	var strSQLValue = ""
	for (i = 0; i < value.length; i++) {
		if (value.charAt(i) == "'") {
			strSQLValue = strSQLValue + value.substring(intStart, i) + "''"
			intStart = i + 1
			continue
		}
	}
	strSQLValue = strSQLValue + value.substr(intStart)
	return strSQLValue;
}
//网页内容
function show(obj){
	var theObj = getObject(obj)
	theObj.visibility = "visible"
}

function hide(obj){
	var theObj = getObject(obj)
	theObj.visibility = "hidden"
}

function fileInput(box_name, old_id, filetable){
	var f_url = '../include/fileInput.jsp?box_name=' + box_name + '&old_id=' + old_id + '&filetable=' + filetable;
	window.open(f_url,'newwin','toolbar=no,status=no,scrollbars=yes,resizable=yes,top=200,left=220 width=400,height=220')
}
/*
 *	校验日期格式是否合法(正确格式为：YYYY-MM-DD)
 *  合法：返回1，不合法：返回0
 */			
function checkDate(dStr){	
	var pos1 = dStr.indexOf("-");
	if (pos1<0){
		return 0;
	}else{
		var year = dStr.substring(0,pos1);
		if (year>1900 && year<3000){				
			var pos2 = dStr.indexOf("-",pos1+1);
			if (pos2>=0){
				month = dStr.substring(pos1+1,pos2);
				day = dStr.substring(pos2+1,dStr.length);					
				if (month>=1&&month<=12){
					if (month==4 || month==6 || month==9 || month==11){
						if (day>=1 && day<=30){
							return 1;
						}else{
							return 0;
						}
					}else if (month==2){
						if (year%400==0){
							if (day>=1 && day<=29){
								return 1;
							}else{
								return 0;						
							}
						}else{
							if (day>=1 && day<=28){
								return 1;
							}else{
								return 0;					
							}
						}
					}else {
						if (day>=1 && day<=31){
							return 1;
						}else{
							return 0;						
						}
					}//endif5	
					
				}else{
					return 0;
				}//end if4					
			}else{
				return 0;
			}//endif3
		}else{
			return 0;
		}//endif2	
	}//endif1				
}
