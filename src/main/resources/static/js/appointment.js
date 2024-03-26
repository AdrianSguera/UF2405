function nextOperationSection() {
    document.getElementById("operation-section").style.display = "none";
    document.getElementById("date-section").style.display = "block";
}

function backDateSection() {
    document.getElementById("date-section").style.display = "none";
    document.getElementById("operation-section").style.display = "block";
}

function nextDateSection() {
    document.getElementById("date-section").style.display = "none";
    document.getElementById("confirmation-section").style.display = "block";
}

function backConfirmationSection() {
    document.getElementById("confirmation-section").style.display = "none";
    document.getElementById("date-section").style.display = "block";
}


document.addEventListener("DOMContentLoaded", function() {
    generateCalendar();

    // Ocultar el botón "Previous Month" al cargar la página
    document.getElementById('prevMonthBtn').style.display = 'none';
});

function generateCalendar() {

        // Realizar solicitud AJAX para obtener los specialDays del mes actual
    fetch('/available_days_current')
        .then(response => response.json())
        .then(specialDaysCurrent => {
            // Realizar solicitud AJAX para obtener los specialDays del mes siguiente
            fetch('/available_days_next')
                .then(response => response.json())
                .then(specialDaysNext => {
                    // Generar el calendario con los specialDays obtenidos
                    generateCalendarHTML(specialDaysCurrent, specialDaysNext);
                })
                .catch(error => {
                    console.error('Error al obtener specialDays del próximo mes:', error);
                });
        })
        .catch(error => {
            console.error('Error al obtener specialDays del mes actual:', error);
        });
}

function generateCalendarHTML(specialDaysCurrent, specialDaysNext) {
    // Obtener el elemento de la tabla del calendario
    const calendar = document.getElementById('calendar');

    // Limpiar el contenido existente de la tabla
    calendar.innerHTML = '';

    // Obtener el mes actual y el siguiente
    const currentDate = new Date();
    const currentMonth = currentDate.getMonth();
    const currentYear = currentDate.getFullYear();
    const nextDate = new Date(currentDate);
    nextDate.setMonth(nextDate.getMonth() + 1);
    const nextMonth = nextDate.getMonth();
    const nextYear = nextDate.getFullYear();

    // Obtener el primer día del mes actual y el siguiente
    const firstDayOfMonthCurrent = new Date(currentYear, currentMonth, 1).getDay();
    const firstDayOfMonthNext = new Date(nextYear, nextMonth, 1).getDay();

    // Obtener el número de días en el mes actual y el siguiente
    const daysInMonthCurrent = new Date(currentYear, currentMonth + 1, 0).getDate();
    const daysInMonthNext = new Date(nextYear, nextMonth + 1, 0).getDate();

    // Generar el encabezado de la tabla con los nombres de los días de la semana
    const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    let headerRow = '<tr>';
    for (let i = 0; i < daysOfWeek.length; i++) {
        headerRow += '<th>' + daysOfWeek[i] + '</th>';
    }
    headerRow += '</tr>';

    // Agregar el encabezado a la tabla
    calendar.innerHTML += headerRow;

    // Generar el calendario para el mes actual
    generateMonthCalendar(calendar, currentMonth, currentYear, firstDayOfMonthCurrent, daysInMonthCurrent, specialDaysCurrent);

    // Generar el calendario para el mes siguiente
    generateMonthCalendar(calendar, nextMonth, nextYear, firstDayOfMonthNext, daysInMonthNext, specialDaysNext);
}

function generateMonthCalendar(calendar, month, year, firstDayOfMonth, daysInMonth, specialDays) {
    // Crear una fila para los días de la semana
    let row = '<tr>';

    // Agregar celdas vacías para los días de la semana anteriores si el mes no comienza en domingo
    for (let i = 0; i < firstDayOfMonth; i++) {
        row += '<td></td>';
    }

    // Generar celdas para cada día del mes
    for (let day = 1; day <= daysInMonth; day++) {
        // Determinar si el día es especial
        const isSpecialDay = specialDays.includes(day);

        // Agregar la clase "special-day" si el día es especial
        const dayClass = isSpecialDay ? ' class="special-day"' : '';

        // Agregar la celda del día al calendario
        row += '<td' + dayClass + '>' + day + '</td>';

        // Si el día es sábado, cerrar la fila actual y comenzar una nueva fila
        if ((firstDayOfMonth + day) % 7 === 0) {
            row += '</tr>';
            calendar.innerHTML += row;
            row = '<tr>';
        }
    }

    // Agregar celdas vacías para completar la última semana si es necesario
    if ((firstDayOfMonth + daysInMonth) % 7 !== 0) {
        for (let i = 0; i < 7 - ((firstDayOfMonth + daysInMonth) % 7); i++) {
            row += '<td></td>';
        }
    }

    // Cerrar la última fila
    row += '</tr>';

    // Agregar la fila al calendario
    calendar.innerHTML += row;
}

function nextMonth() {
    // Ocultar el botón "Next Month" y mostrar el botón "Previous Month"
    document.getElementById('nextMonthBtn').style.display = 'none';
    document.getElementById('prevMonthBtn').style.display = 'inline';

    // Generar el calendario para el próximo mes
    generateCalendar();
}

function prevMonth() {
    // Ocultar el botón "Previous Month" y mostrar el botón "Next Month"
    document.getElementById('prevMonthBtn').style.display = 'none';
    document.getElementById('nextMonthBtn').style.display = 'inline';

    // Generar el calendario para el mes anterior
    generateCalendar();
}
