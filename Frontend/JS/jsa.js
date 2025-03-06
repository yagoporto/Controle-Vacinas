document.addEventListener("DOMContentLoaded", function() {
    const modal = document.getElementById("loginModal");
    const errorMessage = document.getElementById("error-message");

    window.openModal = function() {
        modal.style.display = "flex";
    }

    window.closeModal = function() {
        modal.style.display = "none";
    }

    window.showError = function() {
        errorMessage.style.display = "block";
    }

    // Fecha o modal ao clicar fora dele
    window.onclick = function(event) {
        if (event.target === modal) {
            closeModal();
        }
    }
});
