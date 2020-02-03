<?php
include 'top10CaribbeanSea.php';
$array_data = [];
foreach ($decodedCaribbean as $single) {
    $stn = $single["stn"];
    $rain = getRainfall($single["stn"], $decodedTotal);
    if (is_nan($rain)) {
        continue;
    }
    if (!in_array(["stn" => $stn, "rain" => $rain], $array_data)) {
        array_push($array_data, ["stn" => $stn, "rain" => $rain]);
    }
}
function compare_rain($a, $b)
{
    return strnatcmp($b['rain'], $a['rain']);
}

usort($array_data, 'compare_rain');
$array_data = array_slice($array_data, 0, 10);
if (sizeof($array_data) > 0) {
    foreach ($array_data as $data) {
        echo "                                                <tr>
                                                    <th>" . $data['stn'] . "</th>
                                                    <td>" . $data['rain'] . "</td>
                                                </tr>
                                                 ";
    }
} else {
    echo "                                                <tr>
                                                    <th>Data is currently loading, please wait</th>
                                                    <td>...</td>
                                                </tr>
                                                 ";
}
?>
