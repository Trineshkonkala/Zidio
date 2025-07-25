document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const role = document.getElementById("role").value;

  fetch("http://localhost:8080/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password, role }),
  })
    .then((res) => res.json())
    .then((data) => {
      if (data.success) {
        localStorage.setItem("token", data.token);
        if (role === "student") {
          window.location.href = "student_dashboard.html";
        } else if (role === "recruiter") {
          window.location.href = "recruiter_dashboard.html";
        } else if (role === "admin") {
          window.location.href = "admin_dashboard.html";
        }
      } else {
        document.getElementById("error").textContent = data.message || "Invalid credentials";
      }
    })
    .catch(() => {
      document.getElementById("error").textContent = "Server error. Please try again later.";
    });
});