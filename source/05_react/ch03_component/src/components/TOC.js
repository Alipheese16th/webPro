import React,{Component} from 'react';
class TOC extends Component{
  render(){
    var lists = [];
      // <li><a href="1.html">HTML</a></li>, 형태의 배열로 lists
      // <li><a href="2.html">CSS</a></li>,
      // <li><a href="3.html">JavaScript</a></li>
    var data = this.props.data;
    for(var i=0; i<data.length;i++){
      lists.push(
        <li key={data[i].id}><a href={data[i].id+".html"}>{data[i].title}</a></li>
      );
    }

    return(
      <nav>
        <ul>
          {lists}
        </ul>
      </nav>
    );
  }
}
export default TOC;