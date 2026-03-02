import React, { useEffect, useState, useCallback } from "react";
import axios from "axios";
import "./Assignments.css";
import { toast } from "react-toastify";

const API = process.env.REACT_APP_API_URL;
const Assignments = () => {
  const user = JSON.parse(localStorage.getItem("user")) || {};
  const department =
    typeof user.department === "object"
      ? user.department?.deptName
      : user.department;

  const [assignments, setAssignments] = useState([]);
  const [files, setFiles] = useState({});
  const [showForm, setShowForm] = useState(false);
  const [showSubmissions, setShowSubmissions] = useState(false);
  const [submissions, setSubmissions] = useState([]);
  const [stats, setStats] = useState(null);
  const fetchAssignments = useCallback(async () => {
    try {
      const url =
        user.role === "STUDENT"
          ? `${API}/api/assignments/branch/${department}/${user.id}`
          : `${API}/api/assignments/faculty/${user.id}`;

      const res = await axios.get(url);
      setAssignments(res.data || []);
    } catch (err) {
      console.error(err);
      setAssignments([]);
    }
  }, [user.id, user.role, department]);

  useEffect(() => {
    fetchAssignments();
  }, [fetchAssignments]);
  const submitAssignment = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.post(
        `${API}/api/assignments/create`,
        {
          title: e.target.title.value,
          description: e.target.description.value,
          dueDate: e.target.dueDate.value,
          department,
          facultyId: user.id,
        }
      );

      toast.success(res.data.message);
      setShowForm(false);
      fetchAssignments();
    } catch (err) {
      toast.error("Creation failed");
    }
  };
  const deleteAssignment = async (id) => {
    try {
      const res = await axios.delete(
        `${API}/api/assignments/${id}`
      );

      toast.success(res.data);
      fetchAssignments();
    } catch {
      toast.error("Delete failed");
    }
  };
  const uploadQuestion = async (assignmentId) => {
    const file = files[assignmentId];
    if (!file) {
  toast.warning("Please select a file");
  return;
}

    const fd = new FormData();
    fd.append("file", file);

    try {
      await axios.post(
        `${API}/api/documents/assignment/${assignmentId}/upload`,
        fd
      );

      toast.success("Question uploaded");
      fetchAssignments();
    } catch {
      toast.error("Upload failed");
    }
  };
  const downloadQuestion = (assignmentId) => {
    window.open(
      `${API}/api/documents/assignment/${assignmentId}`,
      "_blank"
    );
  };
  const uploadAnswer = async (assignmentId, submissionId) => {
    const file = files[submissionId];
    if (!file) {
  toast.warning("Please select answer file");
  return;
}
    const fd = new FormData();
    fd.append("file", file);
    try {
      await axios.post(
        `${API}/api/assignment-submissions/${assignmentId}/student/${user.id}/submit`,
        fd
      );
      toast.success("Answer submitted");
      fetchAssignments();
    } catch {
      toast.error("Upload failed");
    }
  };
  const viewSubmissions = async (assignmentId) => {
    try {
      const [subsRes, statsRes] = await Promise.all([
        axios.get(
          `${API}/api/assignment-submissions/${assignmentId}/submissions`
        ),
        axios.get(
          `${API}/api/faculty/assignments/${assignmentId}/stats`
        ),
      ]);
      setSubmissions(subsRes.data || []);
      setStats(statsRes.data);
      setShowSubmissions(true);
    } catch {
      toast.error("Failed to load submissions");
    }
  };

  return (
    <>

      <div className="assignments-container">
        <h1 className="page-title"> Assignment Library 📚</h1>

        {user.role === "FACULTY" && (
          <button className="create-btn" onClick={() => setShowForm(true)}>
            + Create Assignment
          </button>
        )}
        {showForm && (
          <div className="modal-overlay">
            <form className="assignment-form" onSubmit={submitAssignment}>
              <h2>Create Assignment</h2>
              <input name="title" className="form-input" placeholder="Title" required />
              <textarea name="description" className="form-textarea" placeholder="Description" required />
              <input
                name="dueDate"
                type="date"
                className="form-input"
                min={new Date().toISOString().split("T")[0]}
                required
              />
              <div className="form-actions">
                <button type="button" className="cancel-btn" onClick={() => setShowForm(false)}>
                  Cancel
                </button>
                <button type="submit" className="submit-btn">
                  Create
                </button>
              </div>
            </form>
          </div>
        )}
        <div className="assignments-list">
          {assignments.length === 0 ? (
            <p className="empty-text">No assignments available</p>
          ) : (
            assignments.map((a) => (
              <div key={a.assignmentId} className="assignment-card">
                <h3>{a.title}</h3>
                <p>{a.description}</p>
                <div className="assignment-meta">
                  <span><b>Dept:</b> {a.department}</span>
                  <span><b>Due:</b> {a.dueDate}</span>
                </div>
                {user.role === "STUDENT" && (
                  <>
                    {a.questionUploaded && (
                      <button
                        className="download-btn"
                        onClick={() => downloadQuestion(a.assignmentId)}
                      >
                        Download Question
                      </button>
                    )}
                    {!a.answerUploaded ? (
                      <>
                        <input
                          type="file"
                          onChange={(e) =>
                            setFiles({ ...files, [a.submissionId]: e.target.files[0] })
                          }
                        />
                        <button
                          className="btn7"
                          onClick={() =>
                            uploadAnswer(a.assignmentId, a.submissionId)
                          }
                        >
                          Submit Answer
                        </button>
                      </>
                    ) : (
                      <div className="file-name">
                        Your Answer: {a.answerFileName}
                      </div>
                    )}
                  </>
                )}
                {user.role === "FACULTY" && (
                  <>
                    {a.questionUploaded ? (
                      <div className="file-name">
                        {a.questionFileName}
                      </div>
                    ) : (
                      <>
                        <input
                          type="file"
                          onChange={(e) =>
                            setFiles({ ...files, [a.assignmentId]: e.target.files[0] })
                          }
                        />
                        <button
                          className="btn7"
                          onClick={() => uploadQuestion(a.assignmentId)}
                        >
                          Upload Question
                        </button>
                      </>
                    )}
                    <button
                      className="view-submissions-btn"
                      onClick={() => viewSubmissions(a.assignmentId)}
                    >
                      View Submissions
                    </button>
                    <button
                      className="btn7 danger"
                      onClick={() => deleteAssignment(a.assignmentId)}
                    >
                      Delete Assignment
                    </button>
                  </>
                )}
              </div>
            ))
          )}
        </div>
        {showSubmissions && (
          <div className="modal-overlay">
            <div className="assignment-form">
              <h2>Student Submissions</h2>
              {stats && (
                <div className="stats-bar">
                  <div className="stat total">
                    <span>Total</span>
                    <b>{stats.totalStudents}</b>
                  </div>
                  <div className="stat submitted">
                    <span>Submitted</span>
                    <b>{stats.submitted}</b>
                  </div>
                  <div className="stat pending">
                    <span>Not Submitted</span>
                    <b>{stats.pending}</b>
                  </div>
                </div>
              )}
              {submissions.map((s) => (
                <div key={s.submissionId} className="submission-row">
                  <div>
                    <b>{s.studentName}</b> ({s.rollNo})
                    <div className="submission-status">{s.status}</div>
                  </div>
                  {s.answerUploaded && (
                    <button
                      className="btn7"
                      onClick={() =>
                        window.open(
                          `${API}/api/assignment-submissions/submissions/${s.submissionId}/download`,
                          "_blank"
                        )
                      }
                    >
                      Download
                    </button>
                  )}
                </div>
              ))}
              <button className="cancel-btn" onClick={() => setShowSubmissions(false)}>
                Close
              </button>
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default Assignments;