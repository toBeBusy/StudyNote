function auto(){
	//随机分钟数
	var minus = Math.floor(Math.random() * ( 10 + 1)) + 40;
	//当前系统时间
	var myDate = new Date();
	//上班
	var goTOWork = myDate.getDay() != 0 && myDate.getHours() == 7 && (myDate.getMinutes() > minus);
	//下班
	var goBackHome = myDate.getDay() != 0 && myDate.getHours() == 19 && (myDate.getMinutes() > minus);
	//工号
	var empNum = '313353';
	//密码
	var empPassword = 'qiupeng1990';

	if(goTOWork) {
		document.getElementById("code").value = empNum;
    	document.getElementById("pwd").value = empPassword;  
    	document.getElementById("good_value").checked = true;
    	document.getElementById("bb").onclick();
    	document.getElementById("operBtn_on").onclick();

    	check(0);
    }else if(goBackHome) {
		document.getElementById("code").value = empNum;
    	document.getElementById("pwd").value = empPassword;  
    	document.getElementById("good_value").checked = true;
    	document.getElementById("bb").onclick();
    	document.getElementById("operBtn_div").getElementsByTagName("a")[1].onclick();

    	check(1);
    }
}

//不到10分钟执行一次
setInterval("auto()",60*58*10);