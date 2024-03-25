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


// Supongamos que tienes una matriz de días especiales
var specialDays = [3, 7, 14, 20];
var currentDate = new Date();

// Función para generar el calendario
function generateCalendar() {
    var calendar = document.getElementById('calendar');
    var currentMonth = currentDate.getMonth();
    var currentYear = currentDate.getFullYear();
    var firstDayOfMonth = new Date(currentYear, currentMonth, 1).getDay(); // Obtiene el día de la semana del primer día del mes
    var daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate(); // Obtiene el número de días en el mes actual
    var html = '';

    // Agrega el encabezado con el nombre del mes y los días de la semana
    html += '<tr><th colspan="7">' + getMonthName(currentMonth) + ' ' + currentYear + '</th></tr>';
    html += '<tr><th>Sun</th><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th></tr>';

    // Agrega celdas vacías para los días de la semana anteriores si el mes no comienza en domingo
    for (var i = 0; i < firstDayOfMonth; i++) {
        html += '<td></td>';
    }

    // Agrega los días del mes actual
    var dayCounter = 1;
    for (var i = firstDayOfMonth; i < 7; i++) {
        html += '<td' + (specialDays.includes(dayCounter) ? ' class="special-day"' : '') + '>' + dayCounter + '</td>';
        dayCounter++;
    }
    html += '</tr>';

    while (dayCounter <= daysInMonth) {
        html += '<tr>';
        for (var i = 0; i < 7 && dayCounter <= daysInMonth; i++) {
            html += '<td' + (specialDays.includes(dayCounter) ? ' class="special-day"' : '') + '>' + dayCounter + '</td>';
            dayCounter++;
        }
        html += '</tr>';
    }

    calendar.innerHTML = html;
}

// Función para obtener el nombre del mes
function getMonthName(monthIndex) {
    var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    return monthNames[monthIndex];
}

// Función para avanzar al mes siguiente
function nextMonth() {
    currentDate.setMonth(currentDate.getMonth() + 1);
    generateCalendar();
    document.getElementById('prevMonthBtn').style.display = 'inline'; // Muestra el botón "Previous Month"
    document.getElementById('nextMonthBtn').style.display = 'none'; // Oculta el botón "Next Month"
}

// Función para retroceder al mes anterior
function prevMonth() {
    currentDate.setMonth(currentDate.getMonth() - 1);
    generateCalendar();
    document.getElementById('nextMonthBtn').style.display = 'inline'; // Muestra el botón "Next Month"
    document.getElementById('prevMonthBtn').style.display = 'none'; // Oculta el botón "Previous Month"
}

// Genera el calendario al cargar la página
generateCalendar();