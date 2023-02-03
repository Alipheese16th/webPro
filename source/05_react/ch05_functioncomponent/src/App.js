import Subject from './components/Subject';
import TOC from './components/TOC';
import Control from './components/Control';
import ReadContent from './components/ReadContent';
import React,{useState} from 'react';
import CreateContent from './components/CreateContent';
import UpdateContent from './components/UpdateContent';

function App() {
  // let mode = 'read';
  // const _mode = useState('read');
  // let mode = _mode[0];
  // let setMode = _mode[1];
  let [nextId,setNextId] = useState(4); // 현재 contents의 가장 큰 id. create에서 사용. (delete경우에도 수는 유지)
  const [mode,setMode] = useState('read');
  const [selectedId,setSelectedId] = useState(2);
  const [contents,setContents] = useState([
    {id:1, title:'HTML', body:'HTML is HyperText Markup Language.'},
    {id:2, title:'CSS', body:'CSS is for design.'},
    {id:3, title:'JavaScript', body:'JavaScript is for interactive.'},
  ]);
  let subject = {title:'WEB', sub:'World Wide Web!'};
  let welcome = {
    title:'WELCOME', body:'Hello, React !!! !!!',
  };

  let _article, _title, _body = null;
  if(mode === 'welcome'){
    _title = welcome.title;
    _body = welcome.body;
    _article = <ReadContent title={_title} body={_body}></ReadContent>;

  }else if(mode === 'read'){
    for(let i=0;i<contents.length;i++){
      let data = contents[i];
      if(data.id === selectedId){
        _title = data.title;
        _body = data.body;
        break;
      }
    }
    _article = <ReadContent title={_title} body={_body}></ReadContent>;

  }else if(mode === 'create'){
    _article = <CreateContent onCreate={(title,body)=>{
      console.log(nextId);
      let _contents = [...contents];
      _contents.push({id:nextId,title:title,body:body});
      setContents(_contents);
      setSelectedId(nextId);
      setNextId(nextId+1);
    }}></CreateContent>

  }else if(mode === 'update'){
    let _content;
    for(let i=0;i<contents.length;i++){
      if(contents[i].id === selectedId){
        _content = contents[i];
        break;
      }
    }
    _article = <UpdateContent data={_content} onUpdate={(title,body)=>{
      // contents를 복제한 _contents의 id가 selectedId랑 같은 _contents안의 객체를 변경
      let _contents = [...contents];
      for(let i=0;i<_contents.length;i++){
        if(_contents[i].id === selectedId){
          _contents[i] = {id:selectedId,title:title,body:body};
          break;
        }
      }
      setContents(_contents);
      setMode('read');
    }}></UpdateContent>

  } // if (mode)

  return (
    <>
      <Subject 
        title={subject.title} 
        sub={subject.sub}
        onChangeMode={()=>{
          // mode = 'welcome';
          setMode('welcome');
        }}
      ></Subject>
      <TOC 
        data={contents}
        onChangeMode={(_id)=>{
          // selectedId = _id;
          setSelectedId(_id);
          setMode('read');
        }}
      ></TOC>
      <Control onChangeMode={(_mode)=>{
        if(_mode === 'delete'){
          // delete 작업
          if(window.confirm('삭제 복구 불가합니다. 삭제하시겠습니까?')){
            let _contents = [...contents];
            for(let i=0; i<_contents.length; i++){
              if(_contents[i].id === selectedId){
                _contents.splice(i,1); // i번째 1개 제거
                let _id;
                if(_contents.length>0){
                  // _id = _contents[0].id;  // 제일 작은 id 값을 selectedId
                  _id = _contents[_contents.length-1].id;  // 제일 큰 id 값을 selectedId
                }else{
                  _id = 0;
                } // if
                setContents(_contents);
                setMode('read');
                setSelectedId(_id);
                break;
              } // if
            } // for
          } // if - confirm
        }else{
          setMode(_mode);
        }
      }}>
      </Control>
      {_article}
    </>
  );
}

export default App;
