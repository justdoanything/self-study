import './App.css';
import { useState, useEffect } from "react";

const OPTIONS = [
  { value: "hello", name: "hello" },
  { value: "counter", name: "counter" },
  { value: "prop", name: "prop" },
  { value: "useEffect", name: "useEffect" },
  { value: "cleanup", name: "cleanup" },
  { value: "array", name: "array" }
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
      ))}
    </select>
  );
};

const HelloComponent = () => {
  return <div>HelloComponent</div>;
}

const CounterComponent = () => {
  return <div>CounterComponent</div>;
}

const PropComponent = () => {
  return <div>PropComponent</div>;
}

const UseEffectComponent = () => {
  return <div>UseEffectComponent</div>;
}

const CleanupComponent = () => {
  return <div>CleanupComponent</div>;
}

const ArrayComponent = () => {
  return <div>ArrayComponent</div>;
}

function App() {
  var defaultValue = "counter";
  const [optionValue, setOptionValue] = useState(defaultValue);

  const onChangeSelectBox = (event) => {
    setOptionValue(event.target.value);
  }

  return <div>
    <SelectBox options={OPTIONS} default={defaultValue} onChange={onChangeSelectBox}></SelectBox>
    {optionValue === "hello" ? <HelloComponent /> : null}
    {optionValue === "counter" ? <CounterComponent /> : null}
    {optionValue === "prop" ? <PropComponent /> : null}
    {optionValue === "useEffect" ? <UseEffectComponent /> : null}
    {optionValue === "cleanup" ? <CleanupComponent /> : null}
    {optionValue === "array" ? <ArrayComponent /> : null}
  </div>;
}

export default App;
