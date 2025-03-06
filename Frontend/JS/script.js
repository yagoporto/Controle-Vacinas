document.addEventListener('DOMContentLoaded', () => {
    const formVacina = document.getElementById('form-vacina');
    const listaVacinas = document.getElementById('lista-vacinas');
    const listaCalendario = document.getElementById('lista-calendario');
    const selectVacina = document.getElementById('vacina');

    const vacinas = [
        { id: 1, nome: 'BCG', dose: 'Única' },
        { id: 2, nome: 'Pentavalente', dose: '1ª Dose' },
        { id: 3, nome: 'Pentavalente', dose: '2ª Dose' },
        { id: 4, nome: 'Poliomielite', dose: '1ª Dose' },
        { id: 5, nome: 'Poliomielite', dose: '2ª Dose' },
        { id: 6, nome: 'Hepatite A', dose: 'Única' },
        { id: 7, nome: 'Tríplice Viral', dose: '1ª Dose' },
        { id: 8, nome: 'Tríplice Viral', dose: '2ª Dose' },
        { id: 9, nome: 'DTP', dose: 'Ref. 18 meses' },
        { id: 10, nome: 'Varicela', dose: 'Única' },
        { id: 11, nome: 'Febre Amarela', dose: 'Única' },
        { id: 12, nome: 'HPV', dose: '1ª Dose' },
        { id: 13, nome: 'HPV', dose: '2ª Dose' },
        { id: 14, nome: 'Meningocócica ACWY', dose: 'Única' },
        { id: 15, nome: 'Meningocócica B', dose: 'Única' }
    ];

    // Preenche o select com as vacinas
    vacinas.forEach(vacina => {
        const option = document.createElement('option');
        option.value = vacina.id;
        option.textContent = `${vacina.nome} - ${vacina.dose}`;
        selectVacina.appendChild(option);
    });

    formVacina.addEventListener('submit', (e) => {
        e.preventDefault();
        
        const nomeIntegrante = document.getElementById('nome-integrante').value;
        const vacinaSelecionada = vacinas.find(vacina => vacina.id == selectVacina.value);
        const dataAplicacao = document.getElementById('data-aplicacao').value;

        if (!vacinaSelecionada || !nomeIntegrante || !dataAplicacao) {
            alert("Por favor, preencha todos os campos!");
            return;
        }

        const vacinaItem = document.createElement('li');
        vacinaItem.textContent = `${nomeIntegrante} - ${vacinaSelecionada.nome} - ${vacinaSelecionada.dose} - ${dataAplicacao}`;
        listaVacinas.appendChild(vacinaItem);

        // Limpa os campos após cadastrar
        formVacina.reset();
    });

    const carregarCalendario = () => {
        const calendarioVacinas = [
            { idade: '2 meses', vacina: 'BCG' },
            { idade: '4 meses', vacina: 'Pentavalente' },
            { idade: '6 meses', vacina: 'Poliomielite' },
            { idade: '12 meses', vacina: 'Hepatite A' },
            { idade: '15 meses', vacina: 'Tríplice Viral (Sarampo, Caxumba, Rubéola)' },
            { idade: '18 meses', vacina: 'DTP (Difteria, Tétano, Coqueluche)' },
            { idade: '4 anos', vacina: 'Varicela' },
            { idade: '5 anos', vacina: 'Febre Amarela' },
            { idade: '10 anos', vacina: 'HPV' },
            { idade: '11 anos', vacina: 'Meningocócica ACWY' },
            { idade: '16 anos', vacina: 'Meningocócica B' }
        ];

        calendarioVacinas.forEach(item => {
            const calendarioItem = document.createElement('li');
            calendarioItem.textContent = `${item.idade} - ${item.vacina}`;
            listaCalendario.appendChild(calendarioItem);
        });
    };

    carregarCalendario();
});
