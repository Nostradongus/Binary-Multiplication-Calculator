$(document).ready (function () {
    function download(filename, text) {
        var element = document.createElement('a');
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
        element.setAttribute('download', filename);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    }

    $("#downloadBtn").click (function () {
        // alert (answer.answer)
        // alert ("yay")

        var fileContent = "";

        // Append Title
        fileContent += "Binary Multiplication Calculator\n\n";

        // Append Method Used
        fileContent += "Method Used: " + method + "\n\n";

        // Append Given
        fileContent += ("Given: \n");
        if (input.inputType == "DECIMAL") {
            fileContent += "Multiplicand: " + input.multiplicand + "\n";
            fileContent += "Multiplier: " + input.multiplier + "\n\n";
        } else {
            fileContent += "Multiplicand: " + answer.multiplicand + "\n";
            fileContent += "Multiplier: " + answer.multiplier + "\n\n";
        }

        // if DECIMAL input
        if (input.inputType == "DECIMAL") {
            // Append Conversion of Decimal to Binary
            fileContent += "Convert the Decimal operands to Binary:\n";
            fileContent += input.multiplicand + " --> " + answer.multiplicand + "\n";
            fileContent += input.multiplier + " --> " + answer.multiplier + "\n\n";
        }

        // if NOT Pen-And-Paper
        if (input.method != 0) {
            fileContent += "Convert the Multiplier operand to its corresponding " + method + " bit pair:\n";
            fileContent += input.multiplier + " --> " + answer.boothsEquivalent + "\n\n";
        }

        // Append Solution Proper
        fileContent += "Perform binary multiplication and acquire the product";
        if (!(answer.multiplicandTwosComplement == "")) {
            fileContent += ". Since multiplier is negative,\nadd 2's complement of multiplicand as the last intermediate to acquire correct result:\n"
        } else {
            fileContent += ":\n";
        }
        fileContent += "  " + answer.multiplicand + "\n";

        // If Pen-And-Paper, append multiplier as is
        if (input.method == 0)
            fileContent += "x " + answer.multiplier + "\n";
        // Else, append Booth's Equivalent
        else
            fileContent += "x " + answer.boothsEquivalent + "\n"

        // Append bar
        for (let i = 0; i < answer.multiplicand.length * 2 + 2; i++) {
            fileContent += "-";
        }
        fileContent += "\n";

        // Append each Intermediate
        answer.intermediates.forEach (function (intermediate) {
            fileContent += intermediate + "\n";
        })

        // Append bar
        for (let i = 0; i < answer.multiplicand.length * 2 + 2; i++) {
            fileContent += "-";
        }
        fileContent += "\n";

        // Append Answer
        fileContent += answer.answer + "\n\n";

        // Append Final Answer
        fileContent += "And the answer is: \n";
        fileContent += answer.multiplicand + " * ";
        if (input.method == 0)
            fileContent += answer.multiplier;
        else
            fileContent += answer.boothsEquivalent;
        fileContent += " = " + answer.answer;

        download ("solution.txt", fileContent);
    })

})

