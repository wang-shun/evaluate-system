export default {
  // 大写字母、小写字母、数字、特殊字符 满足其中三种即可
  checkStrong: function (value) {
    var modes = 0
    if (value.length < 8) return 0
    if (/\d/.test(value)) modes++
    if (/[a-z]/.test(value)) modes++
    if (/[A-Z]/.test(value)) modes++
    if (/\W/.test(value)) modes++
    return modes * 25
  },
  IterationDelateMenuChildren: function (arr) {
    if (arr.length) {
      for (let i in arr) {
        if (arr[i].children.length) {
          this.IterationDelateMenuChildren(arr[i].children)
        } else {
          delete arr[i].children
        }
      }
    }
    return arr
  }
}
