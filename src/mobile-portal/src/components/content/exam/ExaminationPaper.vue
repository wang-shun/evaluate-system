<template>
  <div class="ExaminationPaper">
    <mt-header title="润和集团考试评测系统">
      <mt-button icon="back" slot="left" @click="goback()"></mt-button>
    </mt-header>
    <div class="ExaPap-SingleChoice" v-if="CurrentData.QuestionTypes && CurrentData.QuestionTypes == '0'">
      <div class="ExaPap-SingleChoice-stem">
        <span>{{QuestionNumber+1}}.</span>
        <span>{{CurrentData.stem}} (单选题)</span>
      </div>
      <div v-if="CurrentData.subjectItems">
        <mt-radio title="" v-model="RadioVal" :options="CurrentData.subjectItems" @change="SingleChoiceFun"></mt-radio>
      </div>
    </div>
    <div class="ExaPap-SingleChoice" v-else-if="CurrentData.QuestionTypes && CurrentData.QuestionTypes == '1'">
      <div class="ExaPap-MultipleChoice-stem">
        <span>{{QuestionNumber+1}}.</span>
        <span>{{CurrentData.stem}} (多选题)</span>
      </div>
      <div v-if="CurrentData.subjectItems">
        <mt-checklist title="复选框列表" v-model="ChecklistVal" :options="CurrentData.subjectItems" @change="MultipleChoiceFun"> </mt-checklist>
      </div>
    </div>
    <div class="ExaminationPaper-switchover">
      <div>
        <mt-button size="normal" type="primary" @click="ShangYitiFun">上一题</mt-button>
      </div>
      <div>
        <mt-button size="normal" type="primary" @click="XiaYitIFun" v-if="QuestionNumber+1 != ItemList.length">下一题</mt-button>
        <mt-button size="normal" type="primary" @click="SubmitPaper" v-else>提交试卷</mt-button>
      </div>
    </div>

    <div class="ExaminationPaper-switchover" style="margin-top:30px">

    </div>
    <!-- 答题导航 -->
    <mt-popup v-model="popupVisible" position="bottom" class="mint-popup-4">
      <div class="examPopTit">答题导航</div>
      <div class="examPopCon">
        <!-- this.ItemList[this.QuestionNumber].RadioVal -->
        <!-- !data.baseData.length -->
        <!-- item.ChecklistVal.length>0 -->
        <div @click="relQusetion(index)" v-for="(item,index) in ItemList" :key="item.id">
          <span class="examPopConItem" v-if="item.QuestionTypes==='0'" :style="item.RadioVal?bgcGreen:bgcwhite">{{index+1}}</span>
          <span class="examPopConItem" v-if="item.QuestionTypes==='1'" :style="item.ChecklistVal.length!==0?bgcGreen:bgcwhite">{{index+1}}</span>
        </div>

      </div>

      <div class="examPopCon" style="clear:both">
        <mt-button size="normal" type="primary" @click="SubmitPaper">提交试卷</mt-button>
      </div>
    </mt-popup>
    <!-- 底部 -->
    <mt-tabbar>
      <mt-tab-item id="tab1">
        <!-- 答题卡 -->
        <mt-button size="normal" type="primary" @click="cardOpen" class='cardButton'>答题卡</mt-button>
      </mt-tab-item>
      <mt-tab-item id="tab2">
        <!-- 倒计时 -->
        <mt-button v-text="keepTime" class="count-down"></mt-button>
      </mt-tab-item>
    </mt-tabbar>
  </div>
</template>

