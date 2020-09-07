<?php
    $start_time = microtime();
    $r = $_GET['r'];
    $x = $_GET['x'];
    $y = $_GET['y'];
    $yy = $_GET['YY'];
    $out = "";
    $flag = 0;
    if ($_SERVER['REQUEST_METHOD'] === 'GET') {
        if (!preg_match('/^-?\d+(\.|,)?\d*$/', $r) ||
            !preg_match('/^-?\d+(\.|,)?\d*$/', $x) ||
            !preg_match('/^-?\d+(\.|,)?\d*$/', $y))
            $flag = 1;
        if($x < -5 || $x > 3)
            $flag = 1;
        if($r< 1 || $r>3)
            $flag = 1;
        if($y<-3 || $y>3)
            $flag = 1;

        if((($x*$x + $y*$y) <= $r*$r && $x >=0 && $y <= 0)||
            ($y+2*$x>=-$r && $x<=0 && $y<=0)||
            ($x>=0 && $y>=0 && $x<=$r && $y<=$r)){
            if($y == $yy) {
                $out = "Входит";
            }else{
                $out = "Не входит";
            }
        }else{
            $out = "Не входит";
        }

        $answer = $flag;
        $answer .= ";";
        $answer .= $x;
        $answer .= ";";
        $answer .= $y;
        $answer .= ";";
        $answer .= $r;
        $answer .= ";";
        $answer .= $out;
        $answer .= ";";
        $answer .= date("Y-m-d H:i:s");
        $answer .= ";";
        $answer .= microtime()-$start_time;
        $answer .= "/";
        echo $answer;
    }
?>