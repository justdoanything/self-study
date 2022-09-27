import { atom } from 'recoil';

const nameState = atom({
  key: 'nameState',
  default: "yongwoo"
});

export {nameState}