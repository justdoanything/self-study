import './css/App.css';
import axios from 'axios';
import {useEffect, useState} from "react";
import moment from "moment";

function App() {

    const [userData, setUserData] = useState({});
    const [selected, setSelected] = useState(1);
    const getBackendData = (userId) => {
        setSelected(userId);
        axios.get(`https://localhost:8080/api/v1/user/${userId}`)
            .then(response => {
                if (response.data) {
                    setUserData(response.data);
                } else {
                    setUserData({});
                }
            });
    }

    const convertDatetime = (datetime) => {
        return moment(datetime).format('YY-MM-DD HH:mm:ss');
    }

    useEffect(() => {
        getBackendData(1);
    },[])


    return (
        <div className="App">
            <div>
                <h1>Title</h1>
            </div>
            <hr/>

            <div>
                <button style={selected === 1 ? {color: 'red'} : {color: 'blue'}} onClick={() => getBackendData(1)}>Get User 1
                </button>
                <button style={selected === 2 ? {color: 'red'} : {color: 'blue'}} onClick={() => getBackendData(2)}>Get User 2
                </button>
                <button style={selected === 3 ? {color: 'red'} : {color: 'blue'}} onClick={() => getBackendData(3)}>Get User 3
                </button>
            </div>
            <hr/>

            <div>
                {convertDatetime('2023-01-01 00:00:00')}
                <br/>
                {(0.0).toFixed(1)}
                <br/>
                {(1.1).toFixed(2)}
                <br/>
                {(2.2).toFixed(3)}
            </div>
            <hr/>

            <div>
                {userData && userData.length > 0 ? (
                    userData.sort((a, b) => a.rate - b.rate).map((item, index) => (
                        <div key={index}>
                            <div>{item.name}</div>
                            {item.rate > 0 ? (
                                <div>rate가 0 초과입니다.</div>
                            ) : (
                                <div>rate가 0 이하입니다.</div>
                            )}
                        </div>
                    ))
                ) : (
                    <div>데이터가 없습니다.</div>
                )}
            </div>
        </div>
    );
}

export default App;
