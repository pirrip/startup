<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(function () {
    var db = {};
    db.countries = [
        { Name: "", Id: 0 },
        { Name: "United States", Id: 1 },
        { Name: "Canada", Id: 2 },
        { Name: "United Kingdom", Id: 3 },
        { Name: "France", Id: 4 },
        { Name: "Brazil", Id: 5 },
        { Name: "China", Id: 6 },
        { Name: "Russia", Id: 7 }
    ];
    var grid = $("#user-grid").jsGrid({
        height: "500px",
        width: "100%",

        sorting: true,
        paging: true,
        autoload: true,
        editing:true,
        inserting: true,
        controller: {
            loadData: function(filter) {
                return $.ajax({
                    type: "GET",
                    url: "/web/static/plugins/jsgrid/client.json",
                    dataType: "json",
                    data: filter
                });
            },
              insertItem: function(insertingClient) {
                  console.log(this);
                  console.log(insertingClient);
// 		        this.clients.push(insertingClient);
              },

              updateItem: function(updatingClient) { },

              deleteItem: function(deletingClient) {
// 		        var clientIndex = $.inArray(deletingClient, this.clients);
// 		        this.clients.splice(clientIndex, 1);
              }
        },
        onDataLoaded: function(obj) {
            console.log(obj);
            console.log(this);
        },
        fields: [
            { name: "Name", type: "text", width: 150 },
            { name: "Age", type: "number", width: 50 },
            { name: "Address", type: "text", width: 200 },
            { name: "Country", type: "select", items: db.countries, valueField: "Id", textField: "Name" },
            { name: "Married", type: "checkbox", title: "Is Married" },
            { type: "control" }
        ]
    });
    $("#btn").on("click", function(){
        console.log(grid);
        var data = $("#user-grid").jsGrid("option", "data");
        console.log(data);
     })
  });
</script>