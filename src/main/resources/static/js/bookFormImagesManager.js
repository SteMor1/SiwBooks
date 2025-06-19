document.addEventListener("DOMContentLoaded", function () {
    const coverInput = document.getElementById("bookCover");
    const otherImagesInput = document.getElementById("otherImages");
    const addImgBtn = document.getElementById("add-img");
    const carouselInner = document.querySelector('#bookCarousel .carousel-inner');
    const carouselIndicators = document.querySelector('#bookCarousel .carousel-indicators');
    let filesList = [];

    function updateCarousel() {
        // ========== RESET COMPLETO DEL CAROSELLO ==========

        // Mantieni solo la slide della copertina originale
        const originalSlide = carouselInner.querySelector('.carousel-item:first-child');
        carouselInner.innerHTML = '';
        carouselInner.appendChild(originalSlide);

        // Assicurati che la prima slide sia attiva
        originalSlide.classList.add('active');

        // Reset indicatori - mantieni solo il primo
        carouselIndicators.innerHTML = `
                <button type="button" data-bs-target="#bookCarousel" data-bs-slide-to="0"
                        class="active" aria-current="true" aria-label="Copertina"></button>
            `;

        // Se non ci sono file, aggiungi una slide placeholder
        if (filesList.length === 0) {
            const placeholderSlide = document.createElement('div');
            placeholderSlide.className = 'carousel-item h-100';
            placeholderSlide.innerHTML = `
                    <div class="d-flex align-items-center justify-content-center h-100 bg-light rounded border border-dashed">
                        <div class="text-center text-muted">
                            <i class="fas fa-plus-circle fa-3x mb-2"></i>
                            <p class="mb-0">Aggiungi altre foto</p>
                        </div>
                    </div>
                `;
            carouselInner.appendChild(placeholderSlide);

            // Indicatore per placeholder
            const placeholderIndicator = document.createElement('button');
            placeholderIndicator.type = 'button';
            placeholderIndicator.setAttribute('data-bs-target', '#bookCarousel');
            placeholderIndicator.setAttribute('data-bs-slide-to', '1');
            placeholderIndicator.setAttribute('aria-label', 'Aggiungi foto');
            carouselIndicators.appendChild(placeholderIndicator);
            return;
        }

        // ========== AGGIUNTA DELLE NUOVE SLIDE ==========

        // Aggiungi le slide delle immagini selezionate
        filesList.forEach((file, index) => {
            const reader = new FileReader();
            reader.onload = e => {
                const slide = document.createElement('div');
                slide.className = 'carousel-item h-100';
                slide.innerHTML = `
                        <div class="position-relative h-100">
                            <img src="${e.target.result}"
                                 class="d-block w-100 h-100 img-thumbnail"
                                 style="object-fit: cover;"
                                 alt="Immagine ${index + 1}">
                            <button type="button"
                                    class="btn btn-danger btn-sm position-absolute top-0 end-0 m-2 rounded-circle"
                                    style="width: 30px; height: 30px; z-index: 10;"
                                    onclick="removeImageFromCarousel(${index})">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                        </div>
                    `;
                carouselInner.appendChild(slide);

                // Crea indicatore
                const indicator = document.createElement('button');
                indicator.type = 'button';
                indicator.setAttribute('data-bs-target', '#bookCarousel');
                indicator.setAttribute('data-bs-slide-to', index + 1);
                indicator.setAttribute('aria-label', `Immagine ${index + 1}`);
                carouselIndicators.appendChild(indicator);
            };
            reader.readAsDataURL(file);
        });
    }

    function updateInputHint() {
        const activeSlide = document.querySelector('#bookCarousel .carousel-item.active');
        const isFirstSlide = activeSlide === carouselInner.querySelector('.carousel-item:first-child');
        const hintText = document.getElementById('hintText');

        if (isFirstSlide) {
            hintText.textContent = 'Seleziona foto copertina';
            hintText.style.color = '#007bff';
        } else {
            hintText.textContent = 'Seleziona altre foto del libro';
            hintText.style.color = '#28a745';
        }
    }

    addImgBtn.addEventListener('click', () => {
        // Controlla quale slide è attiva
        const activeSlide = document.querySelector('#bookCarousel .carousel-item.active');
        const isFirstSlide = activeSlide === carouselInner.querySelector('.carousel-item:first-child');

        if (isFirstSlide) {
            coverInput.click();
            console.log(`${filesList.length} Devo aggiungere alla copertina`);
        } else {
            otherImagesInput.click();
            console.log(`${otherImagesInput.files.length} Devo aggiungere ad altre foto`);

        }

    });


    // Aggiorna l'hint quando cambia slide
    document.getElementById('bookCarousel').addEventListener('slid.bs.carousel', updateInputHint);

    // Inizializza l'hint al caricamento
    updateInputHint();

    // Event listener per copertina (singola foto)
    coverInput.addEventListener("change", function (event) {
        const file = event.target.files[0]; // Solo il primo file
        if (file) {
            // Sostituisci la copertina nel carosello
            const firstSlide = carouselInner.querySelector('.carousel-item:first-child img');
            const reader = new FileReader();
            reader.onload = e => {
                firstSlide.src = e.target.result;
                console.log('Copertina aggiornata');
            };
            reader.readAsDataURL(file);
        }
    });

    // Event listener per altre immagini (multiple)
    otherImagesInput.addEventListener("change", function (event) {
        const newFiles = Array.from(event.target.files);

        // Aggiungi a filesList per la preview
        filesList = [...filesList, ...newFiles];
        updateCarousel();

        // Mantieni i file nell'input per il form (NON svuotare!)
        // Per permettere selezioni multiple successive, accumula i file
        const dataTransfer = new DataTransfer();

        // Copia tutti i file da filesList nell'input
        filesList.forEach(file => {
            dataTransfer.items.add(file);
        });

        otherImagesInput.files = dataTransfer.files;

        console.log(`${newFiles.length} nuove immagini aggiunte`);
        console.log(`${otherImagesInput.files.length} file totali nell'input`);
    });

    // funzione per rimuovere singole immagini
    window.removeImageFromCarousel = function (index) {
        filesList.splice(index, 1);


        const dataTransfer = new DataTransfer();
        filesList.forEach(file => {
            dataTransfer.items.add(file);
        });
        otherImagesInput.files = dataTransfer.files;

        updateCarousel();
        console.log(`Immagine ${index + 1} rimossa. File rimanenti: ${filesList.length}`);
    };
});

