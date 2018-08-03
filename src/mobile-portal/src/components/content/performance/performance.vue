<template>
  <div class="Performance">
     <mt-header fixed title="润和集团考试评测系统">
      <mt-button icon="back" slot="left" @click="goback()"></mt-button>
    </mt-header>
    <div class="Performance-content-title">
      <div class="Performance-content-name">
        <span>试卷名称</span>
      </div>
      <div class="Performance-content-time">
        <span>测试时间</span>
      </div>
      <!-- <div class="Performance-content-duration">
        <span>测试用时</span>
      </div> -->
      <div class="Performance-content-score">
        <span>测试成绩</span>
      </div>
      <div class="Performance-content-start">
        是否通过
      </div>
    </div>
    <ul class="Performance-list">
      <li class="Performance-content" v-for="(PerformanceItme,index) in PerformanceListData" :key="index">
        <div class="Performance-content-name">
          <span>{{PerformanceItme.paperName}}</span>
        </div>
        <div class="Performance-content-time">
          <span>{{PerformanceItme.startTime}}</span>
        </div>
        <!-- <div class="Performance-content-duration">
          <span>{{PerformanceItme.duration}}</span>
          <span>分钟</span>
        </div> -->
        <div class="Performance-content-score">
          <span>{{PerformanceItme.score}}</span>
        </div>
        <div class="Performance-content-start">
          <span v-if="PerformanceItme.paperStatus == 3">通过</span>
          <span v-else>未通过</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
import Comm from '../../../router/commit'
export default {
  name: 'Performance',
  data () {
    return {
      PerformanceListData: [],
      UserId: ''
    }
  },
  mounted: function () {
    this.UserId = sessionStorage.getItem('myid')
    this.InitFun(sessionStorage.getItem('myid'))
  },
  methods: {
    goback () {
      this.$router.push({
        path: '/',
        name: 'index'
      })
    },
    // 页面刷新显示成绩
    InitFun (UserId) {
      axios.get('api/paper-manage/exampaper/' + UserId)
        .then(res => {
          console.log(res)
          if (res.status === 200) {
            console.log(res.data.data.infos)
            let MomentArr = res.data.data.infos
            for (let i = 0; i < MomentArr.length; i++) {
              if (!MomentArr[i].score) {
                MomentArr[i].score = 0
              }
              if (typeof MomentArr[i].startTime === 'number') {
                MomentArr[i].startTime = Comm.timestampToTimeTow(MomentArr[i].startTime)
              } else {
                MomentArr[i].startTime = '-'
              }
            }
            this.PerformanceListData = MomentArr
          }
        })
    }
  }
}

</script>

<style scoped>
  .Performance {
    position: absolute;
    width: 100%;
    height: 100%;
  }

  .Performance-list {
    margin-top: 90px;
    width: 100%;
    height: calc(100% - 50px);
    position: absolute;
    left: 0;
    overflow-x: hidden;
    overflow-y: scroll;

  }

  .Performance-list::-webkit-scrollbar {
    display: none;
  }

  .Performance-content-name {
    width: 20%;
    text-align: center;
  }

  .Performance-content-time {
    width: 22%;
    text-align: center;
  }

  .Performance-content-duration {
    width: 18%;
    text-align: center;
  }

  .Performance-content-score {
    width: 19%;
    text-align: center;
  }

  .Performance-content-start {
    width: 20%;
    text-align: center;
  }

  .Performance-content-title {
    z-index: 999;
    width: 100%;
    position: fixed;
    top:0px;
    left:0px;
    margin-top: 40px;
    padding: 0 10px;
    height: 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #eee;
    color: #222;
    font-size: 14px;
    font-weight: 600;
  }

  .Performance-content {
    height: 50px;
    padding: 0 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #444;
    font-size: 13px;
    border-top: 1px solid #eeeeee;
  }

</style>
