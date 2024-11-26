const initDaysInMonthRange = (month , year) => {
    const daysOfMonth = [];
    //  to be sure month and year are numbers instead of this month + 1 = 111
    const num_month = Number(month);
    const num_year = Number(year);
    for(let d = new Date(num_year, num_month, 1); d <= new Date(num_year, num_month + 1, 0); d.setDate(d.getDate() + 1)){
        daysOfMonth.push(new Date(d));
    }
    return daysOfMonth;
}

const daysOfTheWeekToString = ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'];
const monthsOfTheYear = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];

const setCheckboxValue = (checkbox) => {
    const state = checkbox.getAttribute("data-state");

    if (state === "null") {
        checkbox.checked = true;
        checkbox.indeterminate = false;
        checkbox.setAttribute("data-state", "true");
    } else if (state === "true") {
        checkbox.checked = false;
        checkbox.indeterminate = true;
        checkbox.setAttribute("data-state", "false");
    } else {
        checkbox.checked = false;
        checkbox.indeterminate = false;
        checkbox.setAttribute("data-state", "null");
    }
}

const sendAvailabilities = () => {
    const checkboxes = Array
        .from(document.querySelectorAll('input[name="availability"]'))
        .filter(checkbox => checkbox.getAttribute('data-state') !== 'null');
    const dataToSend = checkboxes.map(checkbox => {
        let id = checkbox.getAttribute('id');
        return {date: id, value: checkbox.getAttribute('data-state')};
    });
    console.log(dataToSend);
    fetch("localhost:8080", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: dataToSend
    })
        .then(response => {response.json()})
        .then(data => console.log(data))
        .catch(error => console.error('Erreur', error));

}

const configForm = (month, year) => {
    const daysOfMonth = initDaysInMonthRange(month, year);
    
    const form = document.getElementById('calendar');
    daysOfMonth.forEach( date => {
        const div = document.createElement('div');

        const label1 = document.createElement('label');
        label1.appendChild(
            document.createTextNode(`${daysOfTheWeekToString[date.getDay()].substring(0, 3)} ${date.getDate()} ${monthsOfTheYear[date.getMonth()].substring(0, 3)}`)
        )

        const checkbox = document.createElement('input');
        checkbox.setAttribute('id', `${String(date.getDate()).padStart(2, '0')}-${String(date.getMonth()+1).padStart(2, '0')}-${date.getFullYear()}`);
        checkbox.setAttribute('type', 'checkbox');
        checkbox.setAttribute('name', 'availability');
        checkbox.setAttribute('data-state', 'null');
        checkbox.setAttribute('onclick', 'setCheckboxValue(this)');
        
        div.appendChild(label1);
        div.appendChild(checkbox);

        form.appendChild(div);
    });
}

let monthFilterValue = 0;
let yearFilterValue = 0;

const refreshForm = (elem) => {
    const form = document.getElementById('calendar');
    
    //  clean calendar form
    while(form.firstChild){
        form.removeChild(form.firstChild);
    }

    //  setup global variable
    if(elem.getAttribute('id') === 'month-filter') {
        monthFilterValue = document.getElementById('month-filter').value;
    } else {
        yearFilterValue = document.getElementById('year-filter').value;
    }

    //  fill calendar form
    configForm(monthFilterValue, yearFilterValue);
}

(function() {
    const now = new Date();
    monthFilterValue = now.getMonth();
    yearFilterValue = now.getFullYear();

    //  fill and setup dropdown month filter
    const monthOption = document.getElementById(`month-${monthFilterValue}`);
    monthOption.setAttribute('selected', 'selected');

    //  fill and setup dropdown year filter
    const yearSelect = document.getElementById('year-filter');
    for(let i = yearFilterValue - 3; i <= yearFilterValue + 3; i++) {
        let option = document.createElement('option');
        option.setAttribute('value', `${i}`);
        option.appendChild(document.createTextNode(`${i}`));
        if(i === yearFilterValue) {
            option.setAttribute('selected','selected');
        }
        yearSelect.appendChild(option);
    }

    //  fill calendar form
    configForm(monthFilterValue, yearFilterValue);
})()