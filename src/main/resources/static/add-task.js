 window.onload = async function () {
     let elem = document.getElementById("button_add");

     elem.onclick = async function () {
         await fetch("/api/tasks", {
             method: "POST",
             headers: {"Accept": "application/json", "Content-Type": "application/json"},
             body: JSON.stringify({
                 "name": document.getElementById("name").value,
                 "description": document.getElementById("description").value,
                 "time": document.getElementById("time").value
             })
         });
     }
 };