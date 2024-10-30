 $( function() {
   $('input[name="delete-button"]').on('click',function(){
        if (confirm("本当に削除しますか？")){
           return true;
        } else {
            return false;
        }
    });
 });
 $( function() {
   $('input[name="isStopped-button"]').on('click', function(){
       if (confirm("ユーザー停止状態を変更してよろしいですか？")) {
           return true;
       } else {
           return false;
       }
   });
 });