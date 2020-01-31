<?php
$dataCaribbean = file_get_contents("./json/Caribbean.json");
$decodedCaribbean = json_decode($dataCaribbean, true);


function getRainfall($stn, $decodedTotal)
{
    $actualFile = $decodedTotal[$stn]["weatherMeasurements"];
    $aantal = 0;
    $gemiddelde = 0;
    foreach ($actualFile as $rf) {
        $gemiddelde = $rf["rf"];
        $aantal++;
    }
    $gemiddelde = $gemiddelde / $aantal;

    return $gemiddelde * 100;

}

?>