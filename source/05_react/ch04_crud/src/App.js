import './App.css';
import React, {Component} from 'react';
import Subject from './components/Subject';
import TOC from './components/TOC';
import ReadContent from './components/ReadContent';
import Control from './components/Control';
import CreateContent from './components/CreateContent';
import UpdateContent from './components/UpdateContent';

class App extends Component{
  constructor(props){
    super(props);
    this.max_content_id = 3;
    this.state = {
      mode : 'welcome',
      selected_content_id : 1,
      subject : {title:'WEB',sub:'World wide web!'},
      contents : [
        {id:1, title:'HTML', desc:'HTML is HyperText Markup Language.'},
        {id:2, title:'CSS', desc:'CSS is for design'},
        {id:3, title:'JavaScript', desc:'JavaScript is for interacitive'},
      ],
      welcome : {title:'WELCOME', desc:'Hello, React!!!'},
    };
  } // constructor

  getReadContent(){ // this.state.selected_content_id가 id인 contents내 객체를 return
    for(var idx=0; idx<this.state.contents.length; idx++){
      var data = this.state.contents[idx];
      if(data.id === this.state.selected_content_id){
        return data;
      }
    }
  } // getReadContent()

  getContent(){
    var _title, _desc, _article = null;
    if(this.state.mode === 'welcome'){
      _title = this.state.welcome.title;
      _desc  = this.state.welcome.desc;
      _article = <ReadContent title={_title} desc={_desc}></ReadContent>

    }else if(this.state.mode === 'read'){
      var data = this.getReadContent();
      _article = <ReadContent title={data.title} desc={data.desc}></ReadContent>

    }else if(this.state.mode === 'create'){
      _article = <CreateContent onChangePage={function(_title,_desc){
        this.max_content_id++;

        // this.state.contents.push( 이렇게 하면 안된다.
        //   {id:this.max_content_id, title:_title, desc:_desc,} );
        // 렌더링하려면 contents를 원본을 수정하지않고 복사해야한다. 방법이 3가지 있다.

        // 1방법 스프레드 문법
        var _contents = [...this.state.contents];
        _contents.push({id:this.max_content_id, title:_title, desc:_desc,});
        // 2방법 concat() 이용
        // var _contents = this.state.contents.concat({id:this.max_content_id, title:_title, desc:_desc,});
        // 3방법 Array.from() 이용
        // var _contents = Array.from(this.state.contents);
        // _contents.push({id:this.max_content_id, title:_title, desc:_desc,});

        this.setState({
          contents : _contents,
          selected_content_id : this.max_content_id,
        });
      }.bind(this)}></CreateContent>

    }else if(this.state.mode === 'update'){
      if(this.state.selected_content_id === 0){
        alert('a');
        debugger;
        return;
      }
      var _content = this.getReadContent();
      _article = <UpdateContent data={_content} onChangePage={function(_id,_title,_desc){
        // id가 _id인 contents안의 객체값을 교체 {id:_id, title:_title, desc:_desc}
        var _contents = Array.from(this.state.contents);
        for(var idx=0; idx<_contents.length; idx++){
          if(_contents[idx].id === _id){
            // _contents[idx] = {id:_id, title:_title, desc:_desc};
            _contents[idx].title = _title;
            _contents[idx].desc = _desc;
            break;
          }
        } // for _contents 교체완료
        this.setState({
          contents : _contents,
          mode : 'read',
        });
        // mode를 read로
      }.bind(this)}></UpdateContent>

    }// if(mode)
    return _article;
  } // getContent()

  render(){
    return(
      <div>
        <Subject title={this.state.subject.title} 
                  sub={this.state.subject.sub} 
                  onChangePage={function(){
                    this.setState({
                      mode : 'welcome',
                    });
                  }.bind(this)}>
        </Subject>
        <TOC data={this.state.contents} 
              onChangePage={function(id){
                this.setState({
                  mode : 'read',
                  selected_content_id : Number(id),
                });
              }.bind(this)}>
        </TOC>
        <Control onChangePage={function(_mode){
          if(_mode === 'delete'){
            if(window.confirm('삭제하시면 복구 불가합니다. 삭제 진행할까요?')){
            // this.state.selected_content_id 가 id인 contents안의 객체를 제거
              var _contents = [...this.state.contents];
              for(var idx=0; idx<_contents.length; idx++){
                if(_contents[idx].id === this.state.selected_content_id){
                  _contents.splice(idx,1); // idx번째 1개 삭제
                  // this.state.selected_content_id를 다른 id로 하기 위한 정렬 작업
                  var first; // id기준 오름차순 정렬후 첫번째 객체의 id
                  if(_contents.length>0){
                    first = Array.from(_contents).sort(function(left,right){
                      return left.id - right.id;  // 오름차순 정렬
                    }).slice(0,1)[0].id;
                  }else{
                    first = 0;
                  }
                  // mode를 welcome이나 read로 변경
                  // this.state.selected_content_id를 다른 id로
                  this.setState({
                    contents : _contents,
                    mode : 'welcome',
                    selected_content_id : first,
                  });
                  break;
                }// id값 찾은 후 제거 실행한 if문
              }// id값 확인하는 for문
            }// if confirm
          }else{
            this.setState({
              mode : _mode,
            });
          }
        }.bind(this)}>
        </Control>
        {this.getContent()}
      </div>
    ); // return
  } // render
} // App

export default App;