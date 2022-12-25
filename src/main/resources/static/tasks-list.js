window.onload = async function () {
    const response = await fetch("/api/tasks", {
            method: "GET",
            headers: { "Accept": "application/json", "Content-Type": "application/json" }
    });

    console.log(response);
    console.log(response.ok);
    if (response.ok === true) {
        let tasks = await response.json();
        console.log(tasks);

        for (let task of tasks) {
            append_table_list(task);
        }
    }
}

function append_table_list(task) {
        let table = document.getElementById("tasks");
        let row = document.createElement("tr");

        let name = document.createElement("td");
        name.innerHTML += `${task.name}`;
        row.appendChild(name);

        let desc = document.createElement("td");
        desc.innerHTML += `${task.description}`;
        row.appendChild(desc);

        let time = document.createElement("td");
        time.innerHTML += `${task.time}`;
        row.appendChild(time);

        table.appendChild(row);
}