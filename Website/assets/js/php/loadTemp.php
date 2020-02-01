<?php
include 'tableTempGulf.php';
include 'top10CaribbeanSea.php';
$array_data = [];
foreach ($decodedCaribbean as $single) {
    $stn = $single["stn"];
    $temp = getTemperature($single["stn"], $decodedTotal);
    if (is_nan($temp)) {
        continue;
    }
    if (!in_array(["stn" => $stn, "temp" => $temp], $array_data)) {
        array_push($array_data, ["stn" => $stn, "temp" => $temp]);
    }
}
function compare_temp($a, $b)
{
    return strnatcmp($b['temp'], $a['temp']);
}

usort($array_data, 'compare_temp');
$array_data = array_slice($array_data, 0, 10);
foreach ($array_data as $data) {
    echo "
                                                    <tr>
                                                        <th>" . $data['stn'] . "</th>
                                                        <td>" . $data['temp'] . "</td>
                                                    </tr>
                                                     ";
}
?>