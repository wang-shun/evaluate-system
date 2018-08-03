<template>
  <div class="questionTemplateModification">
      <el-row>
      <el-col :span="20">
        <div class="grid-content bg-purple-dark">
          <el-form :model="CreateTestFormData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <div>
              <el-row v-for="(Item,index) in CreateTestFormData.domains" :key="index">
                <el-col :span="5">
                  <el-form-item label="试题题型">
                    <el-select v-model.number="Item.colSubjectType" style="width: 100%;" @change="examineRepetitionFun(Item,index)">
                      <el-option label="单选" value="单选"> </el-option>
                      <el-option label="多选" value="多选"> </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="create-test-TestTime">
                  <el-form-item label="试题难度">
                    <el-select v-model.number="Item.colDifficuteLevel" style="width: 100%;" @change="examineRepetitionFun(Item,index)">
                      <el-option label="简单" value="简单"> </el-option>
                      <el-option label="一般" value="一般"> </el-option>
                      <el-option label="较难" value="较难"> </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="create-test-TestTime">
                  <el-form-item label="试题数量">
                    <el-input v-model.number="Item.colSubjectCount" @blur="TotalPointsCount"> </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="create-test-TestTime">
                  <el-form-item label="每题分数">
                    <el-input v-model.number="Item.colSubjectPoint" @blur="TotalPointsCount"> </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="2" class="create-test-TestTime" v-if="index == 0">
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <el-button type="primary" @click="AddTopicFun">添加</el-button>
                </el-col>
                <el-col :span="2" class="create-test-TestTime" v-else>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <el-button type="danger" icon="el-icon-delete" circle @click="DeleteTopicFun(index)"></el-button>
                </el-col>
              </el-row>
            </div>
            <el-form-item>
              <el-button type="primary" @click="submitFormFun('ruleForm')">修改</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  name: 'questionTemplateModification',
  data () {
    return {
      userId: '',
      initData: '',
      CreateTestFormData: {
        domains: []
      }

    }
  },
  mounted () {
    let newData = JSON.parse(this.$route.query.data)
    this.userId = localStorage.getItem('myid')
    this.init(newData)
  },
  methods: {
    init (Obj) {
      let _self = this
      let NewArr = Obj.details
      _self.initData = Obj
      for (let index = 0; index < NewArr.length; index++) {
        let newJson = {
          colTemplateId: NewArr[index].colTemplateId,
          colTemplateName: NewArr[index].colTemplateName,
          colSubjectType: NewArr[index].colSubjectType,
          colDifficuteLevel: NewArr[index].colDifficuteLevel,
          colSubjectCount: NewArr[index].colSubjectCount,
          colSubjectPoint: NewArr[index].colSubjectPoint
        }
        switch (newJson.colSubjectType) {
          case 1:
            newJson.colSubjectType = '多选'
            break
          default:
            newJson.colSubjectType = '单选'
            break
        };
        switch (newJson.colDifficuteLevel) {
          case 1:
            newJson.colDifficuteLevel = '简单'
            break
          case 3:
            newJson.colDifficuteLevel = '较难'
            break
          default:
            newJson.colDifficuteLevel = '一般'
            break
        }
        _self.CreateTestFormData.domains.push(newJson)
      }
    },
    TotalPointsCount () {
      let _self = this
      let TotalPoints = 0
      for (let i = 0; i < _self.CreateTestFormData.domains.length; i++) {
        TotalPoints = TotalPoints + _self.CreateTestFormData.domains[i].colSubjectCount * _self.CreateTestFormData.domains[i].colSubjectPoint
      };
      _self.CreateTestFormData.TotalPoints = TotalPoints
    },
    AddTopicFun () {
      if (this.CreateTestFormData.domains.length < 6) {
        let NewJson = {
          colSubjectType: '',
          colDifficuteLevel: '',
          colSubjectCount: '',
          colSubjectPoint: ''
        }
        this.CreateTestFormData.domains.push(NewJson)
      } else {
        this.$message({
          message: '已达到试题类型添加上限！',
          type: 'warning'
        })
      }
    },
    DeleteTopicFun (index) {
      this.CreateTestFormData.domains.splice(index, 1)
    },
    examineRepetitionFun (value, index) {
      console.log(index)
      console.log(value)
      let _self = this
      let copyDomains = _self.CreateTestFormData.domains
      for (let i = 0; i < copyDomains.length; i++) {
        if (copyDomains[i].colSubjectType === value.colSubjectType) {
          if (i !== index) {
            if (copyDomains[i].colDifficuteLevel === value.colDifficuteLevel) {
              value = {
                colSubjectType: '',
                colDifficuteLevel: '',
                colSubjectCount: '',
                colSubjectPoint: ''
              }
              _self.$set(_self.CreateTestFormData.domains, index, value)
              _self.$message({
                message: '您选择的题型重复请重新选择！',
                type: 'warning'
              })
            }
          }
        }
      }
    },
    submitFormFun () {
      let _self = this
      let Updomains = _self.CreateTestFormData.domains
      let UpdomainsData = [] // 要上传的数组
      for (let index = 0; index < Updomains.length; index++) {
        let newJson = {
          colTemplateId: Updomains[index].colTemplateId,
          colTemplateName: Updomains[index].colTemplateName,
          colSubjectType: Updomains[index].colSubjectType,
          colDifficuteLevel: Updomains[index].colDifficuteLevel,
          colSubjectCount: Updomains[index].colSubjectCount,
          colSubjectPoint: Updomains[index].colSubjectPoint
        }
        switch (newJson.colSubjectType) {
          case '多选':
            newJson.colSubjectType = 1
            break
          default:
            newJson.colSubjectType = 0
            break
        };
        switch (newJson.colDifficuteLevel) {
          case '简单':
            newJson.colDifficuteLevel = 1
            break
          case '较难':
            newJson.colDifficuteLevel = 3
            break
          default:
            newJson.colDifficuteLevel = 2
            break
        }
        UpdomainsData.push(newJson)
      }
      let time = new Date()
      let upData = {
        'id': _self.initData.id,
        'colTemplateName': _self.initData.colTemplateName,
        'colCreateTime': parseInt(time.getTime() / 1000),
        'colCreator': _self.userId,
        'details': UpdomainsData
      }
      console.log(_self.initData)
      console.log(upData)
      axios.put('api/exam-manage/updateTemplate', upData)
        .then(res => {
          if (res.status === 200) {
            if (res.data.resultCode === '0') {
              _self.$message({
                message: '修改成功！',
                type: 'success'
              })
              this.$router.push({
                name: 'paperTemplateList',
                path: 'admin/paperTemplateList'
              })
            }
          }
        })
    }
  }
}
</script>
<style lang="scss" scoped>
  .questionTemplateModification {
    min-width: 1400px;
  }
</style>
