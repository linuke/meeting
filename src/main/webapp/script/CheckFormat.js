/********************************************************************/
/*检查输入字符串是否为空*/
function isEmpty(inputString) {
  /*字符串长度为0，则字符串为空*/
	if (inputString.length == 0) {
    return true
  }
  /*字符串中有一个不是空格，则字符串不为空*/
	for (i = 0; i < inputString.length; i++) {
    if (inputString.charAt(i) != ' ') {
      return false
    }
  }
  /*字符串中全是空格，则字符串是空*/
	return true
}

/*检查输入字符串是否非空*/
function isNotEmpty(inputString) {
  /*字符串长度为0，则字符串为空*/
  if (inputString.length == 0) {
      alert('提示：请输入必要字段！');
      return false
  }
  /*字符串中有一个不是空格，则字符串不为空*/
	for (i = 0; i < inputString.length; i++) {
    if (inputString.charAt(i) != ' ') {
      return true
    }
  }
  alert('提示：请输入必要字段!');
  return false
}

/*检查输入字符串是否仅是数字、字母、“-”、“_”和“.”，主要用于编码和命名*/
function isNamingChar(inputString) {
	/*循环判断字符串中每一个字符*/
  for (i = 0; i < inputString.length; i++) {
		//字符不是ASCII字符，非法返回
    if (inputString.charCodeAt(i) > 128) {
			return false
    }
		//字符是数字，判断下一个
    if (inputString.charAt(i) >= "0" && inputString.charAt(i) <= "9") {
			continue
    }
		//字符是大写字母，判断下一个
    if (inputString.charAt(i) >= "A" && inputString.charAt(i) <= "Z") {
			continue
    }
		//字符是小写字母，判断下一个
    if (inputString.charAt(i) >= "a" && inputString.charAt(i) <= "z") {
			continue
    }
		//字符是“-”，判断下一个
    if (inputString.charAt(i) == "-") {
			continue
    }
		//字符是“_”，判断下一个
    if (inputString.charAt(i) == "_") {
			continue
    }
		//字符是“.”，判断下一个
    if (inputString.charAt(i) == ".") {
			continue
    }
    //字符不是以上合法形式
      alert('提示：编码中有非法字符！');
    		return false
	}
  //检查过所有字符，全都合法
	return true
}

/*检查输入字符串是否是包含HTML特殊字符*/
function isIncludeHtmlChar(inputString) {
  /*字符串中仅有下列字符，则字符串为数字*/
	var allHTMLChar="&<>\""
  for (i = 0; i < inputString.length; i++) {
    if (allHTMLChar.indexOf(inputString.charAt(i)) >= 0) {
      return true
    }
  }
  return false
}

/*检查输入字符串是否是数字*/
function isNumber(inputString) {
  /*字符串中仅有下列字符，则字符串为数字*/
  var allNumber="0123456789."
  if(isEmpty(inputString))
  	return true;
  for (i = 0; i < inputString.length; i++) {
    var j = allNumber.indexOf(inputString.charAt(i)); 
    if ( j < 0) {
      alert('提示：数值类型检查出错！');
      return false
    }
  }
  return true
}

/*检查输入字符串是否非空数字*/
function isNumberNotEmpty(inputString) {
  /*字符串长度为0，则字符串为空*/
	if (inputString.length == 0) {
    return false
  }
  /*字符串中仅有下列字符，则字符串为数字*/
	var allNumber="0123456789.";
  for (i = 0; i < inputString.length; i++) {
    var j = allNumber.indexOf(inputString.charAt(i)); 
    if ( j < 0) {
      alert('提示：数值类型检查出错！');
      return false
    }
  }
  return true
}

/*检查输入字符串是否是整数*/
function isInteger(inputString) {
  /*字符串中仅有下列字符，则字符串为整数*/
	var allNumber="0123456789"
  if(isEmpty(inputString))
  	return true;
  for (i = 0; i < inputString.length; i++) {
    var j = allNumber.indexOf(inputString.charAt(i)); 
    if ( j < 0) {
      alert('提示：整数类型检查出错！');
      return false
    }
  }
  return true
}

