function validateForm() {
    let multiplicand = document.getElementById('multiplicand').value;
    let multiplier = document.getElementById('multiplier').value;
    let binary = document.getElementById('binary');

    console.log(multiplicand);
    console.log(multiplier);
    console.log(binary.checked);

    if (!isBinary(multiplier) && !isBinary(multiplicand) && binary.checked) {
        alert("Error! Only Binary digits (0-1) are allowed in Binary input type!");
        return false;
    }

    return true;
}

function isBinary(input) {
    for (let index = 0; index < input.length; index++) {
        if (input[index] !== '0' || input[index] !== '1') {
            return false;
        }
    }
    return true;
}
