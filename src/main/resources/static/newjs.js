console.log('Новый js')
const url = 'http://localhost:8080/klient';
const urlAdd = 'http://localhost:8080/add_user';
const urlRoles = 'http://localhost:8080/roles';
const urlFindUser = 'http://localhost:8080/findOne/';

const usersList = document.querySelector('.users-list');
const addUserForm = document.querySelector('.add-user-form');
const selectRoles = document.querySelector('.select-roles');
let tableUsers = '';
let addForm = '';
let userRoles = [{}];

const renderUsers = (users) => {
    users.forEach(user => {
        let roles = user.roles;
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
                                </button></td> `;
        tableUsers += `<td><button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#delModal"> 
                                Delete
                                </button></td>`;
    });
    usersList.innerHTML = tableUsers;
}

// Get - Read users
// Method: GET
createRoles();
fetch(url)
    .then(res => res.json())
    .then(data => renderUsers(data))

// Create - insert new user
// Method: POST
addUserForm.addEventListener('submit', (e) => {
    e.preventDefault()
    console.log('Submit')

    newUser();
    inputRoles();
})

function inputRoles() {
    console.log("Отработала функция inputRoles")
    fetch(urlRoles)
        .then(res => res.json())
        .then(data => {
            console.log(data)
            let count = 0;
            data.forEach(role => {
                userRoles[count] = {
                    id: role.id,
                    name: role.name
                }
                count++;
            });
        });
    return(userRoles);
}

// function createRoles() {
//     console.log("Отработала функция createRoles")
//     fetch(urlRoles)
//         .then(res => res.json())
//         .then(data => {
//             data.forEach(role => {
//                 addForm += `<option id="${role.id}"> ${role.name} </option>`;
//             });
//             selectRoles.innerHTML = addForm;
//         });
// }

function newUser() {
// $('#addSubmit').on('click', function (){
    let roles = [{}];
    roles = inputRoles();
    console.log(roles);
    console.log('Получено ролей - ' + roles.length)
    // usersList.remove();
    let user = {
        username: $('#add-username').val(),
        lastName: $('#add-last-name').val(),
        age: $('#add-age').val(),
        password: $('#add-password').val(),
        email: $('#add-email').val(),
        roles: $('.select-roles').val()
    }
    console.log('User ')
    console.log(user)
    console.log('Roles')
    console.log(roles)

    $.each(user.roles, function (index, value) {
        console.log('Индекс итерация по введеным ролям : ' + index, 'Значение : ' + value)
        $.each(roles, function (ind, item) {
            console.log('Итерация по Ролям из БД ключ - ' + ind, 'Значение : ' + item.name)
            if (value === item.name) {
                console.log('Есть совпадение ролей- ' + value + ' Значение ключа  - ' + index);
                user.roles.splice(ind, index);
                user.roles[index] = roles[ind]
            }
        })
    })
    console.log('User ')
    console.log(user)
}