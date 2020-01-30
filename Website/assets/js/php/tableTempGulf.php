<?php
$dataGulf = file_get_contents("./json/gulfMexico.json");
$decodedGulf = json_decode($dataGulf, true);
$dataTotal = file_get_contents("./json/ding1.json");
$decodedTotal = json_decode($dataTotal, true);

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