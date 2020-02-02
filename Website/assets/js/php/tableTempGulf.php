<?php

function getTemperature($stn, $decodedTotal){
    $aantal = 0;
    $totalTemp = 0;
    $acutalTemp = $decodedTotal[$stn]["weatherMeasurements"];


    foreach ($acutalTemp as $temp){
        $totalTemp += $temp["temp"];
        $aantal++;

    }
    $totalTemp = $totalTemp / $aantal;
    return $totalTemp;
}
?>
