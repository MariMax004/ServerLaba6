canvas = document.getElementById("canvas");
ctx = canvas.getContext("2d");
let R = 3;
draw();
function set_R_value(id){
    R = document.getElementById("Select_r").options[document.getElementById("Select_r").
        options.selectedIndex].value;
    document.getElementById('label_r').innerText = "R = " + R;
    draw();
}

function drawPoint(x,y,r) {
    if(x > r || y > r || x < -1 * r|| y < -1 *r){
        alert('Выход за пределы видимости графика\nНе могу отобразить точку');
        draw();
    }else {
        draw();
        ctx.fillStyle = "red";
        ctx.beginPath();
        ctx.moveTo(150, 70);
        ctx.arc(150 + 50 * x / r, 70 - 50 * y / r, 3, 0, 2 * Math.PI);
        ctx.closePath();
        ctx.fill();
    }
}

function draw(){
    ctx.clearRect(0, 0, 1000, 1000);

    ctx.fillStyle = "rgba(91,95,201,0.58)";
    ctx.beginPath();
    ctx.moveTo(150, 70);
    //отображаем сектор круга
    ctx.arc(150, 70, 50, -4.7, 4* Math.PI / 2, true);
    //отображаем прмоугольник
    ctx.fillRect(150, 20, 50, 50);
    //отображаем треугольник
    ctx.moveTo(150,70);
    ctx.lineTo(125,70);
    ctx.lineTo(150,120);
    ctx.fill();

    ctx.fillStyle = "black";
    ctx.beginPath();
    //отображение линии x
    ctx.moveTo(150,70);
    ctx.lineTo(220,70);
    //отрисовка стрелки
    ctx.lineTo(215,75);
    ctx.moveTo(220,70);
    ctx.lineTo(215,65);
    //
    ctx.fillText('X',220,65);
    ctx.moveTo(150,70);
    ctx.lineTo(80,70);

    //подпись значений x
    ctx.moveTo(175,75);
    ctx.lineTo(175,65);
    ctx.fillText(R/2 ,170,63);
    ctx.moveTo(200,75);
    ctx.lineTo(200,65);
    ctx.fillText(R,197,63);

    ctx.moveTo(125,75);
    ctx.lineTo(125,65);
    ctx.fillText(-1*R/2 ,114,63);
    ctx.moveTo(100,75);
    ctx.lineTo(100,65);
    ctx.fillText(-1*R,96,63);

    //отображение линии y
    ctx.moveTo(150,70);
    ctx.lineTo(150,140);
    ctx.moveTo(150,70);
    ctx.lineTo(150,5);
    //отрисовка стрелки
    ctx.lineTo(155,10);
    ctx.moveTo(150,5);
    ctx.lineTo(145,10);
    //
    ctx.fillText('Y',155,15);

    //подпись значений x
    ctx.moveTo(145,95);
    ctx.lineTo(155,95);
    ctx.fillText(-1*R/2 ,160,100);
    ctx.moveTo(145,120);
    ctx.lineTo(155,120);
    ctx.fillText(-1*R ,160,123);

    ctx.moveTo(145,45);
    ctx.lineTo(155,45);
    ctx.fillText(R/2,160,48);
    ctx.moveTo(145,20);
    ctx.lineTo(155,20);
    ctx.fillText(R,160,25);

    ctx.closePath();
    ctx.stroke();
}