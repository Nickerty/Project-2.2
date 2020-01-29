function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function () {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        }
    }
    rawFile.send(null);
}

function locationFromStn(fileLocation, stn) {

    readTextFile(fileLocation, function (text) {
        var returnArray = new Array();
        var json = JSON.parse(text)
        stn.forEach(function (singlestn) {
            let longlat = new Array();
            json.forEach(function (allJson) {
                var columns = allJson["data"];
                columns.forEach(function (singleRow) {
                    if (singleRow["STN"] == singlestn) {
                        longlat["long"] = singleRow["longitude"];
                        longlat["lat"] = singleRow["latiude"];
                        returnArray.push(longlat);
                        longlat = [];
                    }
                });
            })
        });
    });
    return returnArray;
}