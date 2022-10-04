import React from "react";
import { atom, selector, useRecoilValue, useSetRecoilState } from "recoil";

const ANIMALS = {
  CAT: "cat",
  DOG: "dog",
  ALL: "all",
};

const animalsState = atom({
  key: "animalsState",
  default: [
    {
      id: 1,
      name: "홍길동",
      type: ANIMALS.DOG,
    },
    {
      id: 2,
      name: "아무개",
      type: ANIMALS.CAT,
    },
    {
      id: 3,
      name: "고길동",
      type: ANIMALS.CAT,
    },
  ],
});

const icon = (type) => {
  if (type === ANIMALS.DOG) return "🐶";
  if (type === ANIMALS.CAT) return "🐱";
};

const animalFilterState = atom({
  key: "animalFilterState",
  default: ANIMALS.ALL,
});

const filteredAnimalsState = selector({
  key: "animalListState",
  get: ({ get }) => {
    const filter = get(animalFilterState);
    const animals = get(animalsState);
    if (filter === ANIMALS.ALL) return animals;
    return animals.filter((animal) => animal.type === filter);
  },
});

export default function App() {
  const animals = useRecoilValue(filteredAnimalsState);
  const setAnimalFilter = useSetRecoilState(animalFilterState);
  return (
    <div>
      <div className="buttons">
        <button onClick={() => setAnimalFilter(ANIMALS.ALL)}>All</button>
        <button onClick={() => setAnimalFilter(ANIMALS.DOG)}>Dogs</button>
        <button onClick={() => setAnimalFilter(ANIMALS.CAT)}>Cats</button>
      </div>
      <div>
        <h1>Animals:</h1>
        {animals.map((animal) => (
          <div key={animal.id}>
            {animal.name}, {animal.type} {icon(animal.type)}
          </div>
        ))}
      </div>
    </div>
  );
}
