function nextOperationSection() {
    document.getElementById("operation-section").style.display = "none";
    document.getElementById("date-section").style.display = "block";
    document.getElementById("progress25").style.display = "none";
    document.getElementById("progress50").style.display = "block";
}

function backDateSection() {
    document.getElementById("date-section").style.display = "none";
    document.getElementById("operation-section").style.display = "block";
    document.getElementById("progress50").style.display = "none";
    document.getElementById("progress25").style.display = "block";
}

function nextDateSection() {
    document.getElementById("date-section").style.display = "none";
    document.getElementById("confirmation-section").style.display = "block";
    document.getElementById("progress50").style.display = "none";
    document.getElementById("progress75").style.display = "block";
    showConfirmationDetails()
}

function backConfirmationSection() {
    document.getElementById("confirmation-section").style.display = "none";
    document.getElementById("date-section").style.display = "block";
    document.getElementById("progress75").style.display = "none";
    document.getElementById("progress50").style.display = "block";
}

document.getElementById('operation').addEventListener('change', function() {
    // Obtener el valor de la opción seleccionada
    const selectedValue = this.value;

    // Llamar a la función correspondiente según el valor seleccionado
    if (selectedValue === 'Psicologia Adulto') {
        handlePsicologiaAdulto();
    } else if (selectedValue === 'Psicologia Infantil') {
        handlePsicologiaInfantil();
    } else if (selectedValue === 'Gestion de Extranjeria') {
        handleGestionExtranjeria();
    } else {
        handleGestionLaboral();
    }
});

function handlePsicologiaAdulto() {
    document.getElementById("image1").style.display = "block";
    document.getElementById("image2").style.display = "none";
    document.getElementById("image3").style.display = "none";
    document.getElementById("image4").style.display = "none";
    document.getElementById("text1").style.display = "block";
    document.getElementById("text2").style.display = "none";
    document.getElementById("text3").style.display = "none";
    document.getElementById("text4").style.display = "none";

}

function handlePsicologiaInfantil() {
    document.getElementById("image1").style.display = "none";
    document.getElementById("image2").style.display = "block";
    document.getElementById("image3").style.display = "none";
    document.getElementById("image4").style.display = "none";
    document.getElementById("text1").style.display = "none";
    document.getElementById("text2").style.display = "block";
    document.getElementById("text3").style.display = "none";
    document.getElementById("text4").style.display = "none";
}

function handleGestionExtranjeria() {
    document.getElementById("image1").style.display = "none";
    document.getElementById("image2").style.display = "none";
    document.getElementById("image3").style.display = "block";
    document.getElementById("image4").style.display = "none";
    document.getElementById("text1").style.display = "none";
    document.getElementById("text2").style.display = "none";
    document.getElementById("text3").style.display = "block";
    document.getElementById("text4").style.display = "none";
}

function handleGestionLaboral() {
    document.getElementById("image1").style.display = "none";
    document.getElementById("image2").style.display = "none";
    document.getElementById("image3").style.display = "none";
    document.getElementById("image4").style.display = "block";
    document.getElementById("text1").style.display = "none";
    document.getElementById("text2").style.display = "none";
    document.getElementById("text3").style.display = "none";
    document.getElementById("text4").style.display = "block";
}

function generateCalendar() {
    // Obtener el mes y año actual
    const currentDate = new Date();
    const currentMonth = currentDate.getMonth();
    const currentYear = currentDate.getFullYear();

    // Realizar solicitud AJAX para obtener los specialDays del mes actual
    fetch(location.origin + "/available_days_current")
        .then(response => response.json())
        .then(specialDaysCurrent => {
            // Generar el calendario para el mes actual
            const currentCalendar = generateMonthCalendarHTML(currentMonth, currentYear, specialDaysCurrent);
            document.getElementById('calendarContainer').appendChild(currentCalendar);

            // Calcular el mes siguiente
            const nextMonth = (currentMonth + 1) % 12; // Asegurar que esté en el rango 0-11
            const nextYear = nextMonth === 0 ? currentYear + 1 : currentYear; // Incrementar el año si el mes es diciembre

            // Realizar solicitud AJAX para obtener los specialDays del mes siguiente
            fetch(location.origin + "/available_days_next")
                .then(response => response.json())
                .then(specialDaysNext => {
                    // Generar el calendario para el mes siguiente
                    const nextCalendar = generateMonthCalendarHTML(nextMonth, nextYear, specialDaysNext);
                    document.getElementById('calendarContainer').appendChild(nextCalendar);
                })
                .catch(error => {
                    console.error('Error al obtener specialDays del próximo mes:', error);
                });
        })
        .catch(error => {
            console.error('Error al obtener specialDays del mes actual:', error);
        });
}


