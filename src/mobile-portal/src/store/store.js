import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex);
const state={
    userList:{}
}
const mutations={
    setUserList(state,data){
        state.userList=data;
    }
}
const action={
    commitUserList:({commit},userList)=>commit('setUserList',userList)
}