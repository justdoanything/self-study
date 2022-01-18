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
            {toDos}
        </div>
    );
}

export default Practice;
