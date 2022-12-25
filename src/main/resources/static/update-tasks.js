window.onload = async function() {
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
            append_select(task);
        }
    }
}

function append_select(task) {
    let select = document.getElementById("tasks-list");
    let option = document.createElement("option");

    option.innerText = `[${task.time}] <${task.name}>: ${task.description}`;
    option.value = task.id;

    select.appendChild(option);
}

async function choose_select(id) {
    const response = await fetch(`/api/tasks/${id.value}`, {
        method: "GET",
        headers: { "Accept": "application/json", "Content-Type": "application/json" }
    });

    console.log(response);
    console.log(response.ok);
    if (response.ok === true) {
        let task = await response.json();
        console.log(task);
        document.getElementById('id').value = task.id;
        document.getElementById('name').value = task.name;
        document.getElementById('description').value = task.description;
        document.getElementById('time').value = task.time;
    }
}