const table_news = document.querySelector('.table-light');
const modalTemp = document.getElementById("modal-temp");
const controllerPanel = document.getElementById('controller-panel');
const modalContent = document.querySelector('.main-content');
const span = document.getElementsByClassName("close")[0];

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




function deleteNewsConfirming(id){
    axios.post('/news/delete-new-confirming?id='+ id)
        .then(response => {
            if(response.data()){
                const elementToDelete = document.getElementById(id);
                if(elementToDelete){
                    elementToDelete.remove();
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

                console.log('Delete successful:', response.data);
            }
            else{

            }

        })
        .catch(error => {
            // Handle any errors that occur during the request
            console.info('lox');
            console.error('Error:', error);
        });
}

function openModalRemove(button){
    const id = button.getAttribute('data-news-id');
    const action = document.createElement("button");
    action.textContent = "Add Column";
    action.className = "button-modal"
    action.onclick = function (){
      deleteNewsConfirming(id);
    };

    controllerPanel.appendChild(action);
    modalTemp.appendChild(controllerPanel);
    modalTemp.style.display = 'block';

}


// Modal window
