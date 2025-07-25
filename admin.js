
const token = localStorage.getItem("token");

// Redirect to login if token is missing
if (!token) {
  window.location.href = "index.html";
}

fetch("http://localhost:8080/admin/dashboard/summary", {
  headers: { Authorization: "Bearer " + token }
})
  .then(res => res.json())
  .then(data => {
    const div = document.getElementById("summary");
    div.innerHTML = `
      <div class="card">
        <p><strong>Total Students:</strong> ${data.students}</p>
        <p><strong>Total Jobs:</strong> ${data.jobs}</p>
        <p><strong>Total Applications:</strong> ${data.applications}</p>
      </div>
    `;
  })
  .catch(error => {
    console.error("Error loading admin dashboard:", error);
    document.getElementById("summary").innerText = "Failed to load summary.";
  });

function logout() {
  localStorage.removeItem("token");
  window.location.href = "index.html";
}
