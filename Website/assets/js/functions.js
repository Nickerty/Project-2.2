function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function() {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        }
    }
    rawFile.send(null);
}

function locationFromStn(fileLocation, stn) {
    let longlat = new Array();
    readTextFile(fileLocation, function (text) {
        var json = JSON.parse(text)
        json.forEach(function (allJson) {
            var columns = allJson["data"];
            columns.forEach(function (singleRow) {
                if (singleRow["STN"] == stn) {
                    longlat["long"] = singleRow["longitude"];
                    longlat["lat"] = singleRow["latiude"];
                }
            });
        })
    });
    return longlat;
}