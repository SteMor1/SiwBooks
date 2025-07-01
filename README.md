## 🛠️ Funzionalità del Sistema – SiwBooks

### 👥 Ruoli Utente

- **Utente Occasionale**  
  - Può consultare tutte le informazioni su libri, autori e recensioni.  
  - Non può aggiungere, modificare o eliminare alcun dato.

- **Utente Registrato**  
  - Può consultare tutti i libri, gli autori e le recensioni.  
  - Può inserire, modificare o cancellare **le proprie recensioni**.

- **Amministratore**  
  - Pieno accesso al sistema.  
  - Può aggiungere, modificare o eliminare **libri, autori** e **recensioni**.  

---

### 📋 Dettaglio delle Informazioni – SiwBooks

#### 📚 Book (Libro)
- Titolo del libro  
- Data di pubblicazione  
- Copertina  
- Immagini aggiuntive  
- Autori del libro  
- Recensioni associate  

---

#### 👤 Author (Autore) 
- Nome  
- Cognome  
- Data di nascita  
- Data di morte (facoltativa)  
- Nazionalità  
- Fotografia  
- Libri scritti  

---

#### 🧑‍💻 User (Utente)
- Nome  
- Cognome  
- Email di contatto  

---

#### 📝 Review (Recensione)  
- Titolo della recensione  
- Testo della recensione  
- Valutazione numerica  
- Libro recensito  
- Utente autore della recensione  



---

## 📌 Casi d'Uso Implementati – SiwBooks

### 📚 Inserimento di un nuovo libro  
**Attore:** Amministratore  
**Descrizione:** L'amministratore può inserire un nuovo libro nel sistema.  
**Azioni:**
1. L'utente accede alla sezione per l'inserimento dei libri.
2. Il sistema mostra un form per l'inserimento dei dati.
3. L'utente compila e conferma il form.
4. Il sistema mostra un messaggio di conferma.
5. L'utente conferma.
6. Il sistema inserisce il libro.

---

### ✏️ Modifica di un libro esistente  
**Attore:** Amministratore  
**Descrizione:** L'amministratore può modificare i dati di un libro esistente.  
**Azioni:**
1. L'utente seleziona il libro da modificare.
2. Il sistema mostra un form con i dati da modificare.
3. L'utente modifica e conferma i dati.
4. Il sistema mostra un messaggio di conferma.
5. L'utente conferma.
6. Il sistema aggiorna i dati del libro.

---

### 🗑️ Eliminazione di un libro  
**Attore:** Amministratore  
**Descrizione:** L'amministratore può eliminare un libro dal sistema.  
**Azioni:**
1. L'utente seleziona il libro da cancellare.
2. Il sistema mostra un messaggio di conferma.
3. L'utente conferma.
4. Il sistema elimina il libro.

---

### 🔍 Visualizzazione elenco libri  
**Attore:** Utente Generico  
**Descrizione:** Gli utenti possono visualizzare l'elenco dei libri disponibili.  
**Azioni:**
1. L'utente accede alla sezione dell'elenco libri.
2. Il sistema mostra tutti i libri.  

**Estensioni:**
- Ordina per autore / titolo.
- Filtra per autore / titolo (prefisso) / anno.
- Visualizza solo i libri di un autore.

---

### 👤 Inserimento di un nuovo autore  
**Attore:** Amministratore  
**Descrizione:** L'amministratore può aggiungere un nuovo autore nel sistema.  
**Azioni:**
1. L'utente accede alla sezione per l'inserimento autori.
2. Il sistema mostra un form.
3. L'utente compila e conferma i dati.
4. Il sistema mostra un messaggio di conferma.
5. L'utente conferma.
6. Il sistema inserisce l'autore.

---

### ✏️ Modifica di un autore  
**Attore:** Amministratore  
**Descrizione:** L'amministratore può modificare i dati di un autore.  
**Azioni:**
1. L'utente seleziona l'autore da modificare.
2. Il sistema mostra un form con i dati da aggiornare.
3. L'utente apporta le modifiche e conferma.
4. Il sistema aggiorna le informazioni.

---

### 🗑️ Eliminazione di un autore  
**Attore:** Amministratore  
**Descrizione:** L'amministratore può eliminare un autore dal sistema.  
**Azioni:**
1. Il sistema mostra la lista degli autori.
2. L'utente seleziona l'autore da eliminare.
3. Il sistema mostra un messaggio di conferma.
4. L'utente conferma.
5. Il sistema elimina l'autore.

---

### 👁️‍🗨️ Visualizzazione elenco autori  
**Attore:** Utente Generico  
**Descrizione:** Gli utenti possono visualizzare tutti gli autori registrati.  
**Azioni:**
1. L'utente accede alla sezione degli autori.
2. Il sistema mostra l'elenco completo.

**Estensioni:**
- Filtra per nome completo (prefisso).

---

### 📝 Inserimento di una recensione  
**Attore:** Utente Autenticato  
**Descrizione:** L'utente può inserire una recensione per un libro che non ha ancora recensito.  
**Azioni:**
1. L'utente accede alla pagina di un libro.
2. Il sistema mostra un form (titolo, valutazione, testo).
3. L'utente compila e conferma.
4. Il sistema mostra un messaggio di conferma.
5. L'utente conferma.
6. Il sistema inserisce la recensione.

---

### ✏️ Modifica di una recensione  
**Attore:** Utente Autenticato  
**Descrizione:** L'utente può modificare una recensione già rilasciata.  
**Azioni:**
1. L'utente accede alla pagina del libro.
2. Il sistema mostra il form di modifica.
3. L'utente modifica i dati e conferma.
4. Il sistema aggiorna la recensione.
