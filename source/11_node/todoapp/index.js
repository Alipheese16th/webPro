const express = require('express'); // express 라이브러리 첨부
const app = express(); // express 라이브러리를 이용, 객체 생성

const uri = "mongodb+srv://shwoo1103:<password>@cluster0.rzqv8p7.mongodb.net/?retryWrites=true&w=majority";

app.listen(8090, function(){
    console.log('listening on 8090');
});


// '/pet' 요청경로(post, get, put, delete)가 들어왔을 때 브라우저 화면에 출력
app.get('/pet',function(request, response){
    response.send('<h2>펫 용품 쇼핑 페이지입니다</h2>');
});

// '/' 요청경로(get)가 들어왔을 때, 브라우저 화면에 출력할 html
app.get('/',function(request,response){
    response.sendFile(__dirname + '/index.html');
});

app.use(express.static('public')); // css, js 등의 static 파일은 public 폴더

// '/write' 요청경로(get)가 들어왔을 때 write.html로 가라
app.get('/write',(request, response)=>{
    response.sendFile(__dirname + '/write.html');
});

// '/write' 요청경로(post)가 들어왔을 때, 파라미터 정보(title, date)를 DB에 저장
app.post('/write',(request, response)=>{
    console.log(request.body?.title);
    console.log(request.body?.date);
    console.log(request.body); // json 형태
    response.sendFile(__dirname + '/write.html');
});

