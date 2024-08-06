const express = require("express");
const app = express();
const http = require("http");
const path = require("path");
const socketio = require("socket.io");

const server = http.createServer(app);
const io = socketio(server);


app.set("view engine", "ejs");

app.use(express.static(path.join(__dirname, "public")));

app.set("views", path.join(__dirname, "views"));

const userSessions = {};

io.on("connection", function (socket) {
  console.log("A user connected");

  socket.on("join-session", (sessionId) => {
    socket.join(sessionId);
    userSessions[socket.id] = sessionId;
    console.log(`User joined session: ${sessionId}`);
  });

  socket.on("send-location", function (data) {
    console.log("Location data received on server:", data);
    const sessionId = userSessions[socket.id];
    if (sessionId) {
      io.to(sessionId).emit("receive-location", { id: socket.id, ...data });
    }
  });

  socket.on("disconnect", function () {
    const sessionId = userSessions[socket.id];
    if (sessionId) {
      io.to(sessionId).emit("user-disconnected", socket.id);
      delete userSessions[socket.id];
    }
  });
});

app.get("/", function (req, res) {
  res.render("index");
});

app.get("/track/:id", function (req, res) {
  res.render("track", { sessionId: req.params.id });
});


server.listen(3000, function () {
  console.log("Server is running on http://localhost:3000");
});
