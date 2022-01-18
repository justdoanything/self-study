import { useState, useEffect } from "react";

function Movie() {
    const [loading, setLoading] = useState(true);
    const [movies, setMovies] = useState([]);
    const getMovies = async () => {
        // const response = await fetch(`https://yts.mx/api/v2/list_movies.json?minimum_rating=9.0%sort=year`);
        // const json = await response.json();
        const json = await (
            await fetch(`https://yts.mx/api/v2/list_movies.json?minimum_rating=8.8&sort_by=year`)
        ).json();
        setMovies(json.data.movies);
        setLoading(false);
    }

    useEffect(() => {
        getMovies();
    }, []);

    // useEffect(() => {
    //     fetch(`https://yts.mx/api/v2/list_movies.json?minimum_rating=9.0%sort=year`)
    //         .then((response) => response.json())
    //         .then((json) => {
    //             setMovies(json.data.movies);
    //             setLoading(false);
    //         })
    // }, []);
    console.log(movies);
    return <div>
        {loading ? <h1> Loading...</h1> : null}

    </div>;
}

export default Movie;
