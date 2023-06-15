$("#call_bard").click(function(){
    const mask = document.getElementById("mask");
    mask.style.display = "block";
    setTimeout(function(){
        mask.style.display = "none";
    })
}
//    $.ajax({
//        type : "POST",
//        url : "{BARD_API}",
//        success : function(res)
//}