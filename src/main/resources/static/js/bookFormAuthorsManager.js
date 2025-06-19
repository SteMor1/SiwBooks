const selectedAuthorsContainer = document.getElementById('selectedAuthors');
const form = document.querySelector('form');
const selectedAuthors = new Map();
const availableAuthors = document.querySelectorAll('.add-author-btn')
const noAuthorsAv = document.getElementById('noAuthorsAvailableMsg')
const noAuthorsSelected = document.getElementById('noAuthorsSelectedMsg') //RIFERIRIMENTO AD UN ELEMENTO PRESENTE NEL FRAGMENT
window.addEventListener('DOMContentLoaded', () => { //AL CARICAMENTO VERIFICO CHE MESSAGGI DEVO MOSTRARE (NON HO POTUTO USARE THYMELEAF)
    updateSelectedAuthors();
});
// Aggiungi listener a tutti i bottoni add-author-btn
document.querySelectorAll('.add-author-btn').forEach(button => {
    button.addEventListener('click', () => {
        const authorId = button.getAttribute('data-author-id');
        const authorName = button.getAttribute('data-author-name');

        if (!selectedAuthors.has(authorId)) {
            selectedAuthors.set(authorId, authorName);
            updateSelectedAuthors();
            // 🔽 Nascondi tutta la card dell'autore
            const card = document.querySelector(`.author-card[data-author-id="${authorId}"]`);
            if (card) {
                card.style.display = 'none';
            }
        }
    });
})

function updateSelectedAuthors() {
    selectedAuthorsContainer.innerHTML = '';

    // Rimuove gli input nascosti precedenti
    form.querySelectorAll('input[name^="authors["]').forEach(input => input.remove());

    let index = 0;
    selectedAuthors.forEach((name, id) => {
        // Mostra autore selezionato come badge
        const badge = document.createElement('span');
        badge.className = 'badge bg-primary d-flex align-items-center gap-2';
        badge.style.userSelect = 'none';
        badge.textContent = name;

        // Bottone per rimuovere autore
        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'btn-close btn-close-white btn-sm';
        removeBtn.setAttribute('aria-label', 'Remove');
        removeBtn.onclick = () => {
            selectedAuthors.delete(id);
            showCard(id);
            updateSelectedAuthors();
        };

        badge.appendChild(removeBtn);
        selectedAuthorsContainer.appendChild(badge);

        // Crea input hidden: authors[0].id, authors[1].id, ...
        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = `authors[${index}].id`;
        hiddenInput.value = id;
        form.appendChild(hiddenInput);

        index++;
    });
    if (index === availableAuthors.length) {
        noAuthorsAv.style.display = ''; //MOSTRO SE NON CI SONO PIU' AUTORI DA AGGIUNGERE
    } else {
        noAuthorsAv.style.display = 'none'; //NASCONDO SE CI SONO ALTRI AUTORI DA AGGIUNGE
    }
    if (index === 0) {
        noAuthorsSelected.style.display = '';//MOSTRO SE NON E' STATO SELEZIONATO ALMENO UN AUTORE

    } else {
        noAuthorsSelected.style.display = 'none'; //ALTIMENTI NASCONDO SE E' STATO SELEZIONATO ALMENO UN AUTORE
    }

}

function showCard(id) {
    const card = document.querySelector(`.author-card[data-author-id="${id}"]`);
    card.style.display = '';
}

