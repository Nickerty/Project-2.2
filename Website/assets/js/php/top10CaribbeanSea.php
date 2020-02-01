<?php
function scan_dir($dir) {
    $ignored = array('.', '..', '.svn', '.htaccess'); // -- ignore these file names
    $files = array(); //----------------------------------- create an empty files array to play with
    foreach (scandir($dir) as $file) {
        if ($file[0] === '.') continue; //----------------- ignores all files starting with '.'
        if (in_array($file, $ignored)) continue; //-------- ignores all files given in $ignored
        $files[$file] = filemtime($dir . '/' . $file); //-- add to files list
    }
    arsort($files); //------------------------------------- sort file values (creation timestamps)
    $files = array_keys($files); //------------------------ get all files after sorting
    return ($files) ? $files : false;
}
$dataGulf = file_get_contents("gulfMexico.json", true);
$decodedGulf = json_decode($dataGulf, true);
$files = scan_dir('/var/www/html/json/');
$dataTotal = file_get_contents("/var/www/html/json/".$files[0]);
$decodedTotal = json_decode($dataTotal, true);
$dataCaribbean = file_get_contents("Caribbean.json");
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
