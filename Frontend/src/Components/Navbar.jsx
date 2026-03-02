import React, { useState, useRef, useEffect } from "react";
import Icon from "../Images/icon.png";
import ProfileIcon from "../Images/Profile.png";
import "./Navbar.css";
import { Bell } from "lucide-react";

const Navbar = () => {

  const storedUser = JSON.parse(localStorage.getItem("user")) || {};
  const role = storedUser.role || "STUDENT";


  const department =
    typeof storedUser.department === "object"
      ? storedUser.department?.deptName
      : storedUser.department || "GENERAL";

  const storageKey = `notifications_${department}`;

  const [showNotifications, setShowNotifications] = useState(false);
  const [showProfile, setShowProfile] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [newMessage, setNewMessage] = useState("");

  const notificationRef = useRef(null);
  const profileRef = useRef(null);
  useEffect(() => {
    const stored =
      JSON.parse(localStorage.getItem(storageKey)) || [];
    setNotifications(stored);
  }, [storageKey]);


  const saveNotifications = (data) => {
    setNotifications(data);
    localStorage.setItem(storageKey, JSON.stringify(data));
  };

  const addNotification = () => {
    if (!newMessage.trim()) return;

    const newNote = {
      id: Date.now(),
      message: newMessage.trim(),
    };

    saveNotifications([newNote, ...notifications]);
    setNewMessage("");
  };

  const deleteNotification = (id) => {
    const updated = notifications.filter((n) => n.id !== id);
    saveNotifications(updated);
  };


  useEffect(() => {
    const handleClickOutside = (e) => {
      if (
        notificationRef.current &&
        !notificationRef.current.contains(e.target)
      ) {
        setShowNotifications(false);
      }

      if (
        profileRef.current &&
        !profileRef.current.contains(e.target)
      ) {
        setShowProfile(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () =>
      document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <nav className="site-nav">
      <div className="brand">
        <img src={Icon} alt="Logo" />
        <div className="name">Student HUB</div>
      </div>

      <ul className="nav-links">
        <li><a href="/Home2">🏠 Home</a></li>
        <li><a href="/Assignments">👨🏻‍💻 Assignments</a></li>
        <li><a href="/Quizzes">🧠 Quizzes</a></li>
        <li><a href="/Course">🎓 Courses</a></li>
      </ul>

      <div className="nav-actions">


        <div className="notification-wrapper" ref={notificationRef}>
          <Bell
            size={26}
            onClick={() => {
              setShowNotifications(!showNotifications);
              setShowProfile(false);
            }}
            style={{ cursor: "pointer" }}
          />

          {notifications.length > 0 && (
            <span className="notification-badge">
              {notifications.length}
            </span>
          )}

          {showNotifications && (
            <div className="notification-dropdown">
              <div className="notification-header">
                <h4>Notifications</h4>
                {role === "FACULTY" && (
                  <button
                    onClick={addNotification}
                    disabled={!newMessage.trim()}
                  >
                    ➕
                  </button>
                )}
              </div>

              {role === "FACULTY" && (
                <input
                  type="text"
                  placeholder="Enter notification..."
                  value={newMessage}
                  onChange={(e) => setNewMessage(e.target.value)}
                />
              )}

              {notifications.length === 0 ? (
                <p className="empty-msg">No new notifications</p>
              ) : (
                notifications.map((note) => (
                  <div key={note.id} className="notification-item">
                    <span>{note.message}</span>
                    {role === "FACULTY" && (
                      <button
                        onClick={() => deleteNotification(note.id)}
                      >
                        🗑
                      </button>
                    )}
                  </div>
                ))
              )}
            </div>
          )}
        </div>


        <div className="profile-wrapper" ref={profileRef}>
          <img
            src={ProfileIcon}
            alt="Profile"
            className="profile"
            onClick={() => {
              setShowProfile(!showProfile);
              setShowNotifications(false);
            }}
          />

          {showProfile && (
            <div className="profile-dropdown">

              <h3>
                {role === "STUDENT"
                  ? storedUser.studentFullName
                  : storedUser.name}
              </h3>

              <span className="role-badge">{role}</span>

              {role === "STUDENT" && (
                <>
                  <p><strong>Roll No</strong><span>{storedUser.rollNo}</span></p>
                  <p><strong>Gender</strong><span>{storedUser.gender}</span></p>
                  <p><strong>Age</strong><span>{storedUser.age}</span></p>
                  <p><strong>Joined</strong><span>{storedUser.joinedYear}</span></p>
                </>
              )}

              <p><strong>Email</strong><span>{storedUser.email}</span></p>
              <p><strong>Department</strong><span>{department}</span></p>
              <p><strong>College</strong><span>{storedUser.collegeName}</span></p>

              <button
                className="logout-btn"
                onClick={() => {
                  localStorage.removeItem("user");
                  window.location.href = "/";
                }}
              >
                Logout
              </button>

            </div>
          )}
        </div>

      </div>
    </nav>
  );
};

export default Navbar;