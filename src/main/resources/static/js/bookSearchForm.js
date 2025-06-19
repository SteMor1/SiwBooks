document.addEventListener("DOMContentLoaded", function () {
    console.log("Script loaded"); // Debug
    const form = document.getElementById("searchForm");
    console.log("Form found:", form); // Debug

    if (!form) {
        console.error("Form not found!");
        return;
    }

    form.addEventListener("submit", function (e) {
        console.log("Form submitted"); // Debug
        e.preventDefault(); // Previeni l'invio normale

        // Crea FormData e filtra i campi vuoti
        const formData = new FormData(form);
        const params = new URLSearchParams();

        // Aggiungi solo i campi non vuoti
        for (let [key, value] of formData.entries()) {
            if (value.trim() !== '') {
                params.append(key, value);
            }
        }

        // Costruisci l'URL pulito
        const currentUrl = window.location.pathname;
        const newUrl = params.toString() ? `${currentUrl}?${params.toString()}` : currentUrl;

        window.location.href = newUrl;
    });
});
