const express = require('express'); // express 라이브러리 첨부
const app = express(); // express라이브러리를 이용, 객체 생성

// 몽고디비 활용해서 연결
const MongoClient = require('mongodb').MongoClient;
const uri = "mongodb+srv://shwoo1103:<PASSWORD>@cluster0.rzqv8p7.mongodb.net/?retryWrites=true&w=majority";
var db;
MongoClient.connect(uri, function(err, client){

    //MongoDB 연결후 할 일
    if(err){return console.log(err);} // 예외발생시 콘솔에 출력
    db = client.db('toDoApp'); // 'toDoApp' 데이터 베이스 연결
    
    // 임의로 post에 데이터 넣어 보기
    // db.collection('post').insertOne({_id:1, 이름:'홍길동',나이:20},function(err, result){
    //     if(err){return console.log(err);}
    //     console.log('저장 완료');
    // });

    // 웹서버 구동
    app.listen(8090, function(){
        console.log('listening on 8090');
    });

});

// app.listen(8090, function(){
//     console.log("listening on 8090");
// });

// '/pet'요청경로(post, get, put, delete)가 들어왔을 때 브라우저 화면에 출력할 태그
app.get('/pet', function(req, res){
    res.send('<h2>펫 용품 쇼핑 페이지입니다</h2>');
});

// '/'요청경로(get)가 들어왔을 때, 브라우저 화면에 출력할 html
app.get('/', (req, res) => {
    //res.sendFile(__dirname + '/index.html');
    res.render('index.ejs');
});

app.use(express.static('public')); // css, js 등의 static 파일은 public 폴더

// write 뷰페이지
// '/write' 요청경로(get)가 들어왔을 때 write.html로 가라
app.get('/write', (req, res) => {
    //res.sendFile(__dirname + '/write.html');
    res.render('write.ejs');
});

// write 처리
// '/write' 요청경로(post)가 들어왔을 때, 파라미터 정보(title, date)를 DB에 저장
app.use(express.urlencoded({extended : true})); // post방식으로 들어온 req의 파라미터 사용
app.post('/write', (req, res) => {

    // 1. counter 컬렉션에 현재 시퀀스 값(post_sq)을 가져온다
    db.collection('counter').findOne({name:'현재시퀀스'},function(err, result){
        //console.log(result); //findOne결과
        var post_sq = result.post_sq; // 현재 시퀀스 값

        // 2. {_id:현재시퀀스+1, title:req.body.title, date:req.body.date}를 post에 insert
        db.collection('post').insertOne({_id:post_sq+1, title: req.body.title, date: req.body.date}, function(err, result){
            if(err){return console.log(err);}
        });

        // 3. 현재 시퀀스 값(post_sq)을 1 증가시킨다
            // updateOne({수정할 조건}, {$set : ${수정할 내용}}, function(err, result){});
            // updateOne({수정할 조건}, {$inc : ${증분할내용}}, function(err, result){});
        db.collection('counter').updateOne({name:'현재시퀀스'}, {$inc : {post_sq:1}}, function(err, result){
            if(err){
                return console.log(err);
            }
            // 1~3까지 에러 없으면 write.html로 가기
            console.log('post 등록 완료');
            //res.sendFile(__dirname + '/write.html');
            res.redirect('/list');

        });

    });

});

// list 보여주기
// '/list' 요청(get)으로 들어오면 post find한 결과를 배열로 받아 브라우저 화면에 출력
app.set('view engine', 'ejs'); // view엔진으로 ejs를 등록
app.get('/list', (req, res)=>{
    db.collection('post').find().toArray(function(err, result){
        console.log(result); // find 한 결과
        res.render('list.ejs', {posts : result}); // model.addAttribute("posts", result);
    });
});

// list.ejs 에서 하나 삭제하기
app.delete('/delete',function(req, res){
    // req.body._id번 게시물을 post에서 삭제하고 alert 메세지 전송
    var _id = req.body._id; // db에서 int형으로 받아야하는데 이건 string이다
    db.collection('post').deleteOne({_id: Number(_id)},function(err, result){
        if(err){
            return console.log(err);
        }
        res.status(200).send({msg:_id+'번 post 삭제 완료'});
    });
});

// list.ejs 에서 상세보기  ( 파라미터는 /: 이런식으로 받는다  여러개면 /detail/:id/:name 이런식)
app.get('/detail/:id', function(req, res){
    db.collection('post').findOne({_id:Number(req.params.id)}, (err, result)=>{
        if(err){return console.log(err);}
        console.log(result);
        res.render('detail.ejs',{post : result});
    });
});

// detail.ejs 에서 수정form으로 이동  _id의 정보를 가지고있는 하나의 행을 findOne
app.get('/update/:id',function(req, res){
    db.collection('post').findOne({_id:Number(req.params.id)},(err,result)=>{
        if(err){return console.log(err);}
        res.render('update.ejs', {post:result});
    });
});

// 수정form에서 update 처리
app.post('/update',(req, res)=>{
    let _id = req.body._id;
    db.collection('post').updateOne({_id : Number(_id)}, // 수정할 조건
                                    {$set : {title : req.body.title, date : req.body.date}}, // 수정할 내용
                                    (err,result)=>{ // 수정후 콜백함수
                                        if(err){return console.log(err);}
                                        console.log(_id+'번 post 수정완료');
                                        res.redirect('/detail/'+_id);
                                    });
});

app.put('/update',(req, res)=>{
    let _id = req.body._id;
    db.collection('post').updateOne({_id : Number(_id)}, // 수정할 조건
                                    {$set : {title : req.body.title, date : req.body.date}}, // 수정할 내용
                                    (err,result)=>{ // 수정후 콜백함수
                                        if(err){return console.log(err);}
                                        res.status(200).send({msg : _id+'번 post 수정완료'});
                                    });
});

