import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Practice from './Practice';
import CoinTracker from './CoinTracker';
import Movie from './Movie';

ReactDOM.render(
  <React.StrictMode>
    {/* <App /> */}
    {/* <Practice /> */}
    {/* <CoinTracker /> */}
    <Movie />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
