import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Actual.css";

const API = process.env.REACT_APP_API_URL;
const Actual = () => {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user")) || {};

  const displayName = user.studentFullName || user.name || "User";

  const department =
    typeof user.department === "object"
      ? user.department?.deptName
      : user.department;

  const [assignments, setAssignments] = useState([]);
  const [quizzes, setQuizzes] = useState([]);
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    if (!department) return;
    if (user.id) {
      axios
        .get(
          `${API}/api/assignments/branch/${department}/${user.id}`
        )
        .then((res) => setAssignments(res.data))
        .catch((err) =>
          console.error("Error fetching assignments:", err)
        );
    }

    axios
      .get(`${API}/api/quizzes/branch/${department}`)
      .then((res) => setQuizzes(res.data))
      .catch((err) =>
        console.error("Error fetching quizzes:", err)
      );
    axios
      .get(
        `${API}/api/courses/department/name/${department}`
      )
      .then((res) => setCourses(res.data))
      .catch((err) =>
        console.error("Error fetching courses:", err)
      );

  }, [department, user.id]);

  return (
    <>

      <div className="dashboard-container">
        <header className="dashboard-header">
          <h1>Welcome , {displayName}</h1>
          <p>Here's your personalized dashboard</p>
        </header>

        <div className="dashboard-cards">
          <div className="card">
            <h2>Assignments</h2>
            {assignments.length === 0 ? (
              <p>No Assignments Available</p>
            ) : (
              <ul>
                {assignments.map((a) => {
                  const rawDate = a.dueDate || a.endDate;
                  const formattedDate = rawDate
                    ? new Date(rawDate).toLocaleDateString()
                    : "Not Available";

                  return (
                    <li key={`assignment-${a.id}`}>
                      <div className="content">
                        <span className="title">{a.title}</span>
                        <span className="info-text">
                          📅 Due: {formattedDate}
                        </span>
                      </div>

                      <button
                        className="go-btn"
                        onClick={() => navigate("/Assignments")}
                      >
                        Go To
                      </button>
                    </li>
                  );
                })}
              </ul>
            )}
          </div>
          <div className="card">
            <h2>Quizzes</h2>
            {quizzes.length === 0 ? (
              <p>No active quizzes</p>
            ) : (
              <ul>
                {quizzes.map((q) => {
                  const formattedDate = q.endDate
                    ? new Date(q.endDate).toLocaleDateString()
                    : "Not Available";
                  return (
                    <li key={`quiz-${q.id}`}>
                      <div className="content">
                        <span className="title">{q.title}</span>
                        <span className="info-text">
                          ⏳ Ends: {formattedDate}
                        </span>
                      </div>
                      <button
                        className="go-btn"
                        onClick={() => navigate("/Quizzes")}
                      >
                        Go To
                      </button>
                    </li>
                  );
                })}
              </ul>
            )}
          </div>
          <div className="card">
            <h2>Courses</h2>
            {courses.length === 0 ? (
              <p>No courses available</p>
            ) : (
              <ul>
                {courses.map((c) => (
                  <li key={`course-${c.id}`}>
                    <div className="content">
                      <span className="title">{c.courseName}</span>
                      <span className="info-text">
                        👨‍🏫 Instructor: {c.instructor}
                      </span>
                      <span className="info-text">
                        📘 Credits: {c.credits}
                      </span>
                    </div>
                    <button
                      className="go-btn"
                      onClick={() => navigate("/Course")}
                    >
                      Go To
                    </button>
                  </li>
                ))}
              </ul>
            )}
          </div>

        </div>
      </div>

    </>
  );
};

export default Actual;