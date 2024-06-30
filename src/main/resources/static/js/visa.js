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
    if (formId === 'form2'){
        // input fields
        // -> dropdown
        var visa2 = document.getElementById('visa2').value;
        var surname2 = document.getElementById('surname2').value.trim();
        var name2 = document.getElementById('name2').value.trim();
        // -> date
        var calendar2 = document.getElementById('calendar2').value.trim();
        // -> dropdown
        var sex2 = document.getElementById('sex2').value.trim();
        var nationality2 = document.getElementById('nationality2').value.trim();
        // -> check numbers
        var idNumber2 = isFirstApplicationIdValue(visa2, document.getElementById('idNumber2').value.trim());
        var passno2 = document.getElementById('passno2').value.trim();
        // -> date
        var passdate2 = document.getElementById('passdate2').value.trim();
        // -> date
        var passexp2 = document.getElementById('passexp2').value.trim();
        var koraddress2 = document.getElementById('koraddress2').value.trim();
        // -> check numbers && check if starts with 010
        var telno2 = document.getElementById('telno2').value.trim();
        var homeaddress2 = document.getElementById('homeaddress2').value.trim();

        // error msg fields
        var visa2err = document.getElementById('visa2err');
        var surname2err = document.getElementById('surname2err');
        var name2err = document.getElementById('name2err');
        var calendar2err = document.getElementById('calendar2err');

        var sex2err = document.getElementById('sex2err');
        var nationality2err = document.getElementById('nationality2err');
        var idNumber2err = document.getElementById('idNumber2err');
        var passno2err = document.getElementById('passno2err');
        var passdate2err = document.getElementById('passdate2err');
        var passexp2err = document.getElementById('passexp2err');
        var koraddress2err = document.getElementById('koraddress2err');
        var telno2err = document.getElementById('telno2err');
        var homeaddress2err = document.getElementById('homeaddress2err');
        // Reset error messages
        visa2err.textContent = '';
        surname2err.textContent = '';
        name2err.textContent = '';
        calendar2err.textContent = '';
        sex2err.textContent = '';
        nationality2err.textContent = '';
        idNumber2err.textContent = '';
        passno2err.textContent = '';
        passdate2err.textContent = '';
        passexp2err.textContent = '';
        koraddress2err.textContent = '';
        telno2err.textContent = '';
        homeaddress2err.textContent = '';

        //validation
        //dropdown not null
        isDropDownNotNull(visa2, visa2err)
        //validation not null
        isNotNull(surname2, surname2err, "Фамилия")
        isNotNull(name2, name2err, "Имя")
        //check date
        isDataNotNull(calendar2, calendar2err, "Дата рождения")
        //dropdown
        if(isDropDownNotNull(sex2,sex2err)){}
        isNotNull(nationality2, nationality2err, "Гражданство")
        // should be digits and exactly 13
        isDigitsCharsLong(idNumber2, idNumber2err, "13", visa2, true)
        isNotNull(passno2, passno2err, "Номер паспорта")
        isDataNotNull(passdate2, passdate2err, "Дата выдачи паспорта")
        isDataNotNull(passexp2,passexp2err, "Срок годности паспорта")
        isNotNull(koraddress2,koraddress2err, "Адрес проживания в Корее.")
        isDigitsCharsLong(telno2, telno2err, "11")|| isMobile(telno2, telno2err)
        isNotNull(homeaddress2, homeaddress2err, "Адрес проживания на родине.")

        //if all is filled submit
        if(visa2!=="0"&&
            surname2 !==""&&
            name2 !==""&&
            !isNaN(Date.parse(calendar2))&&
            sex2!=="0"&&
            nationality2 !==""&&
            isDigitsCharsLong(idNumber2, idNumber2err,"13", visa2, true)&&
            !isNaN(Date.parse(passdate2))&&
            !isNaN(Date.parse(passexp2))&&
            koraddress2!==""&&
            isDigitsCharsLong(telno2, telno2err, "11")&&
            isMobile(telno2, telno2err)&&
            homeaddress2!==""
        ){
            document.getElementById(formId).submit();

        }
        else {
            alert("Некоторые поля заполнены неверно. Проверьте сообщения, выделенные красным.")
        }
    }

    else if (formId === 'form1'){
        // input fields
        // -> dropdown
        var visa = document.getElementById('visa').value;
        var surname = document.getElementById('surname').value.trim();
        var name = document.getElementById('name').value.trim();
        // -> date
        var calendar = document.getElementById('calendar').value.trim();
        // -> dropdown
        var sex = document.getElementById('sex').value.trim();
        var nationality = document.getElementById('nationality').value.trim();
        // -> check numbers
        var idNumber = isFirstApplicationIdValue(visa, document.getElementById('idNumber').value.trim());
        var passno = document.getElementById('passno').value.trim();
        // -> date
        var passdate = document.getElementById('passdate').value.trim();
        // -> date
        var passexp = document.getElementById('passexp').value.trim();
        var koraddress = document.getElementById('koraddress').value.trim();
        // -> check numbers && check if starts with 010
        var telno = document.getElementById('telno').value.trim();
        var homeaddress = document.getElementById('homeaddress').value.trim();

        // error msg fields
        var visaerr = document.getElementById('visaerr');
        var surnameerr = document.getElementById('surnameerr');
        var nameerr = document.getElementById('nameerr');
        var calendarerr = document.getElementById('calendarerr');

        var sexerr = document.getElementById('sexerr');
        var nationalityerr = document.getElementById('nationalityerr');
        var idNumbererr = document.getElementById('idNumbererr');
        var passnoerr = document.getElementById('passnoerr');
        var passdateerr = document.getElementById('passdateerr');
        var passexperr = document.getElementById('passexperr');
        var koraddresserr = document.getElementById('koraddresserr');
        var telnoerr = document.getElementById('telnoerr');
        var homeaddresserr = document.getElementById('homeaddresserr');
        // Reset error messages
        visaerr.textContent = '';
        surnameerr.textContent = '';
        nameerr.textContent = '';
        calendarerr.textContent = '';
        sexerr.textContent = '';
        nationalityerr.textContent = '';
        idNumbererr.textContent = '';
        passnoerr.textContent = '';
        passdateerr.textContent = '';
        passexperr.textContent = '';
        koraddresserr.textContent = '';
        telnoerr.textContent = '';
        homeaddresserr.textContent = '';

        //validation
        //dropdown not null
        if(isDropDownNotNull(visa, visaerr)){
        }
        //validation not null
        isNotNull(surname, surnameerr, "Фамилия")
        isNotNull(name, nameerr, "Имя")
        //check date
        isDataNotNull(calendar, calendarerr, "Дата рождения")
        //dropdown
        isDropDownNotNull(sex,sexerr)
        isNotNull(nationality, nationalityerr, "Гражданство")
        // should be digits and exactly 13
        isDigitsCharsLong(idNumber, idNumbererr, "13", visa, true)
        isNotNull(passno, passnoerr, "Номер паспорта")
        isDataNotNull(passdate, passdateerr, "Дата выдачи паспорта")
        isDataNotNull(passexp,passexperr, "Срок годности паспорта")
        isNotNull(koraddress,koraddresserr, "Адрес проживания в Корее.")
        isDigitsCharsLong(telno, telnoerr, "11")|| isMobile(telno, telnoerr)
        isNotNull(homeaddress, homeaddresserr, "Адрес проживания на родине.")

        //if all is filled submit
        if(visa!=="0"&&
            surname !==""&&
            name !==""&&
            !isNaN(Date.parse(calendar))&&
            sex!=="0"&&
            nationality !==""&&
            isDigitsCharsLong(idNumber, idNumbererr,"13", visa, true)&&
            !isNaN(Date.parse(passdate))&&
            !isNaN(Date.parse(passexp))&&
            koraddress!==""&&
            isDigitsCharsLong(telno, telnoerr, "11")&&
            isMobile(telno, telnoerr)&&
            homeaddress!==""
        ){
            //document.getElementById('visatype').value = /*[[${'other'}]]*/ '';
            document.getElementById(formId).submit();
        }
        else {
            alert("Некоторые поля заполнены неверно. Проверьте сообщения, выделенные красным.")
        }

    }
}

