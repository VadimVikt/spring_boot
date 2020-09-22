$(document).ready(function (){
    $('.table .eBtn').on('click', function (event){
        console.log("Привет это модальное окно ");
        $('.my-form #exampleModal').modal();
    });






    $("#new-user").click(function() {
        $("#users").addClass('hidden');
        $("#add-user").removeClass('hidden');
        $("#users-table").removeClass('active');
        $("#new-user").addClass('active');
    });

    $("#users-table").click(function() {
        $("#add-user").addClass('hidden');
        $("#users").removeClass('hidden');
        $("#new-user").removeClass('active');
        $("#users-table").addClass('active');
    });

;})