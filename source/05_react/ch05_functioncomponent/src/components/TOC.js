
const TOC = props => {

  let lists = [];

  for(let i=0;i<props.data.length;i++){
    let data = props.data[i];
    lists.push(
      <li key={data.id}>
        <a 
          href={data.id+'.html'} 
          id={data.id}
          onClick={(event)=>{
            event.preventDefault();
            // console.log(event.target); // event.target : 이벤트가 발생된 태그 객체
            // debugger;
            props.onChangeMode(Number(event.target.id));
          }}
        >
          {data.title}
        </a>
      </li>
    );
  }

  return (
    <nav>
      <ol>
        {lists}
      </ol>
    </nav>
  );
};

export default TOC;
