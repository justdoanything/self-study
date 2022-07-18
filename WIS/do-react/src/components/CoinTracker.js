import { useState, useEffect } from "react";

function CoinTracker() {
    const [loading, setLoading] = useState(true);
    const [coins, setCoins] = useState([]);
    useEffect(() => {
        fetch("https://api.coinpaprika.com/v1/tickers")
            .then((response) => response.json())
            .then((json) => {
                setCoins(json);
                setLoading(false);
            }
            );
    }, []);
    return (
        <div>
            <h1>The Coins! {loading ? "" : `(${coins.length})`}</h1>
            {loading ? <strong>Loading...<br /></strong> : <select>
                {coins.map((coin, index) => (
                    <option key={coin.id}>
                        {coin.name} ({coin.symbol}) : {coin.quotes.USD.price} USD
                    </option>))}
            </select>}

            {/* <ul>
                {coins.map((coin, index) => <li key={coin.id}> {coin.name} ({coin.symbol}) : {coin.quotes.USD.price} USD</li>)}
            </ul> */}
        </div>

    )

}

export default CoinTracker;
