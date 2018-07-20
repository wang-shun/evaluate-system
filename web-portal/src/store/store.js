import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const state = {
    username: '' //先是赋值为了admin,在刷新后又被还原为空
    //因为Vuex管理的数据是应用级的,也就是说当点击刷新之后,这整个应用程序就相当于重启了,一重启,这个state里面保存的数据会被还原,即还原为空””
}
const mutations={
    setUsername: (state,username) =>{
        state.username = username; //state.username等于传进来的这个username
        //1.在mutations中把username保存到localStorage中,下一次读取时,从localStorage中去读取
        localStorage.setItem('username',username)//设置'username',它的值就是传过来的admin
    }
}
const actions={

}
const getters={
    //2.在getters中取; 取出之后就保存到了username变量中;
    username: (state) => localStorage.getItem('username')
    //3.去home页面取这个变量,ok
}
export default new Vuex.Store({
    state,
    getters,
    actions,
    mutations
})