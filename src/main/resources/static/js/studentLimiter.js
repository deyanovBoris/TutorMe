document.addEventListener("DOMContentLoaded", () => {
    const studentsContainer = document.getElementById("students-container");
    const showMoreButton = document.getElementById("show-more-students");
    const showLessButton = document.getElementById("show-less-students");
    const students = Array.from(studentsContainer.getElementsByClassName("card"));
    let visibleStudents = 3;

    // Function to show the initial set of students
    function showInitialStudents() {
        students.forEach((student, index) => {
            if (index < visibleStudents) {
                student.style.display = "flex";
            } else {
                student.style.display = "none";
            }
        });
        updateButtons();
    }

    // Function to show more students on button click
    function showMoreStudents() {
        const nextStudents = students.slice(visibleStudents, visibleStudents + 3);
        nextStudents.forEach(student => student.style.display = "flex");
        visibleStudents += 3;
        updateButtons();
    }

    // Function to show less students on button click
    function showLessStudents() {
        visibleStudents = 3;
        showInitialStudents();
    }

    // Function to update the visibility of the buttons
    function updateButtons() {
        if (visibleStudents >= students.length) {
            showMoreButton.style.display = "none";
        } else {
            showMoreButton.style.display = "block";
        }

        if (visibleStudents > 3) {
            showLessButton.style.display = "block";
        } else {
            showLessButton.style.display = "none";
        }
    }

    // Initialize the students display
    showInitialStudents();

    // Add event listeners to the buttons
    showMoreButton.addEventListener("click", showMoreStudents);
    showLessButton.addEventListener("click", showLessStudents);
});
