canvas = document.getElementById("canvas");
ctx = canvas.getContext("2d");

let value_r = 5;


function windowToCanvas(canvas, x, y) {
    let bbox = canvas.getBoundingClientRect();
    return { x: x - bbox.left * (canvas.width / bbox.width),
        y: y - bbox.top * (canvas.height / bbox.height)
    };
}

canvas.onmousedown = function (e) {
    let loc = windowToCanvas(canvas, e.clientX, e.clientY);
    let rly_x = value_r*((loc.x - 250)/200)
    let rly_y = (-1) * value_r*((loc.y - 250)/200)

    if(rly_x > value_r || rly_y > value_r || rly_x < -1 * value_r || rly_y < -1 *value_r){
        document.getElementById('ErrorCanvas').innerText = "Не тыкай по краям, ЗАПРЕТ";
    }else {
        $("#y_param")[0].value = rly_y.toString()
        let rad=document.getElementsByName('X');
        for (let i=0;i<rad.length; i++) {
            if (rad[i].checked) {
                rad[i].value = rly_x.toString();
            }
        }
        $("#inputR")[0].value = value_r.toString()
        flag_cvs = true;
        $("#submit")[0].click()
    }
};

function set_r_value(flag_draw){
    if(flag_draw == 1){
        value_r = document.getElementById("inputR").value;
    }else {
        let n = document.getElementById("table_out").getElementsByTagName("tr").length

        if(n !== 0) {
            value_r = Number($("td", $("table tr").eq(1)).eq(2).text())
            let vl = document.getElementById("inputR");
            vl.value = value_r.toString();
        }else{
            value_r = document.getElementById("inputR").value;
        }
    }
}

function drawPoint(x,y,r, last_point) {
    let flagger = x > r || y > r || x < -1 * r || y < -1 *r;
    if(!flagger){
        ctx.beginPath();

        if(last_point === "last") {
            ctx.fillStyle = "rgba(0, 255, 0, 1)";
        }else{
            ctx.fillStyle = "rgba(255, 0, 0, 1)";
        }

        ctx.arc(250 + 200 * x * 10 / (r*10), 250 - 200 * y*10 / (r*10), 3, 0, 2 * Math.PI);
        ctx.closePath();
        ctx.fill();
    }
}

function draw(flag_draw){
    set_r_value(flag_draw);

    ctx.clearRect(0, 0, 1000, 1000);

    ctx.fillStyle = "#FF8C00";
    ctx.beginPath();
    ctx.moveTo(250, 250);
    //отображаем сектор круга
    ctx.arc(250, 250, 200,Math.PI,Math.PI/2, true);
    //отображаем прмоугольник
    ctx.fillRect(250, 250, -200, -200);
    //отображаем треугольник
    ctx.moveTo(250,250);
    ctx.lineTo(450,250);
    ctx.lineTo(250,50);
    ctx.fill();

    ctx.fillStyle = "rgba(0, 0, 0, 1)";
    ctx.beginPath();
    ctx.moveTo(30,250);
    ctx.lineTo(470,250);

    ctx.lineTo(465,255);
    ctx.moveTo(470,250);
    ctx.lineTo(465,245);
    ctx.font = "20px serif";
    ctx.fillText('X',470,245);

    ctx.moveTo(250,470);
    ctx.lineTo(250,30);

    ctx.lineTo(255,35);
    ctx.moveTo(250,30);
    ctx.lineTo(245,35);

    ctx.fillText('Y',255,35);

    ctx.moveTo(450,260);
    ctx.lineTo(450,240);
    ctx.fillText(value_r ,445,230);

    ctx.moveTo(350,260);
    ctx.lineTo(350,240);
    ctx.fillText(value_r/2 ,345,230);

    ctx.moveTo(50,260);
    ctx.lineTo(50,240);
    ctx.fillText(-value_r ,55,230);

    ctx.moveTo(150,260);
    ctx.lineTo(150,240);
    ctx.fillText(-value_r/2 ,145,230);

    ctx.moveTo(240,50);
    ctx.lineTo(260,50);
    ctx.fillText(value_r ,260,60);

    ctx.moveTo(240,150);
    ctx.lineTo(260,150);
    ctx.fillText(value_r/2 ,260,160);

    ctx.moveTo(240,450);
    ctx.lineTo(260,450);
    ctx.fillText(-value_r ,260,460);

    ctx.moveTo(240,350);
    ctx.lineTo(260,350);
    ctx.fillText(-value_r/2 ,260,360);

    ctx.closePath();
    ctx.stroke();

    chekTableAndDraw()
}

draw();

function chekTableAndDraw(){
    let n = document.getElementById("table_out").getElementsByTagName("tr").length

    if(n !== 0){
        for(let i = n; i > 0; i--){
            if(i === 1){
                drawPoint(Number($("td", $("table tr").eq(i)).eq(0).text()),
                    Number($("td", $("table tr").eq(i)).eq(1).text()),
                    value_r,"last");
            }else {
                drawPoint(Number($("td", $("table tr").eq(i)).eq(0).text()),
                    Number($("td", $("table tr").eq(i)).eq(1).text()),
                    value_r, "");
            }
        }
    }
}