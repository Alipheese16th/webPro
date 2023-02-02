import React,{Component} from 'react';
import './Subject.css';
class Subject extends Component{
  shouldComponentUpdate(newProps){
    if(newProps.data === this.props.data){
      return false;
    }
    return true;
  }
  render(){
    return(
      <header className='subject_h'>
        <h1>
          <a href="/" 
              onClick={function(event){
                event.preventDefault();
                this.props.onChangePage();
              }.bind(this)}>
            {this.props.title}
          </a>
        </h1>
        {this.props.sub}
      </header>
    );
  }
}
export default Subject;