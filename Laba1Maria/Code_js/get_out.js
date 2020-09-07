let data_cookies="";
let flagErrorX = false;
let saveY
function getData(){
    const xhr = new XMLHttpRequest();

    saveY = value_Y

    analysis_param(delete_Virgule(value_Y),"Y");
    analysis_param(delete_Virgule(value_X),"X");

    if(!flagErrorX) {
        let url = new URL('http://localhost:8000/treatment.php');
        url.searchParams.set('x', delete_Virgule(value_X));
        url.searchParams.set('y', delete_Virgule(saveY));
        url.searchParams.set('r', delete_Virgule(R));
        url.searchParams.set('YY', delete_Virgule(value_Y));

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (Cookies.get('data_maria') !== undefined) {
                    data_cookies = Cookies.get('data_maria');
                }
                data_cookies += chek_good_php(xhr.responseText);
                Cookies.set("data_maria", data_cookies);
                addToTable();
            }
        }
        xhr.open('GET', url);
        xhr.send();
    }
}

function chek_good_php(param) {
    if(param.split("/")[0].split(";")[0] === '1'){
        alert("Ошибка в отправленных данных");
    }else{
        drawPoint(value_X * 10, saveY * 10, R * 10);
        return param;
    }
}


function delete_Virgule(value){
    if(/,/i.test(value)){
        return(value.replace(/[,]/,"."));
    }else{
        return (value);
    }
}

function analysis_param(analysis, name){
    if(name === "Y") {
        if(!/^-?\d+(\.|,)?[1-9]*$/i.test(analysis)){
            let sp = analysis.split("0")
            if(Number(sp[0]) == -R && sp[sp.length - 1] > 0){
                value_Y = Number(value_Y) + 1
            }else if(Number(sp[0]) == R && sp[sp.length - 1] > 0){
                value_Y = Number(value_Y) + 1
            }
        }
    }else{
        if(analysis === "-5" || analysis === "-4" || analysis === "-3" || analysis === "-2" || analysis === "-1" || analysis === "0"
            || analysis === "1" || analysis === "2" || analysis === "3"){
            flagErrorX = false;
        }else{
            flagErrorX = true;
            if(analysis === 0){
                flagErrorX = false;
            }else
                alert("Не трогайте, пожалуйста, 'X' он хороший =)")
        }
    }
}