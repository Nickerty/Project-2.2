<?php
ini_set('memory_limit', '2G');
include "checklogin.php";
include './assets/js/php/tableTempGulf.php';
include './assets/js/php/top10CaribbeanSea.php';
$data = file_get_contents("./json/gulfMexico.json");
$tbl_names = file_get_contents("./json/tbl_name.json");
$jsonTabelNames = json_decode($tbl_names, true);
$dataRow = $jsonTabelNames[0]["data"];
$weatherStations = json_decode($data, true);
$stn_from_weatherStations = [];
foreach ($weatherStations as $weatherStation) {
    array_push($stn_from_weatherStations, $weatherStation["stn"]);
}

$mapWidth = 1294;
$mapHeight = 700;

$mapLonLeft = -106.569866;
$mapLonRight = -73.232942;
$mapLonDelta = $mapLonRight - $mapLonLeft;

$mapLatBottom = 12.920548;
$mapLatBottomDegree = $mapLatBottom * M_PI / 180;

function convertGeoToPixel($lat, $lon)
{
    global $mapWidth, $mapHeight, $mapLonLeft, $mapLonDelta, $mapLatBottom, $mapLatBottomDegree;

    $x = ($lon - $mapLonLeft) * ($mapWidth / $mapLonDelta);

    $lat = $lat * M_PI / 180;
    $worldMapWidth = (($mapWidth / $mapLonDelta) * 360) / (2 * M_PI);
    $mapOffsetY = ($worldMapWidth / 2 * log((1 + sin($mapLatBottomDegree)) / (1 - sin($mapLatBottomDegree))));
    $y = $mapHeight - (($worldMapWidth / 2 * log((1 + sin($lat)) / (1 - sin($lat)))) - $mapOffsetY);
    return array($x, $y);
}

function getTemperature2($stn, $jsonFile) {
    $jsonFileNeeded = $jsonFile["weatherMeasurements"];
    $gemiddelde = 0;
    $aantal = 0;
    foreach ($jsonFileNeeded as $singleNodig) {
        $singleNodig['temp'];
        $gemiddelde += $singleNodig['temp'];
        $aantal++;
    }
    return round($gemiddelde / $aantal);
}

