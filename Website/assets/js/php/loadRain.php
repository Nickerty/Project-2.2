<?php
include 'top10CaribbeanSea.php';
$array_data = []; // ------------------------------------------------------------------------------------------ creates an empty array
foreach ($decodedCaribbean as $single) {  // ------------------------------------------------------------------ loop trough the decoded json file
    $stn = $single["stn"]; // --------------------------------------------------------------------------------- creates the stn variable and save the stn's from the json
    $rain = getRainfall($single["stn"], $decodedTotal); // ---------------------------------------------------- creates the rain variable  and calls the get rainfall function
    if (is_nan($rain)) {
        continue; // ------------------------------------------------------------------------------------------ checks if  the rain variable is not a number and continues the script
    }
    if (!in_array(["stn" => $stn, "rain" => $rain], $array_data)) {
        array_push($array_data, ["stn" => $stn, "rain" => $rain]); // --------------------------------- if the data is not in the already present in the array add the new data
    }
}
function compare_rain($a, $b)
{
    return strnatcmp($b['rain'], $a['rain']); // ------------------------------------------------------------- return a natural order of the rain value
}

usort($array_data, 'compare_rain'); // -------------------------------------------------- sort the array based on compare_rain function
$array_data = array_slice($array_data, 0, 10); // ---------------------------------------------- slices the array in sets of 10 elements
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
