<template>
  <div>
    <div id="container"></div>
    <div class="g-poi-search">
      <div>
        <span class="text">位置</span>
        <el-select
          v-model="sPoiSearch"
          filterable
          remote
          reserve-keyword
          placeholder="请输入地名"
          :no-data-text="'查询失败，请输入正确地名'"
          :popper-class="'g-poi-search-popper'"
          :remote-method="fnRemoteMethod"
          :loading="bPoiSearchLoading"
        >
          <el-option
            v-for="item in aPoiSearchOptions"
            :key="item.id"
            :label="item.name"
            :title="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
    </div>

    <div class="info" style="position: absolute;left: 100px;top: 110px;color: #606266;font-size: 12px ">
      <h4>{{ placeWeather }}未来四天预报天气</h4>
      <hr>
      <p id='forecast'></p>
    </div>

  </div>
</template>

<script src="https://webapi.amap.com/maps?v=1.4.15&key=2df80b7ec247496a9f8233cdff6ddb95">
</script>

<script>
import AMapLoader from "@amap/amap-jsapi-loader";
import store from "@/store";
import {mapGetters} from "vuex";

export default {
  name: "map-view",
  computed: {
    ...mapGetters(['address'])
  },
  data() {
    return {
      placeWeather: "",
      map: null,
      sPoiSearch: "",
      aPoiSearchOptions: [],
      bPoiSearchLoading: false,
      PlaceSearch: null,//搜索
      ControlBar: null,//视图控制
      ToolBar: null,//缩放
      Scale: null,//比例尺
      HawkEye: null,//小地图
      Geolocation: null,//定位
      Driving: null,//骑行，驾车路线
      MapType: null,//图层
      Weather: null//天气
    };
  },
  mounted() {
    window._AMapSecurityConfig = {
      securityJsCode: "c8e48f8683ace759c7404f3829f5f79c",
    };
    this.initAMap();
  },
  methods: {
    initAMap() {
      AMapLoader.load({
        key: "2df80b7ec247496a9f8233cdff6ddb95",
        version: "2.0",
        plugins: [
          'AMap.Marker',
          'AMap.PlaceSearch',
          "AMap.ControlBar",
          'AMap.ToolBar',
          'AMap.Scale',
          'AMap.HawkEye',
          'AMap.Geolocation',
          'AMap.MapType',
          'AMap.Driving',
          'AMap.Weather'
        ],
      })
        .then((AMap) => {
          this.map = new AMap.Map("container", {
            viewMode: "3D",
            zoom: 11,
          });

          this.ControlBar = new AMap.ControlBar({
            position: {
              top: "200px",
              right: "10px",
            }
          })

          this.ToolBar = new AMap.ToolBar()
          this.PlaceSearch = new AMap.PlaceSearch()
          this.Scale = new AMap.Scale()
          this.HawkEye = new AMap.HawkEye()
          this.Geolocation = new AMap.Geolocation({
            position: {
              top: "200px",
              left: "10px",
            }
          })
          this.MapType = new AMap.MapType({
            position: {
              top: "10px",
              left: "100px",
            }
          })

          this.Weather = new AMap.Weather()

          this.map.addControl(this.ToolBar);
          this.map.addControl(this.Scale);
          this.map.addControl(this.HawkEye);
          this.map.addControl(this.Geolocation);
          this.map.addControl(this.ControlBar);
          this.map.addControl(this.MapType);

          this.setupPlaceSearchListeners();

          this.map.on("click", async (event) => {

            const position = event.lnglat; // 获取点击位置的经纬度坐标
            const addressName = await this.getAddressName(position); // 获取点击位置的地址名称
            console.log("经度:", position.lng, "纬度:", position.lat, "地址名称:", addressName);

            store.commit('SET_LNG', position.lng);
            store.commit('SET_LAT', position.lat);
            store.commit('SET_ADDRESS', addressName);

            this.loadData(addressName)
          });
        })
        .catch((e) => {
          console.log(e);
        });


      /*          var drivingOptions = {
                  //驾车策略，包括 LEAST_TIME，LEAST_FEE, LEAST_DISTANCE,REAL_TRAFFIC
                  policy: AMap.DrivingPolicy.LEAST_TIME
                };
                var driving = new AMap.Driving(drivingOptions);

                //根据起终点坐标规划驾车路线
                driving.search(new AMap.LngLat(116.379018, 39.865026), new AMap.LngLat(116.42732, 39.903752));
                          this.map.addControl(driving);*/

    },
    setupPlaceSearchListeners() {

    },
    extractCityName(location) {
      let city = '';
      // 定义正则表达式来匹配市名
      const cityRegex = /([^省市区]+市)/;

      const match = location.match(cityRegex);
      if (match && match[0]) {
        city = match[0];
      }

      return city;
    },
    getAddressName(position) {
      return new Promise((resolve, reject) => {
        AMap.plugin('AMap.Geocoder', () => {
          const geocoder = new AMap.Geocoder();
          geocoder.getAddress(position, (status, result) => {
            if (status === 'complete' && result.info === 'OK') {


              resolve(result.regeocode.formattedAddress);
            } else {
              reject('获取地址名称失败');
            }
          });
        });
      });
    },


    fnRemoteMethod(query) {
      if (query !== "") {
        this.bPoiSearchLoading = true;
        this.PlaceSearch.search(query, (status, result) => {
          if (status === "complete" && result.info === "OK") {
            this.aPoiSearchOptions = result.poiList.pois.map((v) => ({
              id: v.id,
              name: v.name,
              location: v.location,
            }));
            var location = this.aPoiSearchOptions[0].location;
            // 移动地图中心到查询结果位置
            this.map.setCenter(location);
          } else {
            this.aPoiSearchOptions = [];
          }
          this.bPoiSearchLoading = false;
        });
      } else {
        this.aPoiSearchOptions = [];
      }
    },
    loadData(addressFull) {

      var address = this.extractCityName(addressFull)
      console.log("市", address)
      this.placeWeather = address

      AMap.plugin("AMap.Weather", function () {
        var weather = new AMap.Weather();
        //查询实时天气信息, 查询的城市到行政级别的城市，如朝阳区、杭州市

        var str = [];

        weather.getForecast(address, function (err, data) {
          if (err) {
            return;
          }

          for (var i = 0, dayWeather; i < data.forecasts.length; i++) {
            dayWeather = data.forecasts[i];
            str.push(
              dayWeather.date +
              ' <span class="weather">' +
              dayWeather.dayWeather +
              "</span> " +
              dayWeather.nightTemp +
              "~" +
              dayWeather.dayTemp +
              "℃"
            );
          }

          document.getElementById("forecast").innerHTML = str.join("<br>");
        });
      });
    },
  },
};
</script>

<style>
#container {
  width: 100%;
  height: 600px;
}

.g-poi-search {
  position: absolute;
  top: 6px;
  right: 6px;
  padding: 8px;
  border-radius: 6px;
  background-color: #fff;
}

.g-poi-search .el-select .el-input > input.el-input__inner {
  height: 28px;
  line-height: 28px;
}

.g-poi-search-popper .el-select-dropdown__item {
  width: 200px;
  height: 28px;
  line-height: 28px;
}

.text {
  font-size: 12px;
  margin-right: 15px;
}

//天气
#cc {
  width: 100%;
  height: 100%;
}

#ceshi {
  width: 100%;
  height: 100%;
}

.weather {
  width: 5rem;
  display: inline-block;
  padding-left: 0.5rem;
}

.sharp {
  height: 1rem;
  width: 1rem;
  background-color: white;
  transform: rotateZ(45deg);
  box-shadow: 2px 2px 3px rgba(114, 124, 245, 0.5);
  position: inherit;
  margin-left: 10.5rem;
  margin-top: -6px;
}


.amap-logo {
  display: none;
  opacity: 0 !important;
}

.amap-copyright {
  opacity: 0;
}

</style>
