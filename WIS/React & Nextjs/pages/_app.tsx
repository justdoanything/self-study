import { RecoilRoot } from "recoil";
import "../styles/globals.css";

function App({ Component, pageProps }) {
  return (
    <RecoilRoot>
      <Component {...pageProps} />
    </RecoilRoot>
  );
}

export default App;
