<?php
include 'tableTempGulf.php';
include 'top10CaribbeanSea.php';

include("XML/Serializer.php");

function json_to_xml($files) {
    $json = file_get_contents("C:/xampp/htdocs/Project-2.2/Website/json/".$files[0]);
    $serializer = new XML_Serializer();
    $obj = json_decode($json);
    if ($serializer->serialize($obj)) {
        return $serializer->getSerializedData();
    }
    else {
        return null;
    }
}

?>

