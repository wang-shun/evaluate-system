<template>
  <div class="addTestQuestions">
    <el-form ref="from" :model="questionFromData" label-width="80px" class="demo-ruleForm">
      <el-form-item label="试题题库">
        <el-select v-model="questionFromData.questionId" placeholder="请选择">
          <el-option v-for="item in questionListData" :key="item.id" :label="item.subjectDbName" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="题干内容">
        <el-input type="textarea" v-model="questionFromData.stem"></el-input>
      </el-form-item>
      <el-form-item label="试题选项">
        <el-row class="addTestQuestions-optionList" v-for="(item,index) in optionListData" :key="index">
          <el-col :span="2" class="addTestQuestions-optionName">{{item.optionName}}</el-col>
          <el-col :span="18">
            <el-input type="textarea" v-model="item.optionContent"></el-input>
          </el-col>
          <el-col :span="2" class="addTestQuestions-optionName" v-if="optionListData.length > 1">
            <el-button type="danger" icon="el-icon-delete" circle @click="deleteOptionFun(index)"></el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-col :span="2" class="addTestQuestions-optionName">
              <el-button type="primary" @click="addOptionFun">添加</el-button>
            </el-col>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="试题类型">
        <el-select v-model="questionFromData.questionType" placeholder="请选择活动区域" @change="answerSwitchoverFun">
          <el-option label="单选" value="单选"></el-option>
          <el-option label="多选" value="多选"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="试题难度">
        <el-select v-model="questionFromData.questionDifficulty" placeholder="请选择活动区域">
          <el-option label="容易" value="容易"></el-option>
          <el-option label="一般" value="一般"></el-option>
          <el-option label="较难" value="较难"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开放状态">
        <el-switch v-model="questionFromData.state"></el-switch>
      </el-form-item>
      <el-form-item label="正确答案" prop="stem">
        <span v-if="answerSwitchoverData">
          <el-radio-group v-model="questionFromData.radioAnswer">
            <el-radio v-for="(item,index) in answerListData" :key="index" :label="item"></el-radio>
          </el-radio-group>
        </span>
        <span v-else>
          <el-checkbox-group v-model="questionFromData.checkboxAnswer">
            <el-checkbox v-for="(item,index) in answerListData" :label="item" :key="index" name="type"></el-checkbox>
          </el-checkbox-group>
        </span>
      </el-form-item>
      <el-button type="primary" @click="submitDataFun">立即创建</el-button>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'addTestQuestions',
  data () {
    return {
      userId: '',
      questionListData: [],
      questionFromData: {
        questionId: '',
        stem: '',
        questionType: '单选',
        questionDifficulty: '一般',
        state: true,
        checkboxAnswer: [],
        radioAnswer: ''
      },
      optionListData: [{
        optionName: '选项A',
        optionContent: ''
      }],
      answerSwitchoverData: true,
      answerListData: ['选项A'],
      prefabricateName: ['选项A', '选项B', '选项C', '选项D', '选项E', '选项F', '选项H']
    }
  },
  mounted () {
    let _self = this
    _self.userId = localStorage.getItem('myid')
    _self.downloadDataFun()
  },
  methods: {
    downloadDataFun () { // 题库列表获取
      let _self = this
      let upData = {
        params: {
          pageNum: 1,
          pageSize: 1000000000
        }
      }
      axios.get('api/subject-manage/queryAllsubjectDb', upData)
        .then(res => {
          if (res.status === 200 && res.data) {
            _self.questionListData = res.data.list
          }
        })
    },
    addOptionFun () { // 添加新选项
      let _self = this
      let arrOne = _self.prefabricateName
      if (_self.optionListData.length < 7) {
        let newJson = {
          optionName: arrOne[_self.optionListData.length],
          optionContent: ''
        }
        _self.answerListData.push(arrOne[_self.optionListData.length])
        _self.optionListData.push(newJson)
      }
    },
    deleteOptionFun (intoIndex) {
      let _self = this
      let arrOne = _self.optionListData
      let arrTow = []
      let arrThree = []
      if (arrOne.length > 1) {
        arrOne.splice(intoIndex, 1)
        for (let index = 0; index < arrOne.length; index++) {
          let newJson = {
            optionName: _self.prefabricateName[index],
            optionContent: arrOne[index].optionContent
          }
          arrTow.push(newJson)
          arrThree.push(_self.prefabricateName[index])
        }
        _self.optionListData = arrTow
        _self.answerListData = arrThree
      }
    },
    answerSwitchoverFun (value) {
      let _self = this
      if (value === '0') {
        _self.answerSwitchoverData = true
      } else {
        _self.answerSwitchoverData = false
      }
    },
    submitDataFun () {
      let _self = this
      let DataJson = _self.questionFromData
      let copyPreName = _self.prefabricateName
      if (DataJson.questionId === '') {
        _self.$message({
          type: 'info',
          message: '请选择题库！'
        })
        return false
      }
      if (DataJson.stem === '') {
        _self.$message({
          type: 'info',
          message: '请填写题目内容！'
        })
        return false
      }
      let upData = {
        'answer': {
          'subjectAnswer': 0
        },
        'creatorId': _self.userId,
        'defficultyWeight': 1,
        'isOpen': 1,
        'subjectDbId': DataJson.questionId,
        'items': [],
        'subjectContent': DataJson.stem,
        'subjectType': 1
      }
      if (DataJson.questionType === '单选') {
        upData.subjectType = 0
      } else if (DataJson.questionType === '多选') {
        upData.subjectType = 1
      } else {
        return false
      }
      if (DataJson.questionDifficulty === '容易') {
        upData.defficultyWeight = 1
      } else if (DataJson.questionDifficulty === '一般') {
        upData.defficultyWeight = 2
      } else if (DataJson.questionDifficulty === '较难') {
        upData.defficultyWeight = 3
      } else {
        return false
      }
      if (DataJson.state) {
        upData.isOpen = 1
      } else {
        upData.isOpen = 0
      }
      for (let i = 0; i < _self.optionListData.length; i++) {
        if (_self.optionListData[i].optionContent === '') {
          _self.$message({
            type: 'info',
            message: '您有空的选项！'
          })
          return false
        }
        let newJson = {
          'optionContent': _self.optionListData[i].optionContent,
          'optionOrder': Math.pow(2, i)
        }
        upData.items.push(newJson)
      }
      if (_self.answerSwitchoverData) {
        if (DataJson.radioAnswer !== '') {
          for (let index = 0; index < copyPreName.length; index++) {
            if (DataJson.radioAnswer === copyPreName[index]) {
              upData.answer.subjectAnswer = Math.pow(2, index)
            }
          }
        } else {
          _self.$message({
            type: 'info',
            message: '请添加您的正确答案！'
          })
          return false
        }
      } else {
        if (DataJson.checkboxAnswer.length > 0) {
          for (let i = 0; i < DataJson.checkboxAnswer.length; i++) {
            for (let j = 0; j < copyPreName.length; j++) {
              if (DataJson.checkboxAnswer[i] === copyPreName[j]) {
                DataJson.checkboxAnswer[i] = Math.pow(2, j)
                upData.answer.subjectAnswer = upData.answer.subjectAnswer | DataJson.checkboxAnswer[i]
              }
            }
          }
        } else {
          _self.$message({
            type: 'info',
            message: '请添加您的正确答案！'
          })
          return false
        }
      }
      axios.post('api/subject-manage/subject', upData)
        .then(res => {
          if (res.status === 200) {
            _self.$message({
              message: '添加试题成功！',
              type: 'success'
            })
            _self.questionFromData = {
              questionId: '',
              stem: '',
              questionType: '单选',
              questionDifficulty: '一般',
              state: true,
              checkboxAnswer: [],
              radioAnswer: ''
            }
            _self.optionListData = [{
              optionName: '选项A',
              optionContent: ''
            }]
            _self.answerSwitchoverData = true
            _self.answerListData = ['选项A']
          }
        })
    }
  }
}

</script>

<style>
  .addTestQuestions-optionList {
    margin-bottom: 10px;
  }

  .addTestQuestions-optionName {
    text-align: center
  }

  .addTestQuestions-list{
    margin-bottom: 20px;
  }

</style>
