import './App.css';
import { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Array from "./components/Array";
import Cleanup from "./components/Cleanup";
import CoinTracker from "./components/CoinTracker";
import Counter from "./components/Counter";
import Movie from "./components/Movie";
import Prop from "./components/Prop";
import UseEffect from "./components/UseEffect";

const OPTIONS = [
  { value: "hello", name: "hello" },
  { value: "array", name: "array" },
  { value: "cleanup", name: "cleanup" },
  { value: "cointracker", name: "cointracker" },
  { value: "counter", name: "counter" },
  { value: "movie", name: "movie" },
  { value: "prop", name: "prop" },
  { value: "useEffect", name: "useEffect" },
];

const SelectBox = (props) => {
  return (
    <select
      onChange={props.onChange}
      defaultValue={props.default}
    >
      {props.options.map((option) => (
        <option
          key={option.value}
          value={option.value}
        >
          {option.name}
        </option>
      ))
      }
    </select >
  );
};

function App() {
  var defaultValue = "counter";
  const [optionValue, setOptionValue] = useState(defaultValue);

  const onChangeSelectBox = (event) => {
    setOptionValue(event.target.value);
  }

  return <div>
    <SelectBox options={OPTIONS} default={defaultValue} onChange={onChangeSelectBox}></SelectBox>
    {optionValue === "hello" ? <App /> : null}
    {optionValue === "array" ? <Array /> : null}
    {optionValue === "cleanup" ? <Cleanup /> : null}
    {optionValue === "cointracker" ? <CoinTracker /> : null}
    {optionValue === "counter" ? <Counter /> : null}
    {optionValue === "movie" ? <Movie /> : null}
    {optionValue === "prop" ? <Prop /> : null}
    {optionValue === "useEffect" ? <UseEffect /> : null}

  </div>;
}

function AppRouter() {
  return <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />} />
      <Route path="/array" element={<Array />} />
      <Route path="/cleanup" element={<Cleanup />} />
      <Route path="/cointracker" element={<CoinTracker />} />
      <Route path="/counter" element={<Counter />} />
      <Route path="/movie" element={<Movie />} />
      <Route path="/prop" element={<Prop />} />
      <Route path="/useEffect" element={<UseEffect />} />
    </Routes>
  </BrowserRouter >
}
export default AppRouter;