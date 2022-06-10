var stompClient = null;


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('http://localhost:8101/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/chat/rooms/1', function (greeting) {
            console.log(greeting.toString())
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/pub/chat/messages", {}, JSON.stringify({
        'roomId': 1,
        'senderId': 1,
        'senderNickname': 'test',
        'message': $("#message").val()
    }));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showChattingRome() {
    $.ajax({

        url: "http://localhost:8101/api/pet/friends", // 어디로 갈거니? // 갈 때 데이터
        type: "get", // 타입은 뭘 쓸거니?
        datatype: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.setRequestHeader("api_key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJlbWFpbFR5cGUiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6InBldGJ1ZGR5IiwibmFtZSI6IuydgO2WiSIsImlhdCI6MTY1NDg1NzQzMSwidXNlcktleSI6MSwiZW1haWwiOiJza2dvZGRuczFAZ21haWwuY29tIn0.T7ntrN96V0HRK535HLWOD1RDRJaZ9X98XCq0VrKCFnejW34H9dyGLQZjerLUQ8oRwenXU3YMN55M38WrKLtpmg");
        },
        success: function (data) { // 갔다온 다음 결과값
            //	alert('seccuss');	// 나오면 파일을 찾았다는 것
            //	alert(data);  // [object Object],[object Object],[object Object]

            // 데이터를 확인하고 싶을 때.
            //	let str = JSON.stringify(data); // <> parse()
            //	alert(str);
            var results = data.response;
            var str = '<TR>';
            $.each(results, function (i) {
                str += '<TD>' + results[i].id + '<TD>' + results[i].petName + '</TD><TD>' + results[i].likesOfMe + '</TD>';
                str += '</TR>';
            });
            $("#chat-room").append(str);
        },
        error: function () {
            alert('error');
        }
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
    showChattingRome();
});


