function toggleMenu(button) {
    const menu = button.nextElementSibling;
    menu.classList.toggle("hidden");

    // Chiude gli altri menu aperti
    document.querySelectorAll('.menu-dropdown').forEach(dropdown => {
        if (dropdown !== menu) dropdown.classList.add('hidden');
    });

    // Chiudi il menu cliccando fuori
    document.addEventListener('click', function handler(e) {
        if (!button.parentElement.contains(e.target)) {
            menu.classList.add('hidden');
            document.removeEventListener('click', handler);
        }
    });
}