/*检查输入字符串是否是非空整数*/
function isIntegerNotEmpty(inputString) {
  /*字符串长度为0，则字符串为空*/
	if (inputString.length == 0) {
    return false
  }
  /*字符串中仅有下列字符，则字符串为整数*/
	var allNumber="0123456789"
  for (i = 0; i < inputString.length; i++) {
    var j = allNumber.indexOf(inputString.charAt(i)); 
    if ( j < 0) {
        alert('提示：整数类型检查出错！');
      return false
    }
  }
  return true
}
/**********************************
** 
** 判断该字符串是否是合法的正浮点数字，是则返回true,不是返回false
**
************************************/
function isFloat(strNumber)
{
	var j;
	strTemp="0123456789. "; //合法字符集
	if(isEmpty(strNumber))
  		return true;
	if(strNumber.length==0){ //为空，出错
		alert('提示：浮点类型检查出错！');
		return false;
	}
	for(i=0;i<strNumber.length;i++) //检查该字符串是否有非法字符
	{
		j=0;
		j=strTemp.indexOf(strNumber.charAt(i));	
		if(j==-1){ 
			alert('提示：浮点类型检查出错！');
			return false;
		}
	}
	j=0;
	for(i=0;i<strNumber.length;i++) //检查是否有超过两个以上的'.'
	{
		if(strNumber.charAt(i)=='.')
			j++;
	}
	if(j>1) { 
			alert('提示：浮点类型检查出错！');
			return false;
		}
	else
		return true;
	
}
function isDouble(strNumber)
{
	return isFloat(strNumber);
}
/*检查输入字符串是否是正确的日期格式*/
function isDateValue(inputYear, inputMonth, inputDay) {
  /*有日期无月份或者有月份无年份，则日期格式错误*/
	if (inputYear.length == 0 &&
		  inputMonth.length == 0 &&
		  inputDay.length == 0) {
		return true
  } else if ((inputYear.length == 0 && inputMonth.length != 0) ||
		         (inputMonth.length == 0 && inputDay.length != 0)) {
    return false
  }
  /*年月日不是整数，则日期格式错误*/	
	if (!isInteger(inputYear)) {
		return false
	}
	if (!isInteger(inputMonth)) {
		return false
	}
	if (!isInteger(inputDay)) {
		return false
	}
  /*年份不是4位，则日期格式错误*/
	if (inputYear.length != 4) {
		return false
	}
  //无月份则不进行月份判断
	if (inputMonth.length == 0) {
		return true
  }
	/*月份不是在1~12之间，则日期格式错误*/
	if (inputMonth < 1 ||
		  inputMonth > 12) {
		return false
	}
	/*逐月判断日期数值，出现非法值则日期格式错误*/
  //无日期则不进行日期判断
	if (inputDay.length == 0) {
		return true
  }
	//日期不能小于1
	if (inputDay < 1) {
		return false
	}
	//日期最大值为31日的月份判断
	if (inputMonth == 1 ||
		  inputMonth == 3 ||
		  inputMonth == 5 ||
		  inputMonth == 7 ||
		  inputMonth == 8 ||
		  inputMonth == 10 ||
		  inputMonth == 12) {
		if (inputDay > 31) {
			return false
		}
	}
	//日期最大值为30日的月份判断
	if (inputMonth == 4 ||
		  inputMonth == 6 ||
		  inputMonth == 9 ||
		  inputMonth == 11) {
		if (inputDay > 30) {
			return false
		}
	}
	//润年的日期处理
	if (inputMonth == 2) {
		if (inputDay > 28) {
			if ((inputYear % 4) == 0 && inputDay == 29) {
				return true
			} else {
  			return false
			}
		}
	}
	return true
}

function isNumber_NoAlert(inputString) {
  /*字符串中仅有下列字符，则字符串为数字*/
  var allNumber="0123456789."
  if(isEmpty(inputString))
  	return true;
  for (i = 0; i < inputString.length; i++) {
    var j = allNumber.indexOf(inputString.charAt(i)); 
    if ( j < 0) {
      return false
    }
  }
  return true
}

