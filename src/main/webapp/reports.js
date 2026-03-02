/* Author: Ameen Ayyad
Purpose: Javascript functionality for the reports tracking page 
Last Modified: 2/28/26 */

/* What I have done so far (Ameen Ayyad): 
 - View Report button functionality
 - Display report details
 - Progress tracking
 - User information display
*/

document.addEventListener("DOMContentLoaded", function () {

    // Buttons and elements
    const viewReportBtn = document.querySelector(".report-card__btn");
    const reportName = document.querySelector(".report-card__info p:first-of-type");
    const reportDescription = document.querySelector(".report-card__info p:nth-of-type(2)");
    const progressFill = document.querySelector(".progress__fill");
    const profileCircle = document.querySelector(".sidebar__profile-circle");
    const userInfo = document.querySelector(".sidebar__nav li");
    
    // Maybe sometype of message element for displaying status could be used here 
    let messageDiv = document.querySelector(".report-message");
    if (!messageDiv) {
        messageDiv = document.createElement("div");
        messageDiv.className = "report-message";
        messageDiv.style.marginTop = "10px";
        messageDiv.style.padding = "10px";
        messageDiv.style.borderRadius = "4px";
        
        // Find where to insert the message
        const reportCard = document.querySelector(".report-card");
        if (reportCard) {
            reportCard.appendChild(messageDiv);
        }
    }

    // Example: Set user information dynamically
    if (userInfo) {
        // This could be information from the user's session or a backend API call
        const userName = "Benjamine Netanhyahu"; // This would normally come from login data
        const userEmail = "littlestjef@gmail.com"; // This would normally come from login data
        userInfo.textContent = `${userName} (${userEmail})`;
    }

    // Example: Set progress based on report status
    if (progressFill) {
        // This would come from backen data about the report's current status
        const reportStatus = "Under Review"; // Example status
        const progressStages = ["Submitted", "Processed", "Under Review", "Completed"];
        const currentStageIndex = progressStages.indexOf(reportStatus);
        
        if (currentStageIndex !== -1) {
            // Calculate progress percentage (25% per stage)
            const progressPercentage = (currentStageIndex + 1) * 25;
            progressFill.style.width = progressPercentage + "%";
            
            // Report status with colors 
            if (progressPercentage <= 25) {
                progressFill.style.backgroundColor = "#ff6b6b"; // Red for submitted
            } else if (progressPercentage <= 50) {
                progressFill.style.backgroundColor = "#ffd93d"; // Yellow for processed
            } else if (progressPercentage <= 75) {
                progressFill.style.backgroundColor = "#6b9cff"; // Blue for under review
            } else {
                progressFill.style.backgroundColor = "#51cf66"; // Green for completed
            }
        }
    }

    // Add click event listener to the View Report button
    if (viewReportBtn) {
        viewReportBtn.addEventListener("click", function () {
            
            // Clear previous message
            messageDiv.textContent = "";
            messageDiv.style.color = "black";
            
            // Report Details
            const currentReportName = "Sample Report"; // Placeholder
            const currentReportDesc = "This is a sample report description"; // Placeholder
            
            try {
                // Simulate fetching report data
                console.log("Fetching report details...");
                
                // Displaying report information
                messageDiv.style.color = "green";
                messageDiv.innerHTML = `
                    <strong>Report Details:</strong><br>
                    Name: ${currentReportName}<br>
                    Description: ${currentReportDesc}<br>
                    Status: Under Review<br>
                    Submitted: 2026-02-15
                `;

                
                console.log("Report viewed:", currentReportName);
                
                // Here backend could implement something that shows a full description of the report
                
                
            } catch (error) {
                messageDiv.style.color = "red";
                messageDiv.textContent = "Error loading report: " + error.message;
            }
        });
    }

    if (profileCircle) {
        profileCircle.addEventListener("click", function () {
            console.log("Profile clicked - could open user menu");
            // Maybe this could show a dropdown menu here
            // or navigate to user profile page
        });
    }

    // Optional: Add functionality to navigation links
    const navLinks = document.querySelectorAll(".sub-nav a");
    navLinks.forEach(link => {
        link.addEventListener("click", function (event) {
            // Analytics could be added here (backend tracking)
            console.log("Navigating to:", link.textContent);
        });
    });

    // Initialize page with sample data
    function initializePageData() {
        // This would normally fetch data from an API
        console.log("Reports page initialized");
        
        // Example: You could fetch list of reports for this user (Backend API call)

    }
    
    // Call initialization
    initializePageData();

});