<?php

$json =file_get_contents("./js/php/testfile.json");



include("XML/Serializer.php");

function json_to_xml($json) {
    $serializer = new XML_Serializer();
    $obj = json_decode($json);

    if ($serializer->serialize($obj)) {
        return $serializer->getSerializedData();
    }
    else {
        return null;
    }
}

echo (json_to_xml());

?>

