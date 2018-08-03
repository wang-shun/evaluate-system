<template>
  <div class="Exam">
    <mt-header title="润和集团考试评测系统">
      <mt-button icon="back" slot="left" @click="goback()"></mt-button>
    </mt-header>
    <div class="Exam-content-title">
      <div class="Exam-content-name">
        <span>试卷名称</span>
      </div>
      <div class="Exam-content-time">
        <span>考试时间</span>
      </div>
      <div class="Exam-content-duration">
        <span>测试时长</span>
      </div>
      <div class="Exam-content-score">
        <span>考试分数</span>
      </div>
      <div class="Exam-content-start">
        操作
      </div>
    </div>
    <ul id="Exam-list">
      <li class="Exam-content" v-for="(examItme,index) in ExamListData" :key='index'>
        <div class="Exam-content-name">
          <span>{{examItme.colExamName}}</span>
        </div>
        <div class="Exam-content-time">
          <span>{{examItme.colOpenTime}}</span>
          <br>
          <span>{{examItme.colCloseTime}}</span>
        </div>
        <div class="Exam-content-duration">
          <span>{{examItme.colDuration}}</span>
          <span>分钟</span>
        </div>
        <div class="Exam-content-score">
          <span>{{examItme.colPassScore}}</span>
          <span>/</span>
          <span>{{examItme.colTotalScore}}</span>
        </div>
        <div class="Exam-content-start" @click="startTestingFun(examItme)" style="color: #26A2FF;">
          开始测试
        </div>
      </li>
    </ul>

  </div>
</template>

<script>
import axios from 'axios'
import Comm from '../../../router/commit'
import {
  Toast, Indicator
} from 'mint-ui'

export default {
  name: 'Exam',
  data () {
    return {
      ExamListData: []
    }
  },
  created () {
    this.initList()
  },
  methods: {
    goback () {
      this.$router.back(-1)
    },
    initList () {
      let _self = this
      let id = sessionStorage.getItem('myid')
      axios.get('api/exam-manage/examaccount/' + id + '/' + 0, {})
        .then(res => {
          if (res.status === 200) {
            console.log(res.data.data.infos)
            let CrrArray = res.data.data.infos
            if (CrrArray.length === 0) {
              Toast('您当前没有可参与的测试！')
              return
            };
            for (let i = 0; i < CrrArray.length; i++) {
              CrrArray[i].colOpenTime = Comm.timestampToTime(CrrArray[i].colOpenTime)
              CrrArray[i].colCloseTime = Comm.timestampToTime(CrrArray[i].colCloseTime)
            }
            _self.ExamListData = CrrArray
          }
        })
    },
    // 点击开始考试
    startTestingFun (examItme) {
      sessionStorage.setItem('myDuration', examItme.colDuration)
      sessionStorage.setItem('mypaperid', examItme.id)
      let myid = sessionStorage.getItem('myid')
      axios.get('api/exam-manage/examstruct/' + examItme.colExamId, {})
        .then(res => {
          if (res.data.code === '0000') {
            let UpData = {
              examId: examItme.colExamId,
              examStructs: res.data.data.infos,
              paperName: examItme.colExamName,
              paperOwnerId: myid,
              subjectDbId: examItme.colSubjectDbId,
              paperId: examItme.id
            }
            axios.post('api/paper-manage/examPapers', UpData)
              .then(res => {
                if (res.status === 200) {
                  console.log(res.data)
                  if (res.data) {
                    Indicator.open()
                    var sessionData = {}
                    sessionData.TestQuestions = res.data
                    sessionData.examId = examItme.colExamId
                    sessionData.paperOwnerId = myid

                    sessionStorage.setItem('examData', JSON.stringify(sessionData))
                    sessionStorage.setItem('exampaperid', res.data.paperId)
                    console.log(res.data.paperId)

                    setTimeout(() => {
                      Indicator.close()
                      this.$router.push({
                        name: 'examinationPaper',
                        query: sessionData
                      })
                    }, 500)
                  }
                }
              })
          }
        })
    }
  }
}
</script>

<style scoped>
  .Exam {
    position: absolute;
    width: 100%;
    height: 100%;
  }

  .Exam-list {
    width: 100%;
    height: calc(100% - 50px);
    position: absolute;
    left: 0;
    overflow-x: hidden;
    overflow-y: scroll;

  }

  .Exam-list::-webkit-scrollbar {
    display: none;
  }

  .Exam-content-name {
    width: 20%;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;

  }

  .Exam-content-time {
    width: 22%;
    text-align: center;
  }

  .Exam-content-duration {
    width: 18%;
    text-align: center;
  }

  .Exam-content-score {
    width: 19%;
    text-align: center;
  }

  .Exam-content-start {
    width: 20%;
    text-align: center;
  }

  .Exam-content-title {
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

  .Exam-content {
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
