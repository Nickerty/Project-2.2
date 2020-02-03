<?php
include 'tableTempGulf.php';
include 'top10CaribbeanSea.php';
$array_data = [];  // ------------------------------------------------------------------------------------------ creates an empty array
foreach ($decodedCaribbean as $single) {   // ------------------------------------------------------------------ loop trough the decoded json file
    $stn = $single["stn"];  // --------------------------------------------------------------------------------- creates the stn variable and save the stn's from the json
    if (is_nan($temp)) {
    $temp = getTemperature($single["stn"], $decodedTotal);  // ---------------------------------------------------- creates the rain variable  and calls the get_Temperature function
    if (is_nan($temp)) {
        continue;  // ------------------------------------------------------------------------------------------ checks if  the temp variable is not a number and continues the script
    }
    if (!in_array(["stn" => $stn, "temp" => $temp], $array_data)) {
        array_push($array_data, ["stn" => $stn, "temp" => $temp]);  // --------------------------------- if the data is not in the already present in the array add the new data
    }
}
function compare_temp($a, $b)
{
    return strnatcmp($b['temp'], $a['temp']);  // ------------------------------------------------------------- return a natural order of the temp value
}
}

usort($array_data, 'compare_temp'); // -------------------------------------------------- sort the array based on compare_temp function
$array_data = array_slice($array_data, 0, 10);  // ---------------------------------------------- slices the array in sets of 10 elements


if (sizeof($array_data) > 0) {
    foreach ($array_data as $data) {
        echo "
                                                    <tr>
                                                        <th>" . $data['stn'] . "</th>
                                                        <td>" . $data['temp'] . "</td>
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