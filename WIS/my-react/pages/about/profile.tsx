import Link from "next/link";
import { useRecoilState } from "recoil";
import { nameState } from "../../state/state";
import React, { useEffect } from "react";

const About = ({ json }) => {
  const [name, setNameState] = useRecoilState(nameState);

  const updateName = (e) => {
    setNameState(e.target.value);
  };

  useEffect(() => {
    setNameState(json.name);
  });

  return (
    <div>
      <h1>{json.name}&apos;s Profile</h1>
      <p>Hello, {name} </p>

      <input
        type="text"
        name="name"
        id="name"
        onChange={updateName}
        placeholder="your name"
      />
    </div>
  );
};

export function getStaticProps() {
  const json = { name: "static props" };
  return {
    props: { json },
  };
}

export default About;
