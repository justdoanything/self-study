window.onload = () => {
  var id = location.search.split("=")[1];

  fetch("http://localhost:3300/v1/chart/detail/" + id)
    .then((response) => response.json())
    .then((data) => {
        console.log(data);
      var chart = data.chart;
      console.log(chart);
      document.getElementById("title").innerHTML = chart.title;
      document.getElementById("singer").innerHTML = chart.singer;

      var tableTag = "<table/>";
      tableTag +=
        '<tr>' + 
            '<td style="font-weight: bold; text-align: right">작사</td>' +
            '<td style="text-align: left">' + chart.lyricist +'</td>' +
        '</tr>' +
        '<tr>' + 
            '<td style="font-weight: bold; text-align: right">작곡</td>' + 
            '<td style="text-align: left">' + chart.melodizer + '</td>' +
        '</tr>' + 
        '<tr>' +
            '<td style="font-weight: bold; text-align: right">장르</td>' +
            '<td style="text-align: left">' + chart.genre + '</td>' +
        '</tr></table>';
      document.getElementById("songDetail").innerHTML = tableTag;
    });
};

function goBack() {
  window.history.back();
}