//检查输入的日期的格式是否为指定的格式
function isDateFormat(src, format) {
  inputString = src;
  var adex = inputString.indexOf(" ");  //去掉了日期后时间??:??的判断
  if(adex > 1)
  	inputString = inputString.substring(0,adex-1);
  if(isEmpty(inputString))
  	return true;
  if (inputString == null || inputString == "") {
    alert("提示：日期格式错误！正确格式为：" + format)
    return false
  }
  if (format == "YYYYMM") {
    var inputYear  = inputString
    if (inputYear.length != 6 || !isNumber_NoAlert(inputYear)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
    return true
  }  
  if (inputString.indexOf("-") < 0) {
    alert("提示：日期格式错误！正确格式为：" + format)
    return false
  }
  if (format == "YYYY-MM") {
    var inputYear  = inputString.substring(0, inputString.indexOf("-"))
    if (inputYear.length != 4 || !isNumber_NoAlert(inputYear)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
    var inputMonth = inputString.substr(inputString.indexOf("-") + 1)
    if (inputMonth.length > 2 || !isNumber_NoAlert(inputMonth)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
  } else if (format == "YYYY-MM-DD") {
    var inputYear = inputString.substring(0, inputString.indexOf("-"))
    if (inputYear.length != 4 || !isNumber_NoAlert(inputYear)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
    var inputMonth = inputString.substring(inputString.indexOf("-") + 1, inputString.lastIndexOf("-"))
    if (inputMonth.length > 2 || !isNumber_NoAlert(inputMonth)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
    var inputDay = inputString.substr(inputString.lastIndexOf("-") + 1)
    if (inputDay.length > 2 || !isNumber_NoAlert(inputDay)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
  } else if (format == "YYYY-MM-DD:HH") {
    var inputYear = inputString.substring(0, inputString.indexOf("-"))
    if (inputYear.length != 4 || !isNumber_NoAlert(inputYear)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
    var inputMonth = inputString.substring(inputString.indexOf("-") + 1, inputString.lastIndexOf("-"))
    if (inputMonth.length > 2 || !isNumber_NoAlert(inputMonth)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
    var inputDay = inputString.substring(inputString.lastIndexOf("-") + 1, inputString.indexOf(":"))
    if (inputDay.length > 2 || !isNumber_NoAlert(inputDay)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
    var inputTime = inputString.substr(inputString.indexOf(":") + 1)
    if (inputTime.length > 2 || !isNumber_NoAlert(inputTime)) {
      alert("提示：日期格式错误！正确格式为：" + format)
      return false
    }
  } else {
    alert("提示：日期格式错误！正确格式为：" + format)
    return false
  }
  return true
}
/********************************************************************/
function isInteger(inputString)
{
	if(isEmpty(inputString))
  	return true;
	if (inputString == null || inputString == "") {
	    alert("提示：请输入必要的数据！")
	    return false;
	  }
	  var allNumber="0123456789";
	  for (i = 0; i < inputString.length; i++) {
	    var j = allNumber.indexOf(inputString.charAt(i)); 
	    if ( j== -1) {
	      alert("提示：整数检查错误！")
	      return false;
	    }
	  }
	  return true
}

function isBetween(inputNumber, MIN, MAX) {
  if (inputNumber == null || inputNumber == "") {
    alert("提示：请输入必要的数据！")
    return false
  }
  if (inputNumber < MIN || inputNumber > MAX) {
    alert("提示：请输入有效范围内数值！")
    return false
  }
  return true
}





//选择多项Check
function selectAll(form, selectedStatus) {
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i]
    if (element.name.indexOf("selected_") >= 0) {
      element.checked = selectedStatus
    }
  }
}

function isSelectedAtLeastOne(form) {
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i]
    if (element.name.indexOf("selected_") >= 0 &&
			  element.name.indexOf("selectedAll") < 0) {
      if (element.checked) {
        return true
      }
    }
  }
  alert("提示：输入错误，请至少做出一项选择！")
  return false
}

function isSelectedOnlyOne(form) {
  var selectedCount = 0
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i]
    if (element.name.indexOf("selected_") >= 0 &&
			  element.name.indexOf("selectedAll") < 0) {
      if (element.checked) {
        selectedCount = selectedCount + 1
      }
    }
  }
  if (selectedCount != 1) {
    alert("提示：输入错误，请做出唯一一项选择！")
    return false
  }
  return true
}