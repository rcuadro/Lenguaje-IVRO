const { readFileSync } = require("fs");
const readline = require('readline');

const insrc = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

entrada = [];
i = 0; 

async function readInput(n) {
    if (n == 0) return; // No hacer nada si el número de lecturas es 0

    var line;
    for await (line of insrc) {
        entrada.push(parseInt(line));
        n--;
        if (n == 0) {
            insrc.close();
            break;
        }
    }
    
    return;
}

var importObjects = {
    runtime: {
        exceptionHandler: function(code) {
            let errStr;
            if (code == 1) {
                errStr = "Error 1. ";
            } else if (code == 2) {
                errStr = "Error 2. ";
            } else if (code == 3) {
                errStr = "Not enough memory. ";
            } else {
                errStr = "Unknown error\n";
            }
            throw new Error(errStr);
        },
        print: function(n) {
            console.log(n);
        },
        read: function() {
            let val = entrada[i];
            i += 1;
            return val;
        }
    }
};

async function start() {
    const code = readFileSync("codigo.wasm");
    wasmModule = await WebAssembly.compile(code);
    instance = await WebAssembly.instantiate(wasmModule, importObjects);
//    await instance.exports.init();
}

async function run() {
    const numLecturas = parseInt(process.argv[2]) || 0; // Obtener el número de lecturas desde los argumentos
    await readInput(numLecturas);
    
    start();
}

run();

