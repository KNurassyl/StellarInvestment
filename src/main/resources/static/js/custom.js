let calendar;
var currentTabIndex = 0;

function showPreviousStepButton() {
    var previousStepButton = document.getElementById('previousStep');
    if (currentTabIndex === 0) {
        // Hide the button on the first tab
        previousStepButton.style.display = 'none';
    } else {
        // Show the button on other tabs
        previousStepButton.style.display = 'block';
    }
}

function showTab(tabName) {
    // Remove outline from all tabs
    var allTabs = document.querySelectorAll('.tab');
    allTabs.forEach(function(tab) {
        tab.style.outline = 'none';
    });

    // Set outline for the clicked tab
    var clickedTab = document.getElementById(tabName + 'Tab');
    clickedTab.style.outline = '2px solid rgb(255, 127, 80)';

    // Hide all tab contents
    var tabContents = document.getElementsByClassName("tab-content");
    for (var i = 0; i < tabContents.length; i++) {
        tabContents[i].style.display = "none";
    }

    // Show the selected tab content
    document.getElementById(tabName + "Content").style.display = "block";

    // Update the visibility of the "Previous Step" button
    showPreviousStepButton();
}


function showPreviousStep() {
    // Handle logic for the "Previous Step" button
    if (currentTabIndex > 0) {
        currentTabIndex--;
        showTab(tabOrder[currentTabIndex]);
    }
}

function continueToNextTab() {
    // Handle logic for the "Next" button
    if (currentTabIndex < tabOrder.length - 1) {
        currentTabIndex++;
        showTab(tabOrder[currentTabIndex]);
    }
}

// Array to define the order of tabs
var tabOrder = ['basicData', 'details', 'tariffs', 'singleTeam'];

// Initially show the Basic Data tab
showTab('basicData');

function showSingleContent() {
    var singleContent = document.getElementById('singleContent');
    var teamContent = document.getElementById('teamContent');

    singleContent.style.display = 'block';
    teamContent.style.display = 'none';
}

function showTeamContent() {
    var singleContent = document.getElementById('singleContent');
    var teamContent = document.getElementById('teamContent');

    singleContent.style.display = 'none';
    teamContent.style.display = 'block';
}


function toggleOptions() {
    var positionSelect = document.getElementById('positionSelect');
    positionSelect.style.display = (positionSelect.style.display === 'none' || positionSelect.style.display === '') ? 'block' : 'none';
}

function updateSelectedPosition() {
    var positionSelect = document.getElementById('positionSelect');
    var selectedPosition = positionSelect.options[positionSelect.selectedIndex].text;

    var selectedPositionText = document.getElementById('selectedPositionText');
    selectedPositionText.textContent = '- ' + selectedPosition + ' -';

    // Hide the position select after selection
    positionSelect.style.display = 'none';
}




function toggleExpOptions() {
    var experienceSelect = document.getElementById('experienceSelect');
    experienceSelect.style.display = (experienceSelect.style.display === 'none' || experienceSelect.style.display === '') ? 'block' : 'none';
}

function updateSelectedExperience() {
    var experienceSelect = document.getElementById('experienceSelect');
    var selectedExperience = experienceSelect.options[experienceSelect.selectedIndex].text;

    var selectedExperienceText = document.getElementById('selectedExperienceText');
    selectedExperienceText.textContent = '- ' + selectedExperience + ' -';

    // Hide the experience select after selection
    experienceSelect.style.display = 'none';
}



function toggleQuaOptions() {
    var quantitySelect = document.getElementById('quantitySelect');
    quantitySelect.style.display = (quantitySelect.style.display === 'none' || quantitySelect.style.display === '') ? 'block' : 'none';
}

function updateSelectedQuantity() {
    var quantitySelect = document.getElementById('quantitySelect');
    var selectedQuantity = quantitySelect.options[quantitySelect.selectedIndex].text;

    var selectedQuantityText = document.getElementById('selectedQuantityText');
    selectedQuantityText.textContent = '- ' + selectedQuantity + ' -';

    // Hide the quantity select after selection
    quantitySelect.style.display = 'none';
}