$(function(){
    $("#datepicker").datepicker();
});

let usedDates = []

fetch("http://localhost:8080/appointments/api").then(response => response.json()).then(data => {
    for (let date of data) {
        usedDates.push(date);
    }
})

$('input').datepicker({
    beforeShowDay: function(date){
        let string = jQuery.datepicker.formatDate('yy-mm-dd', date);
        return [ usedDates.indexOf(string) === -1 ]
    }
});

// var array = ["2021-04-14 00:00:00","2015-06-15 00:00:00","2015-06-16 00:00:00", "2015-06-24 00:00:00", "2015-06-21 00:00:00"]
// var trima = []
// for (i = 0; i < array.length; i++ ) {
//     trima.push(array[i].substring(10,""))
// }
// $('input').datepicker({
//     beforeShowDay: function(date){
//         var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
//         return [ trima.indexOf(string) === -1 ]
//     }
// });
