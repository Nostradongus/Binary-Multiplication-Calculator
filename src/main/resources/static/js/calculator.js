function validateForm() {
    let multiplicand = document.getElementById('multiplicand').value;
    let multiplier = document.getElementById('multiplier').value;
    let binary = document.getElementById('binary');

    if ((!isBinary(multiplier) || !isBinary(multiplicand)) && binary.checked) {
        alert("Error! Only Binary digits (0-1) are allowed in Binary input type!");
        return false;
    }

    return true;
}

function isBinary(input) {
    for (let index = 0; index < input.length; index++) {
        let bit = input[index];
        if (bit != '0' && bit != '1') {
            return false;
        }
    }
    return true;
}