<script>
import axios from 'axios'
import {
  Toast,
  Checklist,
  Radio,
  Popup,
  MessageBox
} from 'mint-ui'
export default {
  name: 'ExaminationPaper',
  data () {
    return {
      bgcGreen: {
        backgroundColor: '#00B439',
        color: '#fff'
      },
      bgcwhite: {
        backgroundColor: '#fff',
        color: '#000'
      },
      keepTime: '倒计时',
      time: '',
      limittime: '',
      settime: '',
      flag: false,
      OptionNumber: ['A', 'B', 'C', 'D', 'E', 'F', 'G'],
      CurrentData: {
        QuestionTypes: ''
      },
      QuestionNumber: 0,
      RadioVal: '',
      ChecklistVal: [],
      ItemList: [],
      StartTime: '',
      examId: '',
      paperId2: '',
      UserId: '',
      popupVisible: false
    }
  },
  created () {
    this.StartCountDown()
  },
  mounted: function () {
    let CurrentTime = new Date()
    this.StartTime = Math.ceil(CurrentTime.getTime())
    var examData = JSON.parse(sessionStorage.getItem('examData'))
    console.log(examData)
    if (examData) {
      this.examId = examData.examId
      this.paperId2 = examData.TestQuestions.paperId
      this.ChecklistFun(examData)
      this.UserId = sessionStorage.getItem('myid')
    } else {
      Toast('页面已经失效')
      setTimeout(() => {
        this.$router.back(-1)
      }, 2000)
    }
    console.log(this.ItemList)
  },
  methods: {
    // 倒计时
    StartCountDown () {
      var mydate = new Date()
      if (!sessionStorage.getItem('startTime')) {
        sessionStorage.setItem('startTime', Date.parse(mydate)) // 存入时间戳
      } else {
        mydate = new Date(parseInt(sessionStorage.getItem('startTime')))
      }

      var myDuration = parseInt(sessionStorage.getItem('myDuration'))
      this.limittime = myDuration
      mydate.setMinutes(mydate.getMinutes() + this.limittime)
      this.settime = mydate
      this.time = setInterval(() => {
        if (this.flag === true) {
          clearInterval(this.time)
        }
        if (this.flag === false) {
          this.timeDown()
        }
      }, 100)
    },
    timeDown () {
      const endTime = new Date(this.settime)
      const nowTime = new Date()
      let leftTime = parseInt((endTime.getTime() - nowTime.getTime()) / 1000)
      let h = this.formate(parseInt(leftTime / (60 * 60) % 24))
      let m = this.formate(parseInt(leftTime / 60 % 60))
      let s = this.formate(parseInt(leftTime % 60))
      if (leftTime === 120) {
        Toast('距离考试结束还剩2分钟,请抓紧答卷')
      }
      if (leftTime === 0) {
        this.flag = true
        // 超时自动提交
        this.SubmitPaperNoCheck()
      }
      this.keepTime = `${h}:${m}:${s}`
    },
    formate (time) {
      if (time >= 10) {
        return time
      } else {
        return `0${time}`
      }
    },
    goback () {
      // 进入试卷界面,如果直接退出,给出提示
      MessageBox.confirm('', {
        message: '确定要退出?退出将自动交卷',
        title: '提示',
        confirmButtonText: '确认',
        cancelButtonText: '取消'
      }).then(action => {
        if (action === 'confirm') { // 确认的回调
          this.SubmitPaperNoCheck()
        }
      }).catch(err => {
        if (err === 'cancel') { // 取消的回调
          console.log(2)
        }
      })
    },
    // 点击答题卡
    cardOpen () {
      this.popupVisible = true
    },

    ChecklistFun: function (ChecklistData) {
      let ArrayOne
      let ArrayTow = []
      let JsonStr = JSON.stringify(ChecklistData)
      // console.log(JsonStr)
      let JsonStrOne = JsonStr.replace(/subjectContent/g, 'stem')
      let JsonStrTow = JsonStrOne.replace(/optionContent/g, 'label')
      let JsonStrThree = JsonStrTow.replace(/optionOrder/g, 'value')
      ArrayOne = JSON.parse(JsonStrThree)

      if (ArrayOne.TestQuestions.singleSubjects && ArrayOne.TestQuestions.singleSubjects.length > 0) {
        for (let i = 0; i < ArrayOne.TestQuestions.singleSubjects.length; i++) {
          ArrayOne.TestQuestions.singleSubjects[i].QuestionTypes = '0'
          ArrayOne.TestQuestions.singleSubjects[i].RadioVal = ''
          ArrayTow.push(ArrayOne.TestQuestions.singleSubjects[i])
        }
      };
      if (ArrayOne.TestQuestions.multiSubjects && ArrayOne.TestQuestions.multiSubjects.length > 0) {
        for (let i = 0; i < ArrayOne.TestQuestions.multiSubjects.length; i++) {
          ArrayOne.TestQuestions.multiSubjects[i].QuestionTypes = '1'
          ArrayOne.TestQuestions.multiSubjects[i].ChecklistVal = []
          ArrayTow.push(ArrayOne.TestQuestions.multiSubjects[i])
        }
      };
      this.ItemList = ArrayTow

      if (ArrayTow.length > 0) {
        for (let i = 0; i < ArrayTow[0].subjectItems.length; i++) {
          ArrayTow[0].subjectItems[i].value = ArrayTow[0].subjectItems[i].value.toString()
        }
      }

      if (ArrayTow.length > 0) {
        this.CurrentData = ArrayTow[0]
        this.QuestionNumber = 0
      }
    },
    ShangYitiFun () {
      if (this.QuestionNumber > 0) {
        this.QuestionNumber--
        for (let i = 0; i < this.ItemList[this.QuestionNumber].subjectItems.length; i++) {
          this.ItemList[this.QuestionNumber].subjectItems[i].value = this.ItemList[this.QuestionNumber].subjectItems[i].value.toString()
        }
        this.CurrentData = this.ItemList[this.QuestionNumber]
        if (this.ItemList[this.QuestionNumber].QuestionTypes === '0') {
          this.RadioVal = this.ItemList[this.QuestionNumber].RadioVal
        } else if (this.ItemList[this.QuestionNumber].QuestionTypes === '1') {
          this.ChecklistVal = this.ItemList[this.QuestionNumber].ChecklistVal
        }
      } else {
        Toast('当前为第一题')
      };
    },
    XiaYitIFun () {
      // if (this.ItemList[this.QuestionNumber].QuestionTypes === '0') {
      //   if (this.RadioVal === '') {
      //     Toast('请选择当前题目答案！')
      //     return false
      //   }
      // } else if (this.ItemList[this.QuestionNumber].QuestionTypes === '1') {
      //   if (this.ChecklistVal.length === 0) {
      //     Toast('请选择当前题目答案！')
      //     return false
      //   }
      // };
      if (this.QuestionNumber < this.ItemList.length) {
        this.QuestionNumber++
        for (let i = 0; i < this.ItemList[this.QuestionNumber].subjectItems.length; i++) {
          this.ItemList[this.QuestionNumber].subjectItems[i].value = this.ItemList[this.QuestionNumber].subjectItems[i].value.toString()
        }
        this.CurrentData = this.ItemList[this.QuestionNumber]
        if (this.ItemList[this.QuestionNumber].QuestionTypes === '0') {
          this.RadioVal = this.ItemList[this.QuestionNumber].RadioVal
        }
        if (this.ItemList[this.QuestionNumber].QuestionTypes === '1') {
          this.ChecklistVal = this.ItemList[this.QuestionNumber].ChecklistVal
        }
      } else {
        Toast('当前为最后一题，请提交试卷！')
      };
    },

    relQusetion (index) {
      this.popupVisible = false
      this.QuestionNumber = index
      for (let i = 0; i < this.ItemList[this.QuestionNumber].subjectItems.length; i++) {
        this.ItemList[this.QuestionNumber].subjectItems[i].value = this.ItemList[this.QuestionNumber].subjectItems[i]
          .value.toString()
      }
      this.CurrentData = this.ItemList[this.QuestionNumber]
      if (this.ItemList[this.QuestionNumber].QuestionTypes === '0') {
        this.RadioVal = this.ItemList[this.QuestionNumber].RadioVal
      }
      if (this.ItemList[this.QuestionNumber].QuestionTypes === '1') {
        this.ChecklistVal = this.ItemList[this.QuestionNumber].ChecklistVal
      }
    },
    SingleChoiceFun (value) {
      this.ItemList[this.QuestionNumber].RadioVal = value
    },
    MultipleChoiceFun (value) {
      this.ItemList[this.QuestionNumber].ChecklistVal = value
    },
    // 提交试卷
    SubmitPaper () {
      sessionStorage.removeItem('startTime')
      clearInterval(this.time)
      let _self = this
      for (let i = 0; i < _self.ItemList.length; i++) {
        if (_self.ItemList[i].QuestionTypes === '0' && !_self.ItemList[i].RadioVal) {
          var str = `请填写第${i + 1}题的答案`
          Toast(str)
          return false
        }
        if (_self.ItemList[i].QuestionTypes === '1' && !_self.ItemList[i].ChecklistVal.length) {
          var str2 = `请填写第${i + 1}题的答案`
          Toast(str2)
          return false
        }
      }
      let answer = []
      for (let i = 0; i < this.ItemList.length; i++) {
        let Arr = this.ItemList[i]
        if (Arr.QuestionTypes === '0') {
          let NewJson = {
            'subjectId': Arr.id,
            'submitAnswer': parseInt(Arr.RadioVal)
          }
          answer.push(NewJson)
        } else if (Arr.QuestionTypes === '1') {
          let multiCheckAnswer = 0
          for (let j = 0; j < Arr.ChecklistVal.length; j++) {
            multiCheckAnswer |= parseInt(Arr.ChecklistVal[j])
          }
          let NewJson = {
            'subjectId': Arr.id,
            'submitAnswer': multiCheckAnswer
          }
          answer.push(NewJson)
        }
      }
      let CurrentTime = new Date()
      let paperId = parseInt(sessionStorage.getItem('mypaperid'))
      console.log(paperId)
      let UpData = {
        'beginTime': this.StartTime,
        'endTime': Math.ceil(CurrentTime.getTime()),
        'examPaperId': this.paperId2,
        'ownerId': this.UserId,
        'answers': answer,
        'paperId': paperId
      }
      axios.put('api/paper-manage/submitExamPaper', UpData)
        .then(res => {
          if (res.status === 200) {
            if (res.data) {
              if (res.data.isPass === 3) {
                Toast('恭喜通过！')
                _self.$router.push({
                  path: '/performance',
                  params: {
                    id: _self.username
                  }
                })
              } else {
                Toast('很遗憾您未通过本次测试！')
                _self.$router.push({
                  path: '/performance',
                  params: {
                    id: _self.username
                  }
                })
              }
            }
          }
        })
    },
    // 提交试卷结束

    // 不检查未答题 提交试卷(超时自动提交,未答题退出)
    SubmitPaperNoCheck () {
      sessionStorage.removeItem('startTime')
      clearInterval(this.time)
      let answer = []
      for (let i = 0; i < this.ItemList.length; i++) {
        let Arr = this.ItemList[i]
        if (Arr.QuestionTypes === '0') {
          let NewJson = {
            'subjectId': Arr.id,
            'submitAnswer': parseInt(Arr.RadioVal)
          }
          answer.push(NewJson)
        } else if (Arr.QuestionTypes === '1') {
          let multiCheckAnswer = 0
          for (let j = 0; j < Arr.ChecklistVal.length; j++) {
            multiCheckAnswer |= parseInt(Arr.ChecklistVal[j])
          }
          let NewJson = {
            'subjectId': Arr.id,
            'submitAnswer': multiCheckAnswer
          }
          answer.push(NewJson)
        }
      }
      let CurrentTime = new Date()
      let paperId = parseInt(sessionStorage.getItem('mypaperid'))
      console.log(paperId)
      let UpData = {
        'beginTime': this.StartTime,
        'endTime': Math.ceil(CurrentTime.getTime()),
        'examPaperId': this.paperId2,
        'ownerId': this.UserId,
        'answers': answer,
        'paperId': paperId
      }
      axios.put('api/paper-manage/submitExamPaper', UpData)
        .then(res => {
          if (res.status === 200) {
            if (res.data) {
              if (res.data.isPass === 3) {
                Toast('恭喜通过！')
                this.$router.push({
                  path: '/performance',
                  params: {
                    id: this.username
                  }
                })
              } else {
                Toast('很遗憾您未通过本次测试！')
                this.$router.push({
                  path: '/performance',
                  params: {
                    id: this.username
                  }
                })
              }
            }
          }
        })
    }
  },
  components: {
    Toast,
    Checklist,
    Radio,
    Popup
  }
}

