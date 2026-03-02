import React, { useEffect, useState } from "react";
import "./Profile.css";

const Profile = () => {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));

    if (storedUser) {
      setUserData(storedUser);
    }

    setLoading(false);
  }, []);

  if (loading) return <p className="loading">Loading profile...</p>;
  if (!userData) return <p className="loading">Profile not found</p>;

  const role = userData.role;

  return (
    <div className="profile-container">
      <div className="profile-card">
        <h2 className="profile-title">
          {role === "STUDENT"
            ? userData.studentFullName
            : userData.name}
        </h2>

        <span className="role-badge">{role}</span>

        <div className="profile-details">

          {role === "STUDENT" && (
            <>
              <p><strong>Roll No:</strong> {userData.rollNo}</p>
              <p><strong>Gender:</strong> {userData.gender}</p>
              <p><strong>Age:</strong> {userData.age}</p>
              <p><strong>Joined Year:</strong> {userData.joinedYear}</p>
            </>
          )}

          <p><strong>Email:</strong> {userData.email}</p>
          <p><strong>Department:</strong> {userData.department}</p>
          <p><strong>College:</strong> {userData.collegeName}</p>
        </div>

        <div className="profile-actions">
          <button
            className="btn logout-btn"
            onClick={() => {
              localStorage.clear();
              window.location.href = "/login";
            }}
          >
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default Profile;
