function windowpopup(url)
{
 window.open(url,"window","left=50,top=50,menubar=no,toolbar=yes,address=no,status=yes,scrollbars=yes,resizable=yes,width=850,height=600");
}
function changeTree(e){
  ns6_index = 0
  if (!document.all && !document.getElementById) {
    return
  }
  if (!document.all && document.getElementById) {
    ns6_index=1
  }
  var source = document.getElementById && !document.all? e.target: event.srcElement
  if (source.className == "folding") {
    var source2 = document.getElementById && !document.all? source.parentNode.childNodes: source.parentElement.all
    if (source2[2+ns6_index].style.display == "none") {
      source2[0].src = "../images/tree_off.jpg"
      source2[2+ns6_index].style.display = ''
    } else {
      source2[0].src = "../images/tree_on.jpg"
      source2[2+ns6_index].style.display = "none"
    }
  }
}
document.onclick = changeTree