function generateMonthCalendarHTML(month, year, specialDays) {
    // Obtener el nombre del mes
    const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    const monthName = monthNames[month];

    // Crear la tabla del calendario
    const table = document.createElement('table');
    table.classList.add('calendar');

    // Generar el encabezado de la tabla con el nombre del mes
    const headerRow = document.createElement('tr');
    const monthHeader = document.createElement('th');
    monthHeader.colSpan = 7; // Abarcar todas las columnas de la tabla
    monthHeader.textContent = monthName + ' ' + year;
    headerRow.appendChild(monthHeader);
    table.appendChild(headerRow);

    // Agregar el encabezado de los días de la semana
    const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    const daysRow = document.createElement('tr');
    for (let i = 0; i < daysOfWeek.length; i++) {
        const dayHeader = document.createElement('th');
        dayHeader.textContent = daysOfWeek[i];
        daysRow.appendChild(dayHeader);
    }
    table.appendChild(daysRow);

    // Obtener el primer día del mes
    const firstDayOfMonth = new Date(year, month, 1).getDay()+1;

    // Obtener el número de días en el mes
    const daysInMonth = new Date(year, month + 1, 0).getDate();

    // Calcular el número de celdas vacías necesarias para completar la primera semana
    const emptyCellsBeforeFirstDay = (firstDayOfMonth === 0) ? 6 : firstDayOfMonth - 1;

    // Generar las celdas para cada día del mes
    let row = document.createElement('tr');

    // Agregar celdas vacías antes del primer día del mes
    for (let i = 0; i < emptyCellsBeforeFirstDay; i++) {
        const emptyCell = document.createElement('td');
        row.appendChild(emptyCell);
    }

    // Agregar las celdas para los días del mes
    for (let day = 1; day <= daysInMonth; day++) {
        const dayCell = document.createElement('td');
        dayCell.textContent = day;
        dayCell.setAttribute('data-month', month); // Agregar el atributo data-month con el valor del mes
        dayCell.setAttribute('data-year', year); // Agregar el atributo data-year con el valor del año
        dayCell.classList.add("selected-date")
        if (specialDays.includes(day)) {
            dayCell.classList.add('special-day');
        }
        row.appendChild(dayCell);

        if ((emptyCellsBeforeFirstDay + day) % 7 === 0 || day === daysInMonth) {
            // Si es el último día de la semana o el último día del mes, crear una nueva fila
            table.appendChild(row);
            row = document.createElement('tr');
        }
    }

    // Obtener todas las celdas de la tabla
    const cells = table.querySelectorAll('td');

    // Agregar un listener de eventos onclick a cada celda
    cells.forEach(cell => {
        cell.addEventListener('click', function() {

            const day = parseInt(cell.textContent); // Obtener el número del día
            const month = parseInt(cell.getAttribute('data-month')); // Obtener el mes
            const year = parseInt(cell.getAttribute('data-year')); // Obtener el año
            if (cell.classList.contains('special-day')) {
                cellsOnClick();
                showEventList(day, month, year); // Llamar a la función para mostrar la lista de eventos
            }
        });
    });
    return table;
}

let selectedDate = ""; // Variable para almacenar la fecha seleccionada

function showEventList(day, month, year) {
    // Realizar una solicitud AJAX para obtener la lista de eventos para el día seleccionado
    fetch(location.origin + "/timeBandsByDay?day=" + day + "&month=" + month + "&year=" + year)
        .then(response => response.json())
        .then(timeList => {
            // Construir la lista de eventos en formato HTML
            let html = '<h2>Select time</h2>';
            html += '<select id="timeList" name="timeList">'; // Lista desplegable
            timeList.forEach(event => {
                html += '<option value="' + event + '">' + event + '</option>'; // Agregar cada opción de la lista desplegable
            });
            html += '</select>';

            // Mostrar la lista de eventos en el contenedor correspondiente en tu vista
            const eventList = document.getElementById('eventList');
            eventList.innerHTML = html;

            selectedDate = `${day}/${month + 1}/${year}`;
        })
        .catch(error => {
            console.error('Error al obtener la lista de eventos:', error);
        });
}

function showConfirmationDetails() {
    // Obtener el valor seleccionado del select 'operation'
    const operationValue = document.getElementById('operation').value;

    // Obtener el valor seleccionado del select dentro de 'eventList'
    const timeBandValue = document.getElementById('eventList').querySelector('select').value;

    // Construir el mensaje de confirmación con los valores obtenidos
    const confirmationMessage = `
        <p><strong>Operation:</strong> ${operationValue}</p>
        <p><strong>Date:</strong> ${selectedDate}</p>
        <p><strong>Time:</strong> ${timeBandValue}</p>
    `;

    // Mostrar los detalles de confirmación en la sección correspondiente
    const confirmationSection = document.getElementById('confirmationDetails');
    confirmationSection.innerHTML = confirmationMessage;
}

function confirmAppointment() {
    // Obtener los valores de los campos del formulario
    const operationValue = document.getElementById('operation').value;
    const timeBandValue = document.getElementById('eventList').querySelector('select').value;

    // Realizar una solicitud POST al controlador
    fetch(location.origin + '/newAppointment?operation=' + operationValue + "&date=" + selectedDate + "&timeBand=" + timeBandValue, {
        method: 'GET'
    })
        .then(response => {
            if (response.ok) {
                alert("Appointment confirmed")
                window.location.href = '/';
            }
        })
        .catch(error => {
            console.error('Error al enviar la solicitud:', error);
        });
}

generateCalendar();

function cellsOnClick() {
    // Obtener todas las tablas con la clase "calendar"
    const calendars = document.getElementsByClassName("calendar");

    // Crear un array vacío para almacenar todas las celdas de ambas tablas
    const allCells = [];

    // Iterar sobre todas las tablas
    for (let i = 0; i < calendars.length; i++) {
        const table = calendars[i];

        // Obtener todas las celdas de la tabla actual y agregarlas al array allCells
        const cells = table.querySelectorAll('td');
        allCells.push(...cells);
    }

    // Asignar el manejador de eventos onclick a cada celda
    allCells.forEach(cell => {
        cell.onclick = () => {
            // Restaurar el estilo de todas las celdas
            allCells.forEach(cell => {
                cell.style.transform = "scale(1)";
                cell.style.color = "black";
            });

            // Aplicar estilos a la celda clickeada
            cell.style.transform = "scale(0.9)";
            cell.style.color = "grey";
        };
    });
}

