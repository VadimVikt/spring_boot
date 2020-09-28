$(document).ready(function () {
    console.log("Start Js")
    const usersList = $('.users-list').get(0);
    const selectRoles = $('.select-roles').get(0);
    const editRoles = $('.edit-roles').get(0);
    const deleteRoles = $('.delete-roles').get(0);

    const url = 'http://localhost:8080/klient';
    const urlAdd = 'http://localhost:8080/add_user';
    const urlRoles = 'http://localhost:8080/roles';
    const urlFindUser = 'http://localhost:8080/findOne/';
    const updateUser = 'http://localhost:8080/update_user/';
    const urlDelUser = 'http://localhost:8080/delete_user/';
    let tableUsers = '';
    let addForm = '';
    let roles = [{}];
// GET - read the roles
//Method - GET

loadTable();

createRoles();

function createRoles() {
    console.log("Отработала функция createRoles")
    fetch(urlRoles)
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                addForm += `<option id="${role.id}"> ${role.name} </option>`;
            });
            $(selectRoles).append(addForm);
            $(editRoles).append(addForm);
            $(deleteRoles).append(addForm);
        });
}

// const inputRoles  = function () {
function inputRoles() {
    console.log("Отработала функция inputRoles")
    fetch(urlRoles)
        .then(res => res.json())
        .then(data => {
            let count = 0;
            data.forEach(role => {
                roles[count] = {
                    id: role.id,
                    name: role.name
                }
                count++;
            });
        });
}

// GET - read the users
//Method - GET
// const loadTable = function () {
function loadTable() {
    // let tableUsers = '';
    console.log("Отработала функция loadTable")
    fetch(url)
        .then(res => res.json())
        .then(data => {
            console.log("Получены пользователи")
            console.log(data)
            data.forEach(user => {
                roles = user.roles;
                tableUsers += `<tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td><td>`
                roles.forEach(role => {
                    tableUsers += `
                        ${role.name}`
                });
                tableUsers += `</td>`;
                tableUsers += `<td><button type="button" class="btn btn-info" data-toggle="modal"  data-user-id="${user.id}" data-target="#addModal">
                            Edit
                            </button></td>`;
                tableUsers += `<td><button type="button" class="btn btn-danger" data-toggle="modal" data-user-id="${user.id}" data-target="#delModal"> 
                            Delete
                            </button></td>`;
            });
            $(usersList).append(tableUsers);
        });
}

function form() {
    return {
        username: $('#add-username').val(),
        lastName: $('#add-last-name').val(),
        age: $('#add-age').val(),
        password: $('#add-password').val(),
        email: $('#add-email').val(),
        roles: $('.select-roles').val()
    };
}
function updateForm() {
    return {
        id: $('#idInput').val(),
        username: $('#firstNameInput').val(),
        lastName: $('#lastNameInput').val(),
        age: $('#ageInput').val(),
        password: $('#passwordInput').val(),
        email: $('#emailInput').val(),
        roles: $('.edit-roles').val()
    };
}
function deleteForm() {
    return {
        id: $('#idDel').val(),
        username: $('#firstNameDel').val(),
        lastName: $('#lastNameDel').val(),
        age: $('#ageDel').val(),
        password: $('#passwordDel').val(),
        email: $('#emailDel').val(),
        roles: $('.delete-roles').val()
    };
}

