// dashboard.js

// Simulated example: getting student name from localStorage
const studentName = localStorage.getItem("studentName") || "Student";

// Display student name in the header
document.addEventListener("DOMContentLoaded", () => {
  const header = document.querySelector("header h1");
  if (header) {
    header.textContent = `Welcome, ${studentName}`;
  }

  // Optional: Fetch summary data (e.g., applied jobs)
  const token = localStorage.getItem("token");

  fetch("http://localhost:8080/student/summary", {
    headers: {
      Authorization: "Bearer " + token
    }
  })
    .then(res => res.json())
    .then(data => {
      const cards = document.querySelector(".cards");

      if (data && cards) {
        const statCard = document.createElement("div");
        statCard.className = "card";
        statCard.innerHTML = `
          <h3>Summary</h3>
          <p>Jobs Applied: ${data.appliedJobs}</p>
          <p>Shortlisted: ${data.shortlisted}</p>
        `;
        cards.appendChild(statCard);
      }
    })
    .catch(err => {
      console.error("Error fetching student summary:", err);
    });
});
