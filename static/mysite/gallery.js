var filters = ["#camera-filter","#lens-filter","#tag-filter"];
var filterParam = ["cameras","lenses","tags"]

function filterMapToString (map) {
    return Object.entries(map).map(pair => pair.map(encodeURIComponent).join('=')).join('&');
}

function loadPictures() {
    var map = {};
    for (i = 0; i < filters.length; i++) {
        var select = M.FormSelect.getInstance($(filters[i]));
        var filterList = select.getSelectedValues().join(",")
        map[filterParam[i]]=filterList;
    }
    var pictureGrid = document.getElementById("pictureGrid");
    var loader = document.getElementById("loader");
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            loader.style.visibility = "hidden";
            if (this.status == 200) {
                pictureGrid.innerHTML = this.responseText;
            } else {
                pictureGrid.innerHTML = "Error!";
            }
        }
    };
    xhttp.open("GET", "query?"+filterMapToString(map), true);
    xhttp.send();
    pictureGrid.innerHTML = "";
    loader.style.visibility = "visible"
}

var i;
for (i = 0; i < filters.length; i++) {
    $(filters[i]).on('change', function() {
        loadPictures();
    });
}
setTimeout(loadPictures, 1000);