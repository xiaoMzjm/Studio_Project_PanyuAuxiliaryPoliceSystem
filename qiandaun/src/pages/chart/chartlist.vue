<template>
  <div>
    <div class="chart-box">
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="chart-num">
            <h3 class="num-title">辅警总数</h3>
            <span class="c-ec">12345</span>
            <span class="num-unit">件</span>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="chart-num">
            <h3 class="num-title">辅警总数</h3>
            <span class="c-ec">12345</span>
            <span class="num-unit">件</span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="chart-num">
            <h3 class="num-title">辅警总数</h3>
            <span class="c-ec">12345</span>
            <span class="num-unit">件</span>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="chart-num">
            <h3 class="num-title">辅警总数</h3>
            <span class="c-ec">12345</span>
            <span class="num-unit">件</span>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="chart-num">
            <h3 class="num-title">辅警总数</h3>
            <span class="c-ec">12345</span>
            <span class="num-unit">件</span>
          </div>
        </el-col>
      </el-row>
      <div class="chart-top">
        <h3 class="num-title">辅警总数</h3>
        <span class="c-ec">12345</span>
        <span class="c-ec">12345</span>
        <span class="c-ec">12345</span>
        <span class="c-ec">12345</span>
      </div>
    </div>
    <!-- <div id="main1" :style="{width: '300px', height: '300px'}"></div> -->
    <img class="bigmap" src="../../assets/bigmap.png">
  </div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'ChartList',
  components: {
    
  },
  computed: {
    
  },
  data() {
    return {
      
    }
  },
  methods: {
    // ...mapActions([
    //   'unit'
    // ]),
    init1() {
      var scale = 1;
var echartData = [{
    value: 21,
    name: '市场签约1'
}, {
    value: 38,
    name: '市场签约2'
}, {
    value: 35,
    name: '市场签约3'
}, {
    value: 31,
    name: '市场签约4'
}, {
    value: 28,
    name: '市场签约5'
}, {
    value: 24,
    name: '市场签约6'
}, {
    value: 25,
    name: '市场签约7'
}]
var rich = {
    yellow: {
        color: "#3bc7ff",
        fontSize: 14 * scale,
        padding: [8, 0],
        align: 'center'
    },
    total: {
        color: "#A2C7F3",
        fontSize: 60 * scale,
        align: 'center'
    },
    white: {
        color: "#a2c7f3",
        align: 'center',
        fontSize: 18 * scale,
        padding: [8,0 ]
    },
    blue: {
        color: '#3bc7ff',
        fontSize: 18 * scale,
        align: 'center'
    },
    hr: {
        borderColor: '#a2c7f3',
        width: '100%',
        borderWidth: 1,
        height: 0,
    }
}
var option = {
    backgroundColor: '#031f2d',
    title: {
        text:'总得分',
        left:'center',
        top:'51%',
        padding:[10,0],
        textStyle:{
            color:'#6B9DD7',
            fontSize:18*scale,
            fontWeight:'normal',
            align:'center'
        }
    },
    legend: {
        selectedMode:false,
        formatter: function() {
            var total = 0; //各科正确率总和
            // var averagePercent; //综合正确率
            echartData.forEach(function(value, index) {
                total += value.value;
                console.log(index)
            });
            return '{total|' + total + '}{white|分}';
        },
        data: [echartData[0].name],
        // data: ['高等教育学'],
        // itemGap: 50,
        left: 'center',
        top: 'center',
        icon: 'none',
        align:'center',
        textStyle: {
            color: "#fff",
            fontSize: 18 * scale,
            rich: rich
        },
    },
    series: [{
        name: '总考生数量',
        type: 'pie',
        radius: ['42%', '50%'],
        hoverAnimation: false,
        color: ['#fc962c', '#d83472', '#0F9AF8', '#2B63D5', '#2039C3', '#2ECACE','#6F81DA'],
        label: {
            normal: {
                formatter: function(params, ticket) {
                    var total = 0; //考生总数量
                    var percent = 0; //考生占比
                    echartData.forEach(function(value, index) {
                        total += value.value;
                        console.log(index, ticket)
                    });
                    percent = ((params.value / total) * 100).toFixed(1);
                    return '{white|' + params.name + '}\n{hr|}\n{yellow|' + params.value + '分/}{blue|' + percent + '%}';
                },
                rich: rich
            },
        },
        labelLine: {
            normal: {
                length:20 * scale,
                length2: 0,
                lineStyle: {
                    color:'#a2c7f3'
                }
            }
        },
        data: echartData
    }]
};
var myChart = echarts.init(document.getElementById('main1'));
myChart.setOption(option)
    }
  },
  mounted() {
    this.init1()
  }
}
</script>

<style scoped>
.chart-box {
  height: 500px;
  background-color: #0f375f;
  color: #ffffff;
  font-size: 1.2em;
}
.chart-num, .chart-top {
  width: 200px;
  display: inline-block;
}
.num-title {
  border-bottom: 1px solid #59DDE1;
  margin: 0;
  padding: 10px 0;
}
.chart-top .c-ec {
  font-size: 2em;
  display: block;
  border-bottom: 1px solid #59DDE1;
}
.c-ec {
  font-size: 2.5em;
  color: #ECED38;
}
.num-unit {
  padding-left: 5px;
  color: #59DDE1;
}
</style>
