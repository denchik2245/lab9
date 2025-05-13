const API_URL = '/api/items';

function fetchItems() {
    $.get(API_URL)
        .done(data => {
            const $list = $('#items').empty();
            data.forEach(item => {
                const li = $('<li>');
                const text = item.bought ? `<s>${item.name}</s>` : item.name;
                li.html(text + ' ');

                if (!item.bought) {
                    $('<button>Bought</button>')
                        .click(() => markBought(item.id))
                        .appendTo(li);
                }

                $('<button>Delete</button>')
                    .click(() => deleteItem(item.id))
                    .appendTo(li);

                $list.append(li);
            });
        })
        .fail(err => console.error('GET failed', err));
}

function addItem(name) {
    $.ajax({
        url: API_URL,
        type: 'POST',
        data: JSON.stringify({ name }),
        contentType: 'application/json',
        dataType: 'json'
    })
        .done(() => {
            $('#itemName').val('');
            fetchItems();
        })
        .fail(err => console.error('POST failed', err));
}

function markBought(id) {
    $.ajax({
        url: `${API_URL}/${id}/bought`,
        type: 'PUT'
    })
        .done(fetchItems)
        .fail(err => console.error('PUT failed', err));
}

function deleteItem(id) {
    $.ajax({
        url: `${API_URL}/${id}`,
        type: 'DELETE'
    })
        .done(fetchItems)
        .fail(err => console.error('DELETE failed', err));
}

$(function() {
    $('#addForm').submit(e => {
        e.preventDefault();
        const name = $('#itemName').val().trim();
        if (name) addItem(name);
    });

    fetchItems();
});
