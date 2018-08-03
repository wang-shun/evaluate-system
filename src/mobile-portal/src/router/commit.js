export default {
  // 密码强度校验 大写字母、小写字母、数字、特殊字符 满足其中三种即可
  checkStrong: function (value) {
    var modes = 0
    if (value.length < 8) return 0
    if (/\d/.test(value)) modes++
    if (/[a-z]/.test(value)) modes++
    if (/[A-Z]/.test(value)) modes++
    if (/\W/.test(value)) modes++
    return modes * 25
  },
  timestampToTime: function (timestamp) {
    // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var date = new Date(timestamp)
    var Y = date.getFullYear() + '-'
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
    var D = date.getDate() + ' '
    // var h = date.getHours() + ':'
    // if (date.getHours() < 10) {
    //   h = '0' + h
    // }
    // var m = date.getMinutes() + ':'
    // if (date.getMinutes() < 10) {
    //   m = '0' + m
    // }
    // var s = date.getSeconds()
    // if (date.getSeconds() < 10) {
    //   s = '0' + s
    // }
    return Y + M + D
  },
  timestampToTimeTow: function (timestamp) {
    var date = new Date(timestamp) // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-'
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
    var D = date.getDate() + ' '
    var h = date.getHours() + ':'
    if (date.getHours() < 10) {
      h = '0' + h
    }
    var m = date.getMinutes()
    if (date.getMinutes() < 10) {
      m = '0' + m
    }
    return Y + M + D + h + m
  }
}
