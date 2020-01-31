function locationFromStn(stn) {
    let longlat = []
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