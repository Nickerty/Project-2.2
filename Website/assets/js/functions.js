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

function locationFromStn(stn) {
    longlat = []
    readTextFile("json/tbl_name.json", function (text) {
        var json = JSON.parse(text);
        json.forEach(function (allJson) {
            var columns = allJson["data"];
            columns.forEach(function (singleRow) {
                if (singleRow["STN"] == stn) {
                    long = singleRow["longitude"];
                    longlat.push(long)
                }
            });
        });
    });
    return longlat
}

function temperatureFromStn(stn) {
    readTextFile("json/ding1.json", function (text) {
        var data = JSON.parse(text);
        var specificData = data[stn]["weatherMeasurements"];
        specificData.forEach(function (measurement) {
            console.log(measurement["temp"]);
            console.log(stn);
        })
    });
}