function toggleInputs() {
    var inputContainer = document.getElementById("inputContainer");
    var toggleSwitch = document.getElementById("toggleSwitch");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitch.checked) {
        toggleSwitch.value = "true";
        inputContainer.style.display = "block";
    } else {
        toggleSwitch.value = "false";
        inputContainer.style.display = "none";
    }
}

function toggleInputs2() {
    var inputContainer2 = document.getElementById("inputContainer2");
    var toggleSwitch2 = document.getElementById("toggleSwitch2");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitch2.checked) {
        toggleSwitch2.value = "true";
        inputContainer2.style.display = "block";
    } else {
        toggleSwitch2.value = "false";
        inputContainer2.style.display = "none";
    }
}

function updateFields(formId){

    if(formId === 'form1'){
        var visa = document.getElementById('visa').value;
        document.getElementById("inputContainerWork").style.display = "none";
        document.getElementById("toggleSwitchWork").checked = false;
        if(visa === "впервые" ){
            document.getElementById('idNumber').type = "hidden";
        }
        else{
            document.getElementById('idNumber').type = "text";
        }
    }
    else if(formId === 'form2'){
        var visa = document.getElementById('visa2').value;
        if(visa === "впервые"){
            document.getElementById('idNumber2').type = "hidden";
        }
        else{
            document.getElementById('idNumber2').type = "text";
        }
    }


}