function getLocation($stn, $dataRow) {
    foreach ($dataRow as $singleRow) {
        if ($singleRow["STN"] == $stn) {
            return [$singleRow["latiude"], $singleRow["longitude"]];
        }
    }
}
function getLast12Readings($files, $locationFile, $stn_from_weatherStations) {
    $all_json = [];
    $json_counter = 0;
    $usedFiles = [];
    $max = 12;
    $i = 0;
    $counter = 0;
    $amountOfFiles = sizeof($files);
    while (($counter < $max) && ($i < $amountOfFiles)){
        if (strpos($files[$i], 'File') !== false) {
            $usedFiles[$counter] = $files[$i];
            $counter++;
        }
        $i++;
    }
    foreach ($usedFiles as $file) {
        $json_needed = [];
        $json_dingen = [];
        $file_content = file_get_contents("./json/".$file);
        $file_json_content = json_decode($file_content, true);
        foreach ($file_json_content as $stn => $data) {
            $list_with_used = [];
            $stn_string = "".$stn."";
            if(in_array($stn_string, $stn_from_weatherStations)) {
                array_push($list_with_used, $stn_string);
                $temp = getTemperature2($stn, $data);
                $location = getLocation($stn, $locationFile);
                $lat = (float)$location[0];
                $long = (float)$location[1];
                $location_xy = convertGeoToPixel($lat,$long);
                $x = (int) round($location_xy[0]);
                $y = (int) round($location_xy[1]);
                array_push($json_dingen, ["x" => $x, "y" => $y, "value" => $temp]);
            }

        }
        $all_json[$json_counter] = ["max"=>15, "min" =>5, "data"=>$json_dingen];
        $json_counter++;

    }
    return $all_json;
}
$animationData = getLast12Readings($files, $dataRow, $stn_from_weatherStations);
$animationData= json_encode($animationData);
?>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <title>Heatmap Temperature</title>
  <link rel="stylesheet" href="Heatmap_Animation/commons.css">
  <link rel="stylesheet" href="Heatmap_Animation/example-commons.css">
  <style>
    #share {
      display:none;
      float:right;
      font-size:14px;
      line-height:170%;
    }
    .symbol {display:inline !important; float:right;}
    .demo-wrapper { position:relative;}
    /* animation player css */
    .timeline-wrapper {
        position:absolute;
        top: 10px;
        left:10px;
        right:10px;
        height:30px;
        background:white;
        transition:1s ease all;
        border-radius:4px;
        box-shadow:0 1px 5px rgba(0,0,0,.65)
    }
    .heatmap-timeline {
        position:absolute;
        top:0;
        right:15px;
        left:80px;
        height:100%;
    }

    .heatmap-timeline .line {
        position:absolute;
        left:0;
        right:0;
        top:15px;
        height:2px;
        background:#d7d7d7;
    }
    .heatmap-timeline .time-point.active {
        background:black;
    }
    .heatmap-timeline .time-point {
        position:absolute;
        background:white;
        border:2px solid #272727;
        width:8px;
        height:8px;
        border-radius:100%;
        cursor:pointer;
        top:15px;
        transform:translateX(-50%) translateY(-50%);
    }
    .heatmap-timeline .time-point:hover {
        box-shadow:0 0 5px black;
    }
    .timeline-wrapper button {
      position:absolute;
      outline:none;
      color: black;
      background: #f2f2f2;
      width: 65px;
      height: 100%;
      cursor: pointer;
      border: none;
      text-transform:uppercase;
      border-top-left-radius:3px;
      border-bottom-left-radius:3px;
    }
    .heatmap-timeline .time-point.active {
      background:black;
    }
    /* end animation player css */
    .legend-area { position:absolute; bottom:0; right:0; padding:10px; background:white; outline:3px solid black; line-height:1em; }
    h4 { margin:0; padding:0; margin-bottom:5px;}
    #min { float:left; }
    #max { float:right; }
    span { font-size:14px; margin:0; padding:0; }
    .tooltip { position:absolute; left:0; top:0; background:rgba(0,0,0,.8); color:white; font-size:14px; padding:5px; line-height:18px; display:none;}
  </style>
