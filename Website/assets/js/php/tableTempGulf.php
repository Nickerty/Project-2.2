<?php
$dataGulf = file_get_contents("./json/gulfMexico.json");
$decodedGulf = json_decode($dataGulf, true);
$dataTotal = file_get_contents("./json/ding1.json");
$decodedTotal = json_decode($dataTotal, true);

function getTemperature($stn, $decodedTotal){
    $acutalTemp =  $decodedTotal[$stn]["weatherMeasurements"];
    foreach ($acutalTemp as $temp){
        $temp["temp"];
    }

return $acutalTemp;
}
?>