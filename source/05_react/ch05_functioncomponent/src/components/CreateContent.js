const CreateContent = props => {
  return(
    <article>
      <h2>CREATE</h2>
      <form 
        action="/create_process"
        onSubmit={(event)=>{
          event.preventDefault();
          // console.log(event.target);
          // debugger;
          props.onCreate(event.target.title.value,event.target.body.value);
          event.target.title.value = '';
          event.target.body.value = '';
        }}
      >
        <p>
          <input type="text" name="title" placeholder="title"/>
        </p>
        <p>
          <textarea name="body" placeholder="body"></textarea>
        </p>
        <p>
          <input type="submit" value="추가"/>
        </p>
      </form>
    </article>
  );
};
export default CreateContent;