</script>

<style>
  .dati-card {
    position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    height: 100%;
  }

  .dati-card .color {
    line-height: 50px;
    background-color: #F5F5F5;
    padding: 0 15px;
  }

  .dati-card .color span {
    vertical-align: middle;
    width: 14px;
    height: 14px;
    display: inline-block;
    border: 0.5px solid #747474;
    background-color: red;
    border-radius: 50%;
  }

  .dati-card .color .span1 {
    background-color: #26A2FF;
  }

  .dati-card .color .span2 {
    background-color: #eee;
  }

  .dati-card .allOption {
    padding: 30px 0 30px 30px;
  }

  .dati-card .allOption span {
    font-size: 16px;
    display: inline-block;
    width: 32px;
    height: 32px;
    border: 1px solid #ccc;
    background-color: #eee;
    line-height: 28px;
    text-align: center;
    margin-right: 32px;
    margin-bottom: 20px;
    border-radius: 50%;
  }

  .dati-card footer {
    width: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
    line-height: 50px;
    text-align: center;
    color: #fff;
    font-size: 16px;
    background-color: #26A2FF;
  }

  .ExaPap-SingleChoice {
    padding: 10px;
  }

  .ExaPap-SingleChoice-stem {
    display: flex;
    justify-content: flex-start;
  }

  .ExaPap-SingleChoice-stem>span {
    font-size: 16px;
    color: #666;
  }

  .ExaPap-SingleChoice-stem>span:first-child {
    position: relative;
    top: 4px;
  }

  .ExaPap-SingleChoice-OptionList {
    margin-top: 10px;
    display: flex;
    justify-content: flex-start;
  }

  .mint-radiolist-label {
    display: flex;
    justify-content: flex-start;
    color: #777;
    font-size: 14px;
  }

  .ExaminationPaper-switchover {
    padding: 0px 10px;
    display: flex;
    justify-content: space-between;
  }

  .mint-popup-4 {
    width: 100%;
  }

  .examPopTit {
    padding: 10px;
    font-size: 16px;
  }

  .examPopCon {
    padding: 10px;
  }

  .examPopConItem {
    width: 50%;
    width: 26px;
    height: 26px;
    border-radius: 50%;
    border: 1px solid #ccc;
    padding: 4px 8px;
    margin: 10px 20px;
    float: left;
  }

</style>
