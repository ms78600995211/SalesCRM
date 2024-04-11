<%-- 
    Document   : emergency
    Created on : 17 May, 2023, 4:37:33 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
            }

            h1 {
                text-align: center;
            }

            .container {
                max-width: 500px;
                margin: 0 auto;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }

            input[type="text"],
            input[type="email"],
            input[type="tel"],
            textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            button {
                display: block;
                width: 100%;
                padding: 10px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            button:hover {
                background-color: #45a049;
            }
        </style>
        <script>
            function getLocation() {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(showPosition);
                } else {
                    alert("Geolocation is not supported by this browser.");
                }
            }

            function showPosition(position) {
                var latitude = position.coords.latitude;
                var longitude = position.coords.longitude;
                document.getElementById("latitude").textContent = "Latitude: " + latitude;
                document.getElementById("lat").value = latitude;
                document.getElementById("longitude").textContent = "Longitude: " + longitude;
                document.getElementById("lng").value = longitude;
            }
        </script>
    </head>
    <body>
        <h1>Emergency Form</h1>
        <div class="container">
            <form name="form" method="POST" action="EmergencyDataController" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="message">File Upload:</label>
                    <input type="file" id="fileData" name="fileData" placeholder="Upload DL Image">
                </div>
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" placeholder="Enter your name">
                    <input type="hidden" id="typeE" name="typeE" value="${type}">
                    <input type="hidden" id="lat" name="lat" value="">
                    <input type="hidden" id="lng" name="lng" value="">
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone" placeholder="Enter your phone number">
                </div>

                <div class="form-group">
                    <label for="email">Address:</label>
                    <input type="text" id="address" name="address" placeholder="Enter your Address">
                </div>

                <div class="form-group">
                    <label for="email">DL No:</label>
                    <input type="text" id="dl_no" name="dl_no" placeholder="Enter your DL Number">
                </div>
                <div class="form-group">
                    <label for="message">Message:</label>
                    <textarea id="message" name="message" rows="5" placeholder="Enter your message"></textarea>
                </div>
                <!--                <div class="form-group">
                                    <label for="message">File Upload:</label>
                                    <input type="file" id="fileData" name="fileData" placeholder="Upload DL Image">
                                </div>-->
                <div>
                    <input type="button" name="Get" value="Get Location" onclick="getLocation()">
                    <p id="latitude" name="latitude"></p>
                    <p id="longitude" name="longitude"></p>
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
    </body>
</html>
