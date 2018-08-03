<template>
  <div class="index">
    <mt-header title="润和集团考试评测系统">
      <mt-button icon="more" slot="right" @click="popupMyFun"></mt-button>
    </mt-header>
    <ul class="index-list">
      <li @click=resExam>
        <i class="icon iconfont hoperun-kaoshizhongxinoff indexFooter-icon"></i>
        <br>
        <span class="indexFooter-text">我的考试</span>
      </li>
      <li @click=resPerformance>
         <i class="icon iconfont hoperun-lishijilu  indexFooter-icon"></i>
          <br>
          <span class="indexFooter-text">成绩查询</span>
      </li>
    </ul>
    <div id="indexPopup" class="index-popup" v-show="indexPopupShow" @click="popupMyFun">
      <!-- 修改密码 -->
      <mt-button type="default" class="mint-button--large" @click="changePwd">修改密码</mt-button>
      <mt-button type="default" class="mint-button--large" @click="logout">退出</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Index',
  data () {
    return {
      indexPopupShow: false

    }
  },
  methods: {
    // 退出
    logout () {
      sessionStorage.removeItem('myid')
      this.$router.push({
        path: '/login',
        name: 'login'
      })
    },
    // 修改密码
    changePwd () {
      this.$router.push({
        name: 'stuChangePwd'
      })
    },
    popupMyFun: function () {
      if (this.indexPopupShow) {
        this.indexPopupShow = false
      } else {
        this.indexPopupShow = true
      }
    },
    resExam () {
      let _this = this
      _this.$router.push({
        path: '/exam'
      })
    },
    resPerformance () {
      let _this = this
      _this.$router.push({
        path: '/performance'
      })
    }
  }
}

</script>

<style scoped>
  .index-list {
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
  }

  .index-list>li {
    width: 50%;
    max-width: 200px;
    text-align: center;
  }

  .index-list>li {
    padding: 10px 0px;
    box-sizing: border-box;
    border-bottom: 1px solid #eee;
  }

  .index-list>li:nth-child(even) {
    border-left: 1px solid #eee;
  }

  .indexFooter-icon {
    font-size: 70px;
    color: #444;
  }

  .indexFooter-text {
    font-size: 16px;
  }

  .index-popup {
    position: fixed;
    top: 40px;
    right: 0px;
    width: 100%;
    height: calc(100% - 40px);
    background-color: rgba(255, 255, 255, 1);
  }

  .mint-button--large {
    margin: 10% auto 0px;
    display: block;
    width: 80%;
    background-color: #26a2ff;
    color: #fff;
  }

</style>
