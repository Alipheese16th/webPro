import {useState} from 'react';

const UpdateContent = props => {
  const [title,setTitle] = useState(props.data.title);
  const [body,setBody] = useState(props.data.body);
  return (
    <article>
      <h2>UPDATE</h2>
      <form onSubmit={e=>{
        e.preventDefault();
        props.onUpdate(title,body);
      }}>
        <p>
          <input 
            type="text" 
            name="title" 
            placeholder="title"
            value={title}
            onChange={e=>{
              // console.log(e.target.value);
              setTitle(e.target.value);
            }}
          />
        </p>
        <p>
          <textarea 
            name="body" 
            placeholder="body"
            value={body}
            onChange={e=>{
              setBody(e.target.value);
            }}
          ></textarea>
        </p>
        <p>
          <input type="submit" value="수정"/>
        </p>
      </form>
    </article>
  );
};

export default UpdateContent;
