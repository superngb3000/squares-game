const API_URL = "http://localhost:8080/api/"
const BOARD_MIN_SIZE = 3

var API = {
  getNextMove : async (requestBody) => {
    let url = API_URL + "nextMove";
    return API.postRequest(url, requestBody);
  },

  getGameStatus : async (requestBody) => {
    let url = API_URL + "gameStatus";
    return API.postRequest(url, requestBody);
  },

  postRequest : async (url, requestBody) => {
    let response = await fetch(url, {
        method : "POST",
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify(requestBody)
    });
    let json = await response.json();
    return json;
  }
}

const playerTypes = {
    user : {
        name : "USER",
        text : "user"
    },
    computer : {
        name : "COMP",
        text : "computer"
    }
}

var gameSettings = {
    boardSize : 3,
    "W" : {
        name : "W",
        class : "cell white",
        playerType : playerTypes.user
    },
    "B" : {
        name : "B",
        class : "cell black",
        playerType : playerTypes.computer
    },
    " " : {
        name : " ",
        class : "cell empty"
    },
    nextMove : "W"
}

var board = [];

var render = {
    renderBoard : () => {
        let boardElem = document.getElementById("board");
        boardElem.classList.remove("static");
        let boardSize = gameSettings.boardSize;
        document.documentElement.style.setProperty("--board-size", boardSize);
        boardElem.innerHTML = "";

        for (let i = 0; i < boardSize; i++) {
            for (let j = 0; j < boardSize; j++) {
                let cellElem = document.createElement("div");
                cellElem.className = gameSettings[board[i][j]].class;
                cellElem.id = `cell-${i}-${j}`;
                cellElem.addEventListener("click", async () => { await handlers.move(i, j, cellElem) });
                boardElem.appendChild(cellElem);
            }
        }
    },

    reRenderCell : (x, y, color, elem) => {
        if (!elem) elem = document.getElementById(`cell-${x}-${y}`);
        let newCell = document.createElement("div");
        newCell.className = gameSettings[color].class;
        newCell.id = `cell-${x}-${y}`;
        newCell.addEventListener("click", async () => { await handlers.move(x, y, newCell) });
        elem.replaceWith(newCell);
    },

    renderStatus : (message) => {
        document.getElementById("status").textContent = message;
    }
}

var handlers = {
    newGame : async () => {
        let size = parseInt(document.getElementById("boardSize").value);
        let whitePlayer = document.getElementById("whitePlayerType").value;
        let blackPlayer = document.getElementById("blackPlayerType").value;

        gameSettings.boardSize = size;
        gameSettings.W.playerType = playerTypes[whitePlayer];
        gameSettings.B.playerType = playerTypes[blackPlayer];
        gameSettings.nextMove = "W";

        board = Array.from({ length: size }, () => Array(size).fill(gameSettings[" "].name));

        document.getElementById("status").classList.remove("hide");
        let message = "New game started. White goes first!";
        render.renderStatus(message);
        render.renderBoard();

        if (gameSettings[gameSettings.nextMove].playerType === playerTypes.computer) await handlers.autoMove();
    },

    move : async (x, y, elem) => {
        if (board[x][y] !== gameSettings[" "].name) return;
        board[x][y] = gameSettings.nextMove;
        gameSettings.nextMove = (gameSettings.nextMove === gameSettings.B.name) ? gameSettings.W.name : gameSettings.B.name;
        render.reRenderCell(x, y, board[x][y], elem);

        let gridString  = board.flat().join('');
        let requestBody = {
            size : gameSettings.boardSize,
            data : gridString
        };
        let gameStatus = await API.getGameStatus(requestBody);
        if (gameStatus.status !== "ONGOING") {
            let message = (gameStatus.playerColor) ? `Game finished. ${gameStatus.playerColor} wins!` : `Game finished. Draw`;
            render.renderStatus(message);
            let boardElem = document.getElementById("board");
            boardElem.classList.add("static");
            return;
        }

        if (gameSettings[gameSettings.nextMove].playerType === playerTypes.computer) await handlers.autoMove();
    },

    checkInputSize : (e) => {
        let minValue = parseInt(e.target.min);
        let newValue = e.target.value;
        if (newValue < minValue) e.target.value = minValue;
    },

    autoMove : async () => {
        let gridString  = board.flat().join('');
        let requestBody = {
            size : gameSettings.boardSize,
            data : gridString,
            nextPlayerColor : gameSettings.nextMove
        };
        let move = await API.getNextMove(requestBody);

        let boardElem = document.getElementById("board");
        boardElem.classList.add("static");
        setTimeout(() => { 
            handlers.move(move.x, move.y);
            boardElem.classList.remove("static"); 
        }, 500);
    }
}

document.getElementById("newGame").addEventListener("click", async () => { await handlers.newGame() })
document.getElementById("boardSize").addEventListener("change", async (e) => { await handlers.checkInputSize(e) })