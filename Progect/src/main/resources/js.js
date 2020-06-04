
function WatchText() {
    var items = [];
    var spin = `<div id="Spin" class="spinner-border text-warning" role="status"><span class="sr-only">Loading...</span></div>`;
    $("#dl").html(spin);
    $.ajax({
        method: 'GET',
        url:'/get_all_users',
        success:function (data) {

            items.push(data);
            $("#dl").html(items);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function Search() {
    var param = document.getElementById("select").value;
    var value = document.getElementById("FormSearch").value;
    var items = [];

    var spin = `<div id="Spin" class="spinner-border text-warning" role="status"><span class="sr-only">Loading...</span></div>`;
    $("#dl").html(spin);

    $.ajax({
        method: 'GET',
        url:'/find&' + param + '&' + value,
        success:function (data) {

            items.push(data);
            $("#dl").html(items);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function SearchToDel(){
    var param = document.getElementById("select").value;
    var value = document.getElementById("FormSearch").value;
    var items = [];

    var spin = `<div id="Spin" class="spinner-border text-warning" role="status"><span class="sr-only">Loading...</span></div>`;
    $("#dl").html(spin);

    $.ajax({
        method: 'GET',
        url:'/find&' + param + '&' + value,
        success:function (data) {

            if(data !== "<dt>Not found</dt>")
                $("#Modal").modal("show");
            else
                $("#ModalError").modal("show");
            items.push(data);
            $("#dl").html(items);
        },
        error: function (error) {
            console.log(error);
        }
    });
}


function Delete() {
    var param = document.getElementById("select").value;
    var value = document.getElementById("FormSearch").value;
    var items = [];

    var spin = `<div id="Spin" class="spinner-border text-warning" role="status"><span class="sr-only">Loading...</span></div>`;
    $("#dl").html(spin);

    $.ajax({
        method: 'GET',
        url: '/delete&' + param + '&' + value,
        success: function (data) {
            items.push(data);
            $("#dl").html(items);
        },
        error: function (error) {
            console.log(error);
        }
    });

    value = document.getElementById("FormSearch");
    value.value = "";

}

function SortByName(){
    var items = [];

    var spin = `<div id="Spin" class="spinner-border text-warning" role="status"><span class="sr-only">Loading...</span></div>`;
    $("#dl").html(spin);

    $.ajax({
        method: 'GET',
        url: '/sort_by_name',
        success: function (data) {
            items.push(data);
            $("#dl").html(items);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function SortByAge(){
    var items = [];

    var spin = `<div id="Spin" class="spinner-border text-warning" role="status"><span class="sr-only">Loading...</span></div>`;
    $("#dl").html(spin);

    $.ajax({
        method: 'GET',
        url: '/sort_by_age',
        success: function (data) {
            items.push(data);
            $("#dl").html(items);
        },
        error: function (error) {
            console.log(error);
        }
    });
}
function UpdateModal(element) {
    var dl = document.getElementById(element.id);
    var modal = document.getElementById("Edit");
    var span = document.getElementById("EditSpan");
    var another ="";

    dl.dataset.target = "#ModalUpdate";
    dl.dataset.toggle = "modal";

    var id = dl.textContent.split(',');
    span.textContent = id[0];

    for(i = 1; i < id.length; ++i)
        another += id[i] + ",";
    another = another.substring(0,another.length -1);

    modal.value = another;

    console.log(span);
}

function Update() {
    var items =[];
    var modal = document.getElementById("Edit").value;
    var span = document.getElementById("EditSpan").textContent;
    console.log(modal);

    span += "," + modal;
    $.ajax({
        method: 'GET',
        url: '/update' + "&" + span,
        success: function (data) {
            items.push(data);
            $("#dl").html(items);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function Add(){
    var url ="";
    let item;
    var items =[];
    var main =[];
    var vis =[];
    var flag = 0;
    item = document.getElementsByClassName("form-control");
    for(i= 0; i < item.length; ++i) {

        if(i === 0)
            url = url + "&name";
        else if(i === 1)
            url+="&age";
        else if (i === 2)
            url+="&phone";
        if(item[i].value !== "")
            url = url + "&" + item[i].value;
    }


    main[0] = document.getElementById("Name");
    main[1] = document.getElementById("Age");
    main[2] = document.getElementById("Phone");

    vis[0] = document.getElementById("NameVis");
    vis[1] = document.getElementById("AgeVis");
    vis[2] = document.getElementById("PhoneVis");

    for(i = 0; i < 3; ++i){
        if(main[i].value === "") {
            vis[i].style.visibility = "visible";
            flag = 1;
        }
        else
            vis[i].style.visibility = "hidden";

    }

    if(flag === 0) {

        $.ajax({
            method: 'GET',
            url: '/insert' + url,
            success: function (data) {
                items.push(data);
                $("#dl").html(items);
            },
            error: function (error) {
                console.log(error);
            }
        });

        for(i = 0; i < item.length; ++i)
            item[i].value ="";

    }
}