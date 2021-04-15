

async function getTableUser(){
    let response = await fetch('admin/users');  // get all users
    let users = await response.json()

    let k = '<tbody>'
    for(let i = 0;i < users.length; i++){
    let roles = users[i].roles;
    let userRoles = "";
    roles.forEach(function (role){
        userRoles += role.role.substring(5) + " ";
    })
    k+= '<tr>';
    k+= '<td>' + users[i].id + '</td>';
    k+= '<td>' + users[i].name + '</td>';
    k+= '<td>' + users[i].lastName + '</td>';
    k+= '<td>' + users[i].age + '</td>';
    k+= '<td>' + users[i].username + '</td>';
    k+= '<td>' + userRoles + '</td>';
    k+= '<td>' + '<a href="/admin/' +users[i].id + '/user" class="btn btn-primary modBtn eBtn" >' + 'Edit' + '</a>' + '</td>';
    k+= '<td>' + '<a href="/admin/' +users[i].id + '/user" class="btn btn-danger modBtn dBtn" >' + 'Delete' + '</a>' + '</td>';
    k+= '</tr>';
    }
    k+='</tbody>';
    document.getElementById('importTable').innerHTML = k;

    let responseRole = await fetch('admin/roles');
    let roles = await responseRole.json();
    $('#newUserRole, #roleEdit').empty();
   // $('#roleEdit').empty();
    for(let i=0; i<roles.length; i++){
    $('#newUserRole').prepend('<option value=' + roles[i].role + '>' + roles[i].role.substring(5) + '');
    $('#roleEdit').prepend('<option value=' + roles[i].role+'>' + roles[i].role.substring(5) + '</option>');
    }
}

async function getPrincipal(href){  // получить авторизованного пользователя
    let response = await fetch(href);
    let users = await response.json();

    let t = '<tbody>'

    let roles = users.roles;
    let userRoles = "";
    roles.forEach(function (role){
        userRoles += role.role.substring(5) + " ";
    })
    t+= '<tr>';
    t+= '<td>' + users.id + '</td>';
    t+= '<td>' + users.name + '</td>';
    t+= '<td>' + users.lastName + '</td>';
    t+= '<td>' + users.age + '</td>';
    t+= '<td>' + users.username + '</td>';
    t+= '<td>' + userRoles + '</td>';
    t+= '</tr>';

    t+='</tbody>';
    document.getElementById('principalTable').innerHTML = t;
    $('#navbarText').text(users.username + ' with role: ' + userRoles)

}
    // Modal window
async function getModal(href, button){
    let response = await fetch(href); // get запрос user по id
    let user = await response.json();

    $('#inputIdEdit').val(user.id);
    $('#inputNameEdit').val(user.name);
    $('#inputLastNameEdit').val(user.lastName);
    $('#inputAgeEdit').val(user.age);
    $('#inputEmailEdit').val(user.username);
    let roles = user.roles;
    $('#roleEdit option:selected').each(function(){
        this.selected=false;
    });
    roles.forEach(function (role){
        $('#roleEdit option[value ='+ role.role +']').prop('selected', true)
    });

    if ($(button).hasClass('eBtn')){
        $('#modalTitle').text('Edit page');
        $('#modalButton').text('Edit').addClass('editBtn btn-primary').removeClass('delBtn btn-danger');
        $('.modInput').prop('readonly', false);
    } else {
        $('#modalTitle').text('Delete page');
        $('#modalButton').text('Delete').addClass('delBtn btn-danger').removeClass('editBtn btn-primary');
        $('.modInput').prop('readonly', true);
    }
    $('#myModal').modal('show');
}

    // Edit user
async function editUser(){
    let id =  $('#inputIdEdit').val();
    let firstName = $('#inputNameEdit').val();
    let lastName =  $('#inputLastNameEdit').val();
    let age = $('#inputAgeEdit').val();
    let username = $('#inputEmailEdit').val();
    let password = $('#inputPassEdit').val();
    let roles = $('#roleEdit').val()

    const user = {
    id: id,
    username: username,
    password: password,
    roles: roles,
    name: firstName,
    lastName: lastName,
    age: age
    };

    let response = await fetch('admin/edit', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
    },
        body: JSON.stringify(user)
    });

    $('#importTable').empty();
    await getTableUser();
    await getPrincipal('admin/principal');
    $('#myModal').modal('hide');
}

    // Delete user
async function deleteUser(){
    let id =  $('#inputIdEdit').val();
    let response = await fetch('admin/delete/' + id, {
        method: 'DELETE',
        headers: {
        'Content-Type': 'application/json;charset=utf-8'
        }
    });
    $('#importTable').empty();
    await getTableUser();

    $('#myModal').modal('hide');
}

    // New user
async function createNewUser(){
    let firstName = $('#inputName').val();
    let lastName = $('#inputLastName').val();
    let age = $('#inputAge').val();
    let username = $('#inputEmail').val();
    let password = $('#inputPass').val();
    let roles = $('#newUserRole').val();

    let user = {
        username: username,
        password: password,
        roles: roles,
        name: firstName,
        lastName: lastName,
        age: age
    };

    let response = await fetch('admin/add', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    },
    body: JSON.stringify(user)
    });

    if(response.ok) {
    $('#importTable').empty();
    await getTableUser();
    $('#nav-admin-tab').click();
    return false;
    } else {
        alert('Email already in use')
    }
}

