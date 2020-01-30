<?php
$dataCaribbean = file_get_contents("/var/www/html/json/Caribbean.json");
$decodedCaribbean = json_decode($dataCaribbean, true);


function getRainfall($stn, $decodedTotal){
    $actualFile = $decodedTotal[$stn]["weatherMeasurements"];
    foreach ($actualFile as $rf){
        $rf["rf"];
    }

return $rf;

}
?>
