import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Course.css";
import { toast } from "react-toastify";

const Course = () => {
  const user = JSON.parse(localStorage.getItem("user")) || {};

  const department =
    typeof user.department === "object"
      ? user.department?.deptName?.toUpperCase()
      : user.department?.toUpperCase();

  const API = process.env.REACT_APP_API_URL;

  const [courses, setCourses] = useState([]);
  const [showForm, setShowForm] = useState(false);

  const [formData, setFormData] = useState({
    courseName: "",
    description: "",
    courseLink: "",
    instructor: "",
    credits: "",
  });

  useEffect(() => {
    if (!department) return;

    axios
      .get(
        `${API}/api/courses/department/name/${department}`,
        { withCredentials: true }
      )
      .then((res) => setCourses(res.data || []))
      .catch((err) => console.error(err));
  }, [department, API]);

  const submitCourse = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        `${API}/api/courses/create`,
        {
          ...formData,
          credits: Number(formData.credits),
          departmentId: user.departmentId,
        },
        { withCredentials: true }
      );

      toast.success("Course created successfully");
      setShowForm(false);

      setFormData({
        courseName: "",
        description: "",
        courseLink: "",
        instructor: "",
        credits: "",
      });

      const res = await axios.get(
        `${API}/api/courses/department/name/${department}`,
        { withCredentials: true }
      );

      setCourses(res.data || []);
    } catch (err) {
      toast.error(err.response?.data || "Failed to create course");
    }
  };

  const deleteCourse = async (id) => {
    if (!window.confirm("Delete this course?")) return;

    try {
      await axios.delete(
        `${API}/api/courses/${id}`,
        { withCredentials: true }
      );

      setCourses((prev) => prev.filter((c) => c.id !== id));
      toast.success("Course deleted successfully");
    } catch (err) {
      toast.error(err.response?.data || "Unauthorized or Failed to delete");
    }
  };

  return (
    <div className="course-container">
      <h1 className="page-title">Course Library 📚</h1>

      {user.role === "FACULTY" && (
        <button className="create-btn" onClick={() => setShowForm(true)}>
          + Create Course
        </button>
      )}

      <div className="course-grid">
        {courses.length === 0 ? (
          <p className="empty-text">No courses available</p>
        ) : (
          courses.map((c) => (
            <div key={c.id} className="course-card">
              <h3>{c.courseName}</h3>
              <p>{c.description}</p>
              <p><b>Instructor:</b> {c.instructor}</p>
              <p><b>Credits:</b> {c.credits}</p>
              <p><b>Department:</b> {c.departmentName}</p>

              <a
                href={c.courseLink}
                target="_blank"
                rel="noreferrer"
                className="open-btn"
              >
                Open Course
              </a>

              {user.role === "FACULTY" && (
                <button
                  className="delete-btn"
                  onClick={() => deleteCourse(c.id)}
                >
                  Delete
                </button>
              )}
            </div>
          ))
        )}
      </div>

      {showForm && (
        <div className="modal-overlay">
          <form className="course-form" onSubmit={submitCourse}>
            <h2>Create Course</h2>

            <input
              placeholder="Course Name"
              required
              value={formData.courseName}
              onChange={(e) =>
                setFormData({ ...formData, courseName: e.target.value })
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
              placeholder="Course Link"
              required
              value={formData.courseLink}
              onChange={(e) =>
                setFormData({ ...formData, courseLink: e.target.value })
              }
            />

            <input
              placeholder="Instructor Name"
              required
              value={formData.instructor}
              onChange={(e) =>
                setFormData({ ...formData, instructor: e.target.value })
              }
            />

            <input
              type="number"
              placeholder="Credits"
              required
              value={formData.credits}
              onChange={(e) =>
                setFormData({ ...formData, credits: e.target.value })
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

export default Course;