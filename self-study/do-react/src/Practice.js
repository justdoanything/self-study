import { useState, useEffect } from "react";

function Practice() {
    const [toDo, setToDo] = useState("");
    const [toDos, setToDos] = useState([]);
    const onChange = (event) => setToDo(event.target.value);
    const onSubmit = (event) => {
        event.preventDefault();
        if (toDo === "") {
            return;
        }
        setToDos((currentArray) => [toDo, ...currentArray]);
        setToDo("");
        console.log(toDos);
    };

    return (
        <div>
            <h1>My To Dos ({toDos.length})</h1>
            <form onSubmit={onSubmit}>
                <input
                    type="text"
                    placeholder="Write your todo..."
                    onChange={onChange}
                    value={toDo}
                />
                <button>Add To Do</button>
            </form>
            <hr />
            {toDos}<br />
            {toDos.map(() => ":)")}<br />
            {toDos.map((item) => item.toUpperCase())}<br />
            {toDos.map((item, index) => (
                <li key={index}>{item}</li>
            ))}
            <br />

        </div>
    );
}

export default Practice;
