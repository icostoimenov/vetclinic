$(function(){
    $("#datepicker").datepicker();
});

let usedDates = []

fetch("http://localhost:8080/appointments/api").then(response => response.json()).then(data => {
    for (let date of data) {
        usedDates.push(date["appointmentDate"]);
    }
})

$('.app-date').datepicker({
    beforeShowDay: function(date){
        let string = jQuery.datepicker.formatDate('yy-mm-dd', date);
        return [ usedDates.indexOf(string) === -1 ]
    },
    minDate: 0
});
