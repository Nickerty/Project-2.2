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
$dataGulf = file_get_contents("C:/xampp/htdocs/Project-2.2/Website/json/gulfMexico.json", true);
$decodedGulf = json_decode($dataGulf, true);
$files = scan_dir('C:/xampp/htdocs/Project-2.2/Website/json/', SCANDIR_SORT_DESCENDING);
$dataTotal = file_get_contents("C:/xampp/htdocs/Project-2.2/Website/json/".$files[0]);
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
