<%@ page import="app.model.Model" %><%--
  Created by IntelliJ IDEA.
  User: iurii
  Date: 29.09.2020
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>main</title>
  <style>
    <%@include file='/resources/main.css' %>
  </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div class="head" >
        <span id="head-title">
         <h1> Максимова Мария Р3210 Вариант 10025</h1>
        </span>
</div>

<div class="main">
<form method="POST">
    <div class = "ErrorCanvas">
    <a type="text" id="ErrorCanvas"></a>
</div>
    <canvas id="canvas" width="500" height="500"></canvas>
    <h2>Выберите координату X</h2>
    <input type="radio" id="r1" name="X" value="-5">-5

    <input type="radio" id="r2" name="X" value="-4">-4

    <input type="radio" id="r3" name="X" value="-3">-3

    <input type="radio" id="r4" name="X" value="-2">-2

    <input type="radio" id="r5" name="X" value="-1">-1

    <input type="radio" id="r6" name="X" value="0" checked>0

    <input type="radio" id="r7" name="X" value="1">1

    <input type="radio" id="r8" name="X" value="2">2

    <input type="radio" id="r9" name="X" value="3">3
    <h2 id="label_y"> Введите координату Y</h2>
    <input  type="text" name="Y" id="y_param" placeholder="Y(-5 .. 3)">
    <h2>Выберите радиус</h2>
<button  type="button" name="R" class="r-button" value="1" onclick="setR(1)">1</button>
 <button type="button" name="R" class="r-button" value="2" onclick="setR(2)">2</button>
 <button type="button" name="R" class="r-button" value="3" onclick="setR(3)">3</button>
 <button type="button" name="R" class="r-button" value="4" onclick="setR(4)">4</button>
 <button type="button" name="R" class="r-button" value="5" onclick="setR(5)">5</button>
  <input type="hidden" id ="inputR" name="InputR" value="1">
<p><button class="submit-button" type="submit" id="submit">Отправить</button></p>
</form>

<table class="table">
<thead>
<td>X</td>
<td>Y</td>
<td>R</td>
<td>RESULT</td>
</thead>
<tbody id="table_out">
<ul>
<%
 if (!Model.points.isEmpty()) {
   for (int i = Model.points.size()-1; i >= 0; i--) {
       out.println(Model.points.get(i).getRes());
   }
 }
%>
</ul>
</tbody>
</table>
</div>
<script src="script/draw.js"></script>
<script src="script/Valid.js"></script>
</body>
</html>
