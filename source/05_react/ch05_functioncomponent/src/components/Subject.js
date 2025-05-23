import './Subject.css';

const Subject = props => {  // props
  // props.title = '수정 불가. ReadOnly';
  return (
    <header className="Subject">
      <h1 onClick={() => {
        props.onChangeMode();
      }}>
        {props.title}
      </h1>
      {props.sub}
    </header>
  );
};
export default Subject;