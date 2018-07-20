
<template>
  <div id="app">		
		<mt-checklist 
		v-model="value" 
		:options="options">
		</mt-checklist>
  </div>
</template>
 
<script>
export default {
  name: 'HelloWorld',
  data () {
  	return {
  		//存放所选选项
			value:[],
			//checklist设置
			options : [{
		    label: '选项A',
		    value: 'A',
		    disabled: true	//可以禁用选项
			},
			{
		    label: '选项B',
		    value: 'B',
		    disabled: true
			},
			{
		    label: '选项C',
		    value: 'C'
			},
			{
		    label: '选项D',
		    value: 'D'
			}]
  	}
  },
  mounted:function(){
  	
  }
}
</script>
 
<style>
	
