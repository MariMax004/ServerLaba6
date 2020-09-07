let input = document.getElementById("y_param");
let error = document.getElementById("Error");
let value_X = 0;
let value_Y = 0;
let flag = true;

function set_x_value(){
	value_X = document.getElementById("Select_x").options[document.getElementById("Select_x").
		options.selectedIndex].value;
	document.getElementById('label_x').innerText = "X = " + value_X;
}


function chek_input(){
	if(!/^-?\d+(\.|,)?\d*$/i.test(input.value)){
		error.textContent = "Ошибка: Значение \"Y\" не валидно";
		input.value = "";
		flag = false;
	}else{
		error.textContent = "";
		let y = input.value.replace(/[,]/,".");
		if(y<-3 || y>3){
			error.textContent = "Ошибка: Выход за пределы, введите число в интервале [-3;3]";
			input.value = "";
			flag = false;
		}else if(Number(y.split(".")[0]) >=3 && Number(y.split(".")[1])>0){
			error.textContent = "Ошибка: Выход за пределы, введите число в интервале [-3;3]";
			input.value = "";
			flag = false;
		}else if(Number(y.split(".")[0]) <=-3 && Number(y.split(".")[1])>0){
			error.textContent = "Ошибка: Выход за пределы, введите число в интервале [-3;3]";
			input.value = "";
			flag = false;
		}else {
			document.getElementById('label_y').innerText = "Y = " + input.value;
			value_Y = input.value.replace(/[,]/,".");
			flag = true;
		}
	}
}

let button = document.getElementById("button");

button.onclick = function() {
	chek_input();
	if(flag) {
		getData();
	}
}