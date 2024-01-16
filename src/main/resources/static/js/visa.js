function showForm(formId) {
    var form1 = document.getElementById('form1');
    var form2 = document.getElementById('form2');

    // Hide both forms initially
    form1.classList.add('hidden-form');
    form2.classList.add('hidden-form');

    // Show the selected form
    document.getElementById(formId).classList.remove('hidden-form');
}

function submitForm(formId) {
    // You can add validation or other logic here
    document.getElementById(formId).submit();
}

function toggleInputs() {
    var inputContainer = document.getElementById("inputContainer");
    var toggleSwitch = document.getElementById("toggleSwitch");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitch.checked) {
        inputContainer.style.display = "block";
    } else {
        inputContainer.style.display = "none";
    }
}

function toggleInputs2() {
    var inputContainer2 = document.getElementById("inputContainer2");
    var toggleSwitch2 = document.getElementById("toggleSwitch2");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitch2.checked) {
        inputContainer2.style.display = "block";
    } else {
        inputContainer2.style.display = "none";
    }
}

function toggleInputsWork() {
    var inputContainerWork = document.getElementById("inputContainerWork");
    var toggleSwitchWork = document.getElementById("toggleSwitchWork");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitchWork.checked) {
        inputContainerWork.style.display = "block";
    } else {
        inputContainerWork.style.display = "none";
    }
}

function toggleInputsWork2() {
    var inputContainerWork2 = document.getElementById("inputContainerWork2");
    var toggleSwitchWork2 = document.getElementById("toggleSwitchWork2");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitchWork2.checked) {
        inputContainerWork2.style.display = "block";
    } else {
        inputContainerWork2.style.display = "none";
    }
}