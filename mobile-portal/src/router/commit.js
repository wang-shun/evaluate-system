export default{
	timestampToTime:function(timestamp){
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        if(date.getHours() < 10)
        {
          h = '0'+h;
        }
        var m = date.getMinutes() + ':';
        if(date.getMinutes() < 10)
        {
          m = '0'+m;
        }
        var s = date.getSeconds();
        if(date.getSeconds() < 10)
        {
          s = '0'+s;
        }
        return Y+M+D;
   },
   timestampToTimeTow:function(timestamp){
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        if(date.getHours() < 10)
        {
          h = '0'+h;
        }
        var m = date.getMinutes();
        if(date.getMinutes() < 10)
        {
          m = '0'+m;
        }
        return Y+M+D+h+m;
    }
}
