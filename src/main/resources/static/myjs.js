$(document).ready(function (){
    // $('.table .eBtn').on('click', function (event){
    //     console.log("Привет это модальное окно ");
    //     $('.my-form #exampleModal').modal();
    // });

    $("#exampleModal ").on('show.bs.modal', function(e) {

        let userId = $(e.relatedTarget).data('user-id');
        let userName = $(e.relatedTarget).data('user-firstname');
        let userLastName = $(e.relatedTarget).data('user-lastname');
        let age = $(e.relatedTarget).data('user-age');
        let email = $(e.relatedTarget).data('user-email');
        let roles = $(e.relatedTarget).data('user-roles');
        let ob = $(e.relatedTarget).data('object')

        $('#firstNameInput').val(userName);
        $('#lastNameInput').val(userLastName);
        $('#ageInput').val(age);
        $('#emailInput').val(email);
        $('#roleInput').val(roles);
        $('#idInput').val(userId);
    });

    $("#exampleModal").on('hidden.bs.modal', function() {
        let form = $(this).find('form');
        form[0].reset();
    });

    $("#modalDelete ").on('show.bs.modal', function(e) {

        let userId = $(e.relatedTarget).data('user-id');
        let userName = $(e.relatedTarget).data('user-firstname');
        let userLastName = $(e.relatedTarget).data('user-lastname');
        let age = $(e.relatedTarget).data('user-age');
        let email = $(e.relatedTarget).data('user-email');
        let roles = $(e.relatedTarget).data('user-roles');
        let ob = $(e.relatedTarget).data('object')

        $('#firstNameDelete').val(userName);
        $('#lastNameDelete').val(userLastName);
        $('#ageDelete').val(age);
        $('#emaiDelete').val(email);
        $('#roleDelete').val(roles);
        $('#idDelete').val(userId);
    });

    $("#modalDelete").on('hidden.bs.modal', function() {
        let form = $(this).find('form');
        form[0].reset();
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