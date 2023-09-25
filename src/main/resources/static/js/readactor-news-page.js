const editableDiv  = document.getElementById("text-input");
const contentInput = document.getElementById("hidden-input-content");
const modalTemp = document.getElementById("modal-temp");
const addTable = document.getElementById("addTable");
const span = document.getElementsByClassName("close")[0];
const modalContent = document.querySelector('.main-content');
const tableList = [];


function insertSource(source) {
    editableDiv.innerHTML = source.value;
}
insertSource(contentInput);
const currentPath = window.location.pathname;

if (currentPath.startsWith('/news/edit/')) {
    const variableValue = currentPath.replace('/news/edit/', '');

    insertSource(contentInput);

    console.log("Значение переменной:", variableValue);
}

// Modal window
span.onclick = function() {
    modalTemp.style.display = "none";
    const modalContent = document.querySelector('.main-content');
    const controllerPanel = document.querySelector('#controller-panel');
    while (controllerPanel.firstChild) {
        controllerPanel.removeChild(controllerPanel.firstChild);
    }
    while (modalContent.firstChild) {
        modalContent.removeChild(modalContent.firstChild);
    }
}

window.onclick = function(event) {
    if (event.target === modalTemp) {
        modalTemp.style.display = "none";
        const modalContent = document.querySelector('.main-content');
        const controllerPanel = document.querySelector('#controller-panel');
        while (controllerPanel.firstChild) {
            controllerPanel.removeChild(controllerPanel.firstChild);
        }
        while (modalContent.firstChild) {
            modalContent.removeChild(modalContent.firstChild);
        }
    }
}

addTable.onclick = function (){

    const label = document.createElement('label');
    label.textContent = 'Количество колонок:';
    label.setAttribute('for', 'columns-count');
    label.className = "label-modal ";

    const input = document.createElement('input');
    input.type = 'number';
    input.id = 'columns-count';
    input.name = 'columns';
    input.min = '1';
    input.className = "input-number-modal";

    const button = document.createElement('button');
    button.textContent = 'Создать таблицу';
    button.onclick = addNewTable;
    button.className = "button-modal";

    modalContent.appendChild(label);
    modalContent.appendChild(input);
    modalContent.appendChild(button);
    modalTemp.style.display = "block";
}

function updateTable(id){
    function getParPlus(line){
        let matches = line.match(/\d+/g);
        if (matches.length === 3){
            let num_1 = matches[0];
            let num_2 = matches[1];
            return  num_1.toString() + (num_2 + 1).toString();
        }
        else{
            return  parseInt(matches[0]) + 1;
        }
    }

    function getParMin(line){
        let matches = line.match(/\d+/g);
        if (matches.length === 3){
            let num_1 = matches[0];
            let num_2 = matches[1];
            return num_1.toString() + (num_2 - 1).toString();
        }
        else{
            return   parseInt(matches[0]) - 1;
        }
    }


    const controllerPanel = document.getElementById('controller-panel');
    let global_table = document.getElementById(id);
    let local_table  = global_table.cloneNode(true);

    const action = document.createElement("button");
    action.textContent = "Add Column";
    action.className = "button-modal"
    action.onclick = function (){
        let value = local_table.style.gridTemplateColumns;

        global_table.style.gridTemplateColumns = "repeat(" + getParPlus(value) + ", 1fr)";
        local_table.style.gridTemplateColumns = "repeat(" + getParPlus(value) + ", 1fr)";
    };

    const actionMin = document.createElement("button");
    actionMin.textContent = "Remove Column";
    actionMin.className = "button-modal"
    actionMin.onclick = function (){
        let value = local_table.style.gridTemplateColumns;

        global_table.style.gridTemplateColumns = "repeat(" + getParMin(value) + ", 1fr)";
        local_table.style.gridTemplateColumns = "repeat(" + getParMin(value) + ", 1fr)";
    };

    const h3 = document.createElement('h3');
    h3.textContent = 'Редактор таблиці:';
    h3.className = "label-modal";

    const block = document.createElement("div");
    block.style.background = "white";
    block.appendChild(local_table);
    modalContent.appendChild(h3);
    modalContent.appendChild(block);
    controllerPanel.appendChild(actionMin);
    controllerPanel.appendChild(action);

    modalTemp.style.display = "block";
}

// ----------------------------------


function showResult(){
    let content = editableDiv.innerHTML;

    let newWindow = window.open('', '_blank');

    newWindow.document.write('<html><head><title>Новая Страница</title> ' +
        '<link rel="stylesheet" href="http://localhost:9999/css/news-review-info.css">' +
        '</head><body>');
    newWindow.document.write('<div class="container">' + content + '</div>');
    newWindow.document.write('</body></html>');
    newWindow.document.close();
}


function addNewTable(){

    let columns = document.querySelector("#columns-count");
    if(columns.value !== 0) {
        let name;
        if (tableList.length === 0) {
            name = "0_table";
            tableList.push(name);
        } else {
            name = tableList.length + "_table"
            tableList.push(name);
        }
        const gridTbl = document.createElement("div");

        gridTbl.className = "table-container";
        gridTbl.style.gridTemplateColumns = "repeat(" + columns.value + ", 1fr)";
        for (let j = 0; j < columns.value; j++) {
            let gridItem = document.createElement("div");
            gridItem.className = "table-cell";
            gridTbl.appendChild(gridItem);
        }

        gridTbl.addEventListener("dblclick", function () {
            updateTable(name);
        });
        gridTbl.id = name;
        editableDiv.appendChild(gridTbl);
        editableDiv.appendChild(document.createElement("br"));
    }
}


function createSpace(){
    editableDiv.appendChild(document.createElement("br"));
}

editableDiv.addEventListener('input', function (){
    contentInput.value = editableDiv.innerHTML;
})