</head>
<body>
  <div class="wrapper">

    <div class="demo-wrapper">

      <div class="heatmap" style="position: relative;background-repeat: no-repeat;"><canvas class="heatmap-canvas" width="834" height="400" style="position: absolute; left: 0px; top: 0px;"></canvas></div>
      <div class="timeline-wrapper"><button>play</button><div class="heatmap-timeline"><div class="line"></div><div class="time-point active" style="left: 0%;"></div><div class="time-point" style="left: 5.26316%;"></div><div class="time-point" style="left: 10.5263%;"></div><div class="time-point" style="left: 15.7895%;"></div><div class="time-point" style="left: 21.0526%;"></div><div class="time-point" style="left: 26.3158%;"></div><div class="time-point" style="left: 31.5789%;"></div><div class="time-point" style="left: 36.8421%;"></div><div class="time-point" style="left: 42.1053%;"></div><div class="time-point" style="left: 47.3684%;"></div><div class="time-point" style="left: 52.6316%;"></div><div class="time-point" style="left: 57.8947%;"></div><div class="time-point" style="left: 63.1579%;"></div><div class="time-point" style="left: 68.4211%;"></div><div class="time-point" style="left: 73.6842%;"></div><div class="time-point" style="left: 78.9474%;"></div><div class="time-point" style="left: 84.2105%;"></div><div class="time-point" style="left: 89.4737%;"></div><div class="time-point" style="left: 94.7368%;"></div><div class="time-point" style="left: 100%;"></div></div></div>
    </div>
    <div class="tooltip" style="display: none; transform: translate(145px, 397px);">1176</div>
    <div class="legend-area">
      <h4>Legend Title</h4>
      <span id="min">4</span>
      <span id="max">1229</span>
      <img id="gradient" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAAAKCAYAAABCHPt+AAABBklEQVRYR+2X2wqDMBBER4x4+f/vtH1qCgqWkWwbgjEKzZJShbAMiT7kODtJBSwLcj9mBvon0FnVasyEHkAHqFZjAdwBjJF6A8Ah856udIBMqiAEvDGzKggBvwKRTRYwB/UFJIODLiCRVliEQ8QZB6uOQ5oJ6FyGDK4qaAIZnAPYTjhCzYyRnNma33q/9d7hfKhrv2UdBCEtTgeI+bMMeSRCPRb2I6ADhA7JccpqLSCO4/cD3dTxUJcA9t0RuiWlfUdxLZ1Cx9TFA/Edwg3kxu0B6u2nxXFdSpeWIQQSa1WJU9dvOyRxr9lzSM77yeqQ1DE3ck9RBOL++rczQv39i2MRQE6G+gsn/eBRZ5gBTQAAAABJRU5ErkJggg==" style="width:100%">
    </div>
    <div class="demo-controls">
      <button class="trigger-refresh btn" data-fps="10">Set speed to 10 frames per second</button>
      <button class="trigger-refresh btn" data-fps="5">Set speed to 5 frames per second</button>
      <button class="trigger-refresh btn" data-fps="1">Set speed to 1 frame per second</button>
      <br style="clear:both">
    </div>
  <script src="Heatmap_Animation/heatmap.min.js.download"></script>
  <script>
    window.onload = function() {

      // function generateRandomData(len) {
      //   // generate some random data
      //   var points = [];
      //   var max = 20;
      //   var width = 1920;
      //   var height = 1080;
      //
      //   while (len--) {
      //     var val = Math.floor(Math.random()*100);
      //     max = Math.max(max, val);
      //     var point = {
      //       x: Math.floor(Math.random()*width),
      //       y: Math.floor(Math.random()*height),
      //       value: val
      //     };
      //     points.push(point);
      //   }
      //
      //   var data = {data: points };
      //   return data;
      // }
      function $(selector) {
        return document.querySelectorAll(selector);
      }


      function AnimationPlayer(options) {
        this.heatmap = options.heatmap;
        this.data = options.data;
        this.interval = null;
        this.animationSpeed = options.animationSpeed || 300;
        this.wrapperEl = options.wrapperEl;
        this.isPlaying = false;
        this.init();
      };

      AnimationPlayer.prototype = {
        init: function() {
          var dataLen = this.data.length;
          this.wrapperEl.innerHTML = '';
          var playButton = this.playButton = document.createElement('button');
          playButton.onclick = function() {
            if (this.isPlaying) {
              this.stop();
            } else {
              this.play();
            }
            this.isPlaying = !this.isPlaying;
          }.bind(this);
          playButton.innerText = 'play';

          this.wrapperEl.appendChild(playButton);

          var events = document.createElement('div');
          events.className = 'heatmap-timeline';
          events.innerHTML = '<div class="line"></div>';

          for (var i = 0; i < dataLen; i++) {

            var xOffset = 100/(dataLen - 1) * i;

            var ev = document.createElement('div');
            ev.className = 'time-point';
            ev.style.left = xOffset+'%';

            ev.onclick = (function(i) {
              return function() {
                this.isPlaying = false;
                this.stop();
                this.setFrame(i);
              }.bind(this);
            }.bind(this))(i);

            events.appendChild(ev);

          }
          this.wrapperEl.appendChild(events);
          this.setFrame(0);
        },
        play: function() {
          var dataLen = this.data.length;
          this.playButton.innerText = 'pause';
          this.interval = setInterval(function() {
            this.setFrame(++this.currentFrame%dataLen);
          }.bind(this), this.animationSpeed);
        },
        stop: function() {
          clearInterval(this.interval);
          this.playButton.innerText = 'play';
        },
        setFrame: function(frame) {
          this.currentFrame = frame;
          var snapshot = this.data[frame];
          this.heatmap.setData(snapshot);
          var timePoints = $('.heatmap-timeline .time-point');
          for (var i = 0; i < timePoints.length; i++) {
            timePoints[i].classList.remove('active');
          }
          timePoints[frame].classList.add('active');
        },
        setAnimationData: function(data) {
          this.isPlaying = false;
          this.stop();
          this.data = data;
          this.init();
        },
        setAnimationSpeed: function(speed) {
          this.isPlaying = false;
          this.stop();
          this.animationSpeed = speed;
        }
      };

      var heatmapInstance = h337.create({
        container: document.querySelector('.heatmap'),
          radius: 120,
          blur: 0,
          opacity: .5,
          gradient: {
            '.1': '#0059b3',
            '.2': '#0073e6',
            '.3': '#4da6ff',
            '.4': '#99ccff',
            '.5': '#ffcc66',
            '.6': '#ffbb33',
            '.7': '#ff9900',
            '.9': '#ff5c33',
            '.95': '#ff3300'
          }

      });
      var animationData;
        console.log(animationData);
      // animationData contains an array of heatmap states
      <?php
        echo "animationData = ".$animationData.";";
        ?>
        console.log(animationData);

      // for (var i = 0; i < 20; i++) {
      //   animationData.push(generateRandomData(100));
      // }

      var player = new AnimationPlayer({
        heatmap: heatmapInstance,
        wrapperEl: document.querySelector('.timeline-wrapper'),
        data: animationData,
        animationSpeed: 1000
      });

      /*  legend code */
      // we want to display the gradient, so we have to draw it
      var legendCanvas = document.createElement('canvas');
      legendCanvas.width = 100;
      legendCanvas.height = 10;
      var min = document.querySelector('#min');
      var max = document.querySelector('#max');
      var gradientImg = document.querySelector('#gradient');

      var legendCtx = legendCanvas.getContext('2d');
      var gradientCfg = {};

      function updateLegend(data) {
        // the onExtremaChange callback gives us min, max, and the gradientConfig
        // so we can update the legend
        min.innerHTML = data.min;
        max.innerHTML = data.max;
        // regenerate gradient image
        if (data.gradient != gradientCfg) {
          gradientCfg = data.gradient;
          var gradient = legendCtx.createLinearGradient(0, 0, 100, 1);
          for (var key in gradientCfg) {
            gradient.addColorStop(key, gradientCfg[key]);
          }

          legendCtx.fillStyle = gradient;
          legendCtx.fillRect(0, 0, 100, 10);
          gradientImg.src = legendCanvas.toDataURL();
        }
      };
      /* legend code end */


      var demoWrapper = document.querySelector('.demo-wrapper');
      var tooltip = document.querySelector('.tooltip');

      function updateTooltip(x, y, value) {
        // + 15 for distance to cursor
        var transform = 'translate(' + (x + 15) + 'px, ' + (y + 15) + 'px)';
        tooltip.style.MozTransform = transform; /* Firefox */
        tooltip.style.msTransform = transform; /* IE (9+) - note ms is lowercase */
        tooltip.style.OTransform = transform; /* Opera */
        tooltip.style.WebkitTransform = transform; /* Safari and Chrome */
        tooltip.style.transform = transform; /* One day, my pretty */
        tooltip.innerHTML = value;
      }

      demoWrapper.onmousemove = function(ev) {
        var x = ev.layerX;
        var y = ev.layerY;
        var value = heatmapInstance.getValueAt({
          x: x,
          y: y
        });

        tooltip.style.display = 'block';

        updateTooltip(x, y, value);
      };
      demoWrapper.onmouseout = function() {
        tooltip.style.display = 'none';
      };


      var controlButtons = $('.trigger-refresh');
      for(var i = 0; i < controlButtons.length; i++) {
        controlButtons[i].onclick = function() {
          var fps = this.dataset.fps;
          player.setAnimationSpeed(1/(+fps) * 1000);
        };
      }


    };
  </script>
  </div>
</body>
</html>