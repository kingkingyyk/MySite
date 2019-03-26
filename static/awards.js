function loadUVAPercentile() {
    var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            var badge = document.getElementById("uva_percentile");
            if (this.readyState == 4) {
                if (this.status == 200) {
                    badge.className = badge.className + " blue";
                    badge.innerHTML = this.responseText;
                } else {
                    badge.className = badge.className + " red";
                    badge.innerHTML = "ERROR LOADING";
                }
            }
        };
        xhttp.open("GET", "uva-percentile", true);
    xhttp.send();
}

function loadUVARankList() {
    var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            var card = document.getElementById("uva_rank_list");
            if (this.readyState == 4) {
                if (this.status == 200) {
                    card.innerHTML = this.responseText;
                } else {
                    card.innerHTML = "<i class=\"tiny material-icons\">error</i> "+
                                      "We just encountered a problem while loading the rank list.</br></br>"+
                                      "<a class=\"waves-effect waves-light blue btn\" onclick=\"reloadUVARankList()\">Reload for me!</a>";
                }
            }
        };
        xhttp.open("GET", "uva-rank-list", true);
    xhttp.send();
}

function reloadUVARankList() {
    var card = document.getElementById("uva_rank_list");
    card.innerHTML = "<div class=\"progress\"><div class=\"indeterminate\" style=\"background-color: #2196F3;\"></div></div>";
    setTimeout(loadUVARankList, 1000);
}


loadUVAPercentile();
setTimeout(loadUVARankList, 1000);
