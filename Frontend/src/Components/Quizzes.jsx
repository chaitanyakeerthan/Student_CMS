import React, { useEffect, useState, useCallback } from "react";
import axios from "axios";
import "./Quizzes.css";
import { toast } from "react-toastify";

const Quizzes = () => {
  const user = JSON.parse(localStorage.getItem("user")) || {};

  const department =
    typeof user.department === "object"
      ? user.department?.deptName
      : user.department;

  const API = process.env.REACT_APP_API_URL;

  const [quizzes, setQuizzes] = useState([]);
  const [showForm, setShowForm] = useState(false);

  const [formData, setFormData] = useState({
    title: "",
    description: "",
    quizLink: "",
    responseLink: "",
    endDate: "",
  });

  const today = new Date().toISOString().split("T")[0];

  const fetchQuizzes = useCallback(async () => {
    if (!department) return;

    try {
      const res = await axios.get(
        `${API}/api/quizzes/branch/${department}`,
        { withCredentials: true }
      );
      setQuizzes(res.data || []);
    } catch {
      toast.error("Failed to load quizzes");
      setQuizzes([]);
    }
  }, [department, API]);

  useEffect(() => {
    fetchQuizzes();
  }, [fetchQuizzes]);

  const submitQuiz = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        `${API}/api/quizzes/create`,
        { ...formData, department },
        { withCredentials: true }
      );

      toast.success("Quiz created successfully");

      setShowForm(false);
      setFormData({
        title: "",
        description: "",
        quizLink: "",
        responseLink: "",
        endDate: "",
      });

      fetchQuizzes();
    } catch (err) {
      toast.error(err.response?.data || "Failed to create quiz");
    }
  };

  const deleteQuiz = async (id) => {
    try {
      await axios.delete(
        `${API}/api/quizzes/${id}`,
        { withCredentials: true }
      );

      toast.success("Quiz deleted successfully");
      setQuizzes((prev) => prev.filter((q) => q.id !== id));
    } catch (err) {
      toast.error(err.response?.data || "Failed to delete quiz");
    }
  };

  const openQuiz = async (quizId) => {
    try {
      const res = await axios.get(
        `${API}/api/student/quizzes/${quizId}/open`,
        { withCredentials: true }
      );

      window.open(res.data.quizLink, "_blank");
    } catch (err) {
      toast.error(err.response?.data || "Unauthorized");
    }
  };

  return (
    <div className="quizzes-container">
      <h1 className="page-title">Quiz Library 📚</h1>

      {user.role === "FACULTY" && (
        <button className="create-btn" onClick={() => setShowForm(true)}>
          + Create Quiz
        </button>
      )}

      <div className="quiz-grid">
        {quizzes.length === 0 ? (
          <p className="empty-text">No quizzes available</p>
        ) : (
          quizzes.map((q) => (
            <div key={q.id} className="quiz-card">
              <h3>{q.title}</h3>
              <p>{q.description}</p>
              <p><b>Department:</b> {department}</p>
              <p><b>End Date:</b> {q.endDate}</p>

              {user.role === "STUDENT" && (
                <button
                  className="open-btn"
                  onClick={() => openQuiz(q.id)}
                >
                  Open Quiz
                </button>
              )}

              {user.role === "FACULTY" && (
                <>
                  <a
                    href={q.responseLink}
                    target="_blank"
                    rel="noreferrer"
                    className="response-btn"
                  >
                    View Responses
                  </a>

                  <button
                    className="delete-btn"
                    onClick={() => deleteQuiz(q.id)}
                  >
                    Delete
                  </button>
                </>
              )}
            </div>
          ))
        )}
      </div>

      {showForm && (
        <div className="modal-overlay">
          <form className="quiz-form" onSubmit={submitQuiz}>
            <h2>Create Quiz</h2>

            <input
              placeholder="Title"
              required
              value={formData.title}
              onChange={(e) =>
                setFormData({ ...formData, title: e.target.value })
              }
            />

            <textarea
              placeholder="Description"
              required
              value={formData.description}
              onChange={(e) =>
                setFormData({ ...formData, description: e.target.value })
              }
            />

            <input
              placeholder="Student Quiz Link"
              required
              value={formData.quizLink}
              onChange={(e) =>
                setFormData({ ...formData, quizLink: e.target.value })
              }
            />

            <input
              placeholder="Faculty Response Link"
              required
              value={formData.responseLink}
              onChange={(e) =>
                setFormData({ ...formData, responseLink: e.target.value })
              }
            />

            <input
              type="date"
              min={today}
              required
              value={formData.endDate}
              onChange={(e) =>
                setFormData({ ...formData, endDate: e.target.value })
              }
            />

            <div className="form-actions">
              <button type="submit" className="submit-btn">
                Publish
              </button>

              <button
                type="button"
                className="cancel-btn"
                onClick={() => setShowForm(false)}
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default Quizzes;