function toggleInputsWork() {
    var inputContainerWork = document.getElementById("inputContainerWork");
    var toggleSwitchWork = document.getElementById("toggleSwitchWork");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitchWork.checked) {
        toggleSwitchWork.value = "true";
        inputContainerWork.style.display = "block";
        if(document.getElementById('visa').value === "работа"){
            document.getElementById('nWorkPlace').type = "text";
            document.getElementById('nWorkId').type = "text";
            document.getElementById('nWorkNum').type = "text";
        }
        else{
            document.getElementById('nWorkPlace').type = "hidden";
            document.getElementById('nWorkId').type = "hidden";
            document.getElementById('nWorkNum').type = "hidden";
        }
    } else {
        toggleSwitchWork.value = "false";
        inputContainerWork.style.display = "none";
    }
}

function toggleInputsWork2() {
    var inputContainerWork2 = document.getElementById("inputContainerWork2");
    var toggleSwitchWork2 = document.getElementById("toggleSwitchWork2");

    // Toggle the visibility of input fields based on the toggle switch
    if (toggleSwitchWork2.checked) {
        toggleSwitchWork2.value = "true";
        inputContainerWork2.style.display = "block";
    } else {
        toggleSwitchWork2.value = "false";
        inputContainerWork2.style.display = "none";
    }
}

function isNotNull(value, errorElement, varName) {
    if (value === '') {
        errorElement.textContent = 'Пожалуйста заполните графу " '+ varName + ' ".';
        return false;
    }
    return true;
}

function isDropDownNotNull (value, errorElement){
    if(value === '0'){
        errorElement.textContent = 'Пожалуйста выберите значение.';
        return false;
    }
    return true;
}

function isDataNotNull(value, errorElement, varName){
    if(isNaN(Date.parse(value))){
        errorElement.textContent='Пожалуйста заполните графу " '+ varName + ' ".';
        return false;
    }
    return true;
}

function isDigitsCharsLong(value, errorElement, number, visa = "smth", isIdCheck=false) {
    var num = parseInt(number);
    if(isIdCheck && visa === "впервые") return true;
    else if (value.length !== num || !/^\d+$/.test(value)) {
        errorElement.textContent = 'Введите '+ number+'ти значное число.';
        return false;
    }
    return true;
}
function isFirstApplicationIdValue(visa, id){
    if(visa === "впервые"){
        return "*************";
    }
    else return id;
}

function isMobile(value, errorElement){
    if(!value.startsWith("010")){
        errorElement.textContent = "Номер телефона должен начинаться с 010";
        return false;
    }
    return true;
}