function newUser() {
    console.log('Получено ролей - ' + roles.length)
    // usersList.remove();
    let user = form();
    console.log('User ')
    console.log(user)
    console.log('Roles')
    console.log(roles)
    $('.users-list').empty();
    $.each(user.roles, function (index, value) {
        // console.log('Индекс итерация по введеным ролям : ' + index, 'Значение : ' + value)
        $.each(roles, function (ind, item) {
            // console.log('Итерация по Ролям из БД ключ - ' + ind, 'Значение : ' + item.name)
            if (value === item.name) {
                // console.log('Есть совпадение ролей- ' + value + ' Значение ключа  - ' + index);
                user.roles.splice(ind, index);
                user.roles[index] = roles[ind]
            }
        })
    })
    tableUsers = '';
    fetch(urlAdd, {
        method: 'POST',
        credentials: "same-origin",
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(res => res.json())
        .then(data => loadTable());
    console.log(tableUsers)
    $('#add-username').val('');
    $('#add-last-name').val('');
    $('#add-age').val('');
    $('#add-email').val('');
    $('#add-password').val('');
}

$('#addModal').on('show.bs.modal', function (e) {
    console.log('Нажали кнопку')
    console.log('Ид юзера для правки - ' + $(e.relatedTarget).data('user-id'));
    inputRoles();
    let id = $(e.relatedTarget).data('user-id');
    console.log(addForm);
    fetch(urlFindUser + id)
        .then(res => res.json())
        .then(user => {
            $('#idInput').val(user.id);
            $('#firstNameInput').val(user.username);
            $('#lastNameInput').val(user.lastName);
            $('#ageInput').val(user.age);
            $('#emailInput').val(user.email);
            $('#passwordInput').val(user.password);
        });

});
    $('#updateUser').on('click', function () {
        console.log('Начинаем обновлять')
        console.log('Получено ролей - ' + roles.length)
        let user = updateForm();
        $('.users-list').empty();
        $.each(user.roles, function (index, value) {
            $.each(roles, function (ind, item) {
                if (value === item.name) {
                    user.roles.splice(ind, index);
                    user.roles[index] = roles[ind]
                }
            })
        })
        console.log(user);
        tableUsers = '';
        fetch(updateUser, {
            method: 'PUT',
            credentials: "same-origin",
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(user)
        })
            .then(res => res.json())
            .then(data => loadTable());

    })

    $('#delModal').on('show.bs.modal', function (e) {
        console.log('Нажали кнопку')
        console.log('Ид юзера для удаления - ' + $(e.relatedTarget).data('user-id'));
        inputRoles();
        let id = $(e.relatedTarget).data('user-id');
        console.log(addForm);
        fetch(urlFindUser + id)
            .then(res => res.json())
            .then(user => {
                let roles = user.roles
                $('#idDel').val(user.id);
                $('#firstNameDel').val(user.username);
                $('#lastNameDel').val(user.lastName);
                $('#ageDel').val(user.age);
                $('#emailDel').val(user.email);
                $('#passwordDel').val(user.password);
                $.each(roles, function (index, value) {
                    $('.delete-roles').val(value);
                    console.log(value.name)
                } )
            });

    });
    $('#deleteUser').on('click', function () {
        let user = deleteForm();
        console.log(user);
        $('.users-list').empty();
        tableUsers = '';
        fetch(urlDelUser , {
            method: 'DELETE',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(user)

        })
            .then(res => res.json())
            .then(data => loadTable());
    });

    $("#exampleModal ").on('show.bs.modal', function (e) {

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

    $("#exampleModal").on('hidden.bs.modal', function () {
        let form = $(this).find('form');
        form[0].reset();
    });

    $("#modalDelete ").on('show.bs.modal', function (e) {

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
        $('#emailDelete').val(email);
        $('#roleDelete').val(roles);
        $('#idDelete').val(userId);
    });

    $("#modalDelete").on('hidden.bs.modal', function () {
        let form = $(this).find('form');
        form[0].reset();
    });


    $("#new-user").click(function () {
        inputRoles();
        // $('.users-list').empty();
        $("#users").addClass('hidden');
        $("#add-user").removeClass('hidden');
        $("#users-table").removeClass('active');
        $("#new-user").addClass('active');
    });

    $("#users-table").click(function () {
        // loadTable();
        $("#add-user").addClass('hidden');
        $("#users").removeClass('hidden');
        $("#new-user").removeClass('active');
        $("#users-table").addClass('active');
    });
    $("#addSubmit").on('click', function () {
        // loadTable();
        newUser();
        $("#add-user").addClass('hidden');
        $("#users").removeClass('hidden');
        $("#new-user").removeClass('active');
        $("#users-table").addClass('active');

    });

})
