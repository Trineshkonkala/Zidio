// Hide all sections and show the selected one
function navigateTo(page) {
  const pages = document.querySelectorAll(".page");
  pages.forEach(p => (p.style.display = "none"));

  const targetPage = document.getElementById(page);
  if (targetPage) {
    targetPage.style.display = "block";
  }

  // Fetch dynamic data if needed
  if (page === "jobs") fetchJobListings();
  else if (page === "profile") loadProfile();
  else if (page === "status") loadApplicationStatus();
}

// Logout function
function logout() {
  alert("Logging out...");
  window.location.href = "login.html"; // Redirect back to login
}

// Load dummy or real profile data (this could be fetched from the backend)
function loadProfile() {
  const studentData = {
    name: "Student User",
    email: "student@gmail.com",
    college: "XYZ Institute, ENTC",
    phone: "9876543210"
  };

  document.getElementById("name").value = studentData.name;
  document.getElementById("email").value = studentData.email;
  document.getElementById("college").value = studentData.college;
  document.getElementById("phone").value = studentData.phone;
}

// Handle profile update
document.getElementById("profile-form").addEventListener("submit", function (e) {
  e.preventDefault();

  const updatedData = {
    name: document.getElementById("name").value,
    college: document.getElementById("college").value,
    phone: document.getElementById("phone").value
  };

  console.log("Updated Profile:", updatedData);

  // TODO: Replace with actual API call using fetch/axios
  document.getElementById("profile-message").textContent = "Profile updated successfully!";
});

// Resume upload handler
document.getElementById("resume-form").addEventListener("submit", function (e) {
  e.preventDefault();

  const fileInput = document.getElementById("resumeFile");
  const formData = new FormData();
  formData.append("resume", fileInput.files[0]);

  const token = localStorage.getItem("token");

  fetch("http://localhost:8080/student/resume", {
    method: "POST",
    headers: {
      Authorization: "Bearer " + token
    },
    body: formData
  })
    .then(res => res.json())
    .then(data => {
      document.getElementById("resume-message").textContent = data.message || "Resume uploaded successfully!";
    })
    .catch(err => {
      console.error("Upload error:", err);
      document.getElementById("resume-message").textContent = "Upload failed. Try again.";
    });
});

// Fetch and display job listings
function fetchJobListings() {
  const token = localStorage.getItem("token");

  fetch("http://localhost:8080/student/jobs", {
    headers: {
      Authorization: "Bearer " + token
    }
  })
    .then(res => res.json())
    .then(jobs => {
      const jobListings = document.getElementById("job-listings");
      jobListings.innerHTML = "";

      if (!jobs.length) {
        jobListings.innerHTML = "<p>No job listings available.</p>";
        return;
      }

      jobs.forEach(job => {
        const card = document.createElement("div");
        card.className = "job-card";

        card.innerHTML = `
          <h3>${job.title}</h3>
          <p><strong>Company:</strong> ${job.company}</p>
          <p><strong>Location:</strong> ${job.location}</p>
          <p><strong>Eligibility:</strong> ${job.eligibility}</p>
          <p><strong>Description:</strong> ${job.description}</p>
          <button class="apply-btn" onclick="applyToJob('${job.id}')">Apply</button>
        `;

        jobListings.appendChild(card);
      });
    })
    .catch(err => {
      console.error("Failed to fetch jobs:", err);
    });
}

// Apply to a specific job
function applyToJob(jobId) {
  const token = localStorage.getItem("token");

  fetch("http://localhost:8080/student/apply", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token
    },
    body: JSON.stringify({ jobId })
  })
    .then(res => res.json())
    .then(data => {
      alert(data.message || "Application submitted successfully!");
    })
    .catch(err => {
      console.error("Apply error:", err);
      alert("Failed to apply. Try again.");
    });
}

// Fetch and display application status
function loadApplicationStatus() {
  const token = localStorage.getItem("token");

  fetch("http://localhost:8080/student/status", {
    headers: {
      Authorization: "Bearer " + token
    }
  })
    .then(res => res.json())
    .then(applications => {
      const tracker = document.getElementById("application-tracker");
      tracker.innerHTML = "";

      if (!applications.length) {
        tracker.innerHTML = "<p>No applications found.</p>";
        return;
      }

      applications.forEach(app => {
        const card = document.createElement("div");
        card.className = "application-card";

        card.innerHTML = `
          <h3>${app.jobTitle}</h3>
          <p><strong>Company:</strong> ${app.company}</p>
          <p><strong>Applied On:</strong> ${new Date(app.appliedDate).toLocaleDateString()}</p>
          <p><strong>Status:</strong> <span class="status ${app.status.toLowerCase()}">${app.status}</span></p>
        `;

        tracker.appendChild(card);
      });
    })
    .catch(err => {
      console.error("Status fetch error:", err);
    });
}

// On initial load, default to profile page
window.onload = () => {
  navigateTo("profile");
};
