<?php
$dataCaribbean = file_get_contents("./json/Caribbean.json");
$decodedCaribbean = json_decode($dataCaribbean, true);
$dataTotal = file_get_contents("./json/ding1.json");
$decodeTotal = json_decode($dataTotal, true);


function getRainfall($stn, $decodedTotal){
    $actualFile = $decodedTotal[$stn]["rf"]



}