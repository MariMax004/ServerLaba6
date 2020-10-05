let value_y = 0;
let flag = false;
let flag_cvs = false;
let input = document.getElementById("y_param");



function set_y_value(){
    value_y = input.value;
    if(!flag_cvs) {
        if (value_y.length > 17) {
            input.value = ""
            document.getElementById('label_y').innerText = "Ошибка введённых данных";
            document.getElementById('label_y').style.color = "red";
            flag = true;
        } else {
            if (!/^-?\d+(\.|,)?\d*$/i.test(value_y)) {
                input.value = ""
                document.getElementById('label_y').innerText = "Ошибка введённых данных";
                document.getElementById('label_y').style.color = "red";
                flag = true;
            } else {
                if (value_y <= -5 || value_y >= 3) {
                    input.value = ""
                    document.getElementById('label_y').innerText = "Ошибка введённых данных";
                    document.getElementById('label_y').style.color = "red";
                    flag = true
                } else {
                    flag = false;
                }
            }
        }
    }
}

function setEventListener(){
    $("#submit").on('click', function(event) {
        set_y_value();
        if(flag) {
            event.preventDefault()
            flag = false;
        }

    })
}

setEventListener()

function setR(value) {
    let vl = document.getElementById("inputR");
    vl.value=value;
    draw(1)
}