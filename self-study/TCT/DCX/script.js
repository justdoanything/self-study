window.onload = () => {
  let today = new Date();

  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1; // 월
  let date = today.getDate(); // 날짜
  let day = today.getDay(); // 요일
  let hours = today.getHours(); // 시
  let minutes = today.getMinutes(); // 분

  document.getElementById("currentTime").innerHTML =
    year + "년 " + month + "월 " + day + "일 " + hours + ":" + minutes;

  clickLocation("kor");
};

function clickLocation(lang) {
  if (lang == "kor") {
    document.getElementById("kor").style.color = "red";
    document.getElementById("eng").style.color = "black";

    fetch("http://localhost:3300/v1/chart/domestic")
      .then((response) => response.json())
      .then((data) => console.log(data));
  } else if (lang == "eng") {
    document.getElementById("kor").style.color = "black";
    document.getElementById("eng").style.color = "red";

    fetch("http://localhost:3300/v1/chart/overseas")
      .then((response) => response.json())
      .then((data) => console.log(data));
  } else {
  }
}
