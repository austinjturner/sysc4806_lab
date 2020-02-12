
$(document).ready(function() {
    loadAddressBooks();
});

function loadAddressBooks(){
    // We're starting from scratch...
    $(document.body).empty();

    $(document.body).append('<button id="addressBookBtn" type="button">Create a new address book </button><br><hr>');
    $('#addressBookBtn').click(function(e) {
        $.ajax({
            type: 'POST',
            url: "/address-book",
            dataType: "json",
            success: function(resultData) {
                loadAddressBooks();
            }
        });
    });

    $.ajax({
        type: 'GET',
        url: "/address-book",
        dataType: "json",
        success: function(resultData) {
            for (var i = 0; i < resultData.length; i++){
                function set(book){
                    var book = resultData[i];
                    var elementId = 'loadAddressBook'+book.id+'Btn';
                    $(document.body).append('<button id="'+elementId+'" type="button">View Address Book '+book.id+'</button><br>');
                    $('#'+elementId).click(function(){
                        loadAddressBook(book.id)
                    })
                }
                set(resultData[i]);


            }
        }
    });
}

function loadAddressBook(addressBookId){
    console.log(addressBookId);
    $.ajax({
        type: 'GET',
        url: "/address-book/"+addressBookId,
        dataType: "json",
        success: function(resultData) {
            var buddies = resultData.buddyInfos;

            // cleanup
            $('.buddyTable').remove();
            $('.addBuddyForm').remove();
            $('.addBuddyBtn').remove();
            console.log(resultData);

            // un-hide the buddy stuff
            // Add the (still empty) table
            $(document.body).append('<table class="buddyTable">\n' +
                '        <thead>\n' +
                '        <tr>\n' +
                '            <th> Name </th>\n' +
                '            <th> Phonenumber </th>\n' +
                '            <th> Address </th>\n' +
                '            <th> Age </th>\n' +
                '            <th> Ranking </th>\n' +
                '        </tr>\n' +
                '        </thead>\n' +
                '        <tbody id="buddyTableBody">\n' +
                '        </tbody>\n' +
                '    </table>');


            // Add buddies
            if (buddies.length === 0){
                $('#buddyTableBody').append('<tr id="loner"><td colspan="4"> You have no friends! </td></tr>')
            } else {
                for (var i = 0; i < buddies.length; i++){
                    var row = '<tr>';
                    row += '<td>'+buddies[i].name+'</td>';
                    row += '<td>'+buddies[i].phonenumber+'</td>';
                    row += '<td>'+buddies[i].address+'</td>';
                    row += '<td>'+buddies[i].age+'</td>';
                    row += '<td>'+buddies[i].ranking+'</td>';
                    row += '</tr>';
                    $('#buddyTableBody').append(row);
                }
            }
            var buddyFormBody = "<form class=\"addBuddyForm\">\n" +
                "        Name:<br>\n" +
                "        <input type=\"text\" name=\"name\"><br>\n" +
                "        Phone Number:<br>\n" +
                "        <input type=\"text\" name=\"phonenumber\"><br>\n" +
                "        Address:<br>\n" +
                "        <input type=\"text\" name=\"address\"><br>\n" +
                "        Ranking:<br>\n" +
                "        <input type=\"text\" name=\"ranking\"><br><br>\n" +
                "    </form>";
            $(document.body).append(buddyFormBody);

            $(document.body).append('<button class="addBuddyBtn" type="button">Add a new buddy</button>')
            $('.addBuddyBtn').click(function(){
                var array = $(".addBuddyForm").serializeArray();
                var formData = {};
                for (var j = 0; j < array.length; j++){
                    formData[array[j].name] = array[j].value;
                }
                formData = JSON.stringify(formData)
                //console.log(formData);
                $.ajax({
                    type: "POST",
                    url: "/address-book/"+addressBookId+"/buddy",
                    data: formData,
                    success: function(){
                        loadAddressBook(addressBookId);
                    },
                    error: function(err){
                        console.log(err);
                    },
                    dataType: "json",
                    contentType : "application/json"
                });
            });
        }
    });
}