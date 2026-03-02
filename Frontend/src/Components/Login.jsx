import React, { useState } from 'react';
import './Login.css';
import Student from '../Images/Student.png';
import Admin from '../Images/Admin.png';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const API = process.env.REACT_APP_API_URL;

const Login = () => {
  const [isAdmin, setIsAdmin] = useState(false);

  const [data, setData] = useState({
    email: '',
    rollNo: '',
  });

  const [data2, setData2] = useState({
    email: '',
    password: '',
  });

  const navigate = useNavigate();
  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.post(
          `${API}/api/student/login`,
        data, {withCredentials: true }
      );

      if (res.status === 200) {
        toast.success('Login Successful');

        localStorage.setItem('user', JSON.stringify(res.data));
        localStorage.setItem("student_full_name", res.data.studentFullName);
        localStorage.setItem("userEmail", res.data.email);
        localStorage.setItem("student_department_name", res.data.department?.deptName);
        localStorage.setItem("student_id", res.data.id);

        setData({ email: '', rollNo: '' });
        navigate('/Home2');
      }

    } catch (error) {
      toast.error('Invalid Credentials');
      setData({ email: '', rollNo: '' });
    }
  };

  const handleChange2 = (e) => {
    setData2({ ...data2, [e.target.name]: e.target.value });
  };

  const handleSubmit2 = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.post(
        `${API}/api/faculty/login`,
        data2 , {withCredentials: true }
      );

      if (res.status === 200) {
        toast.success('Login Successful');

        localStorage.setItem("user", JSON.stringify(res.data));
        localStorage.setItem("Faculty_Name", res.data.name);
        localStorage.setItem("userEmail", res.data.email);

        setData2({ email: '', password: '' });
        navigate('/Home2');
      }

    } catch (error) {
      toast.error('Invalid Credentials');
      setData2({ email: '', password: '' });
    }
  };

  return (
    <div className={`box ${isAdmin ? 'active' : ''}`}>
      <div className="forms-wrap">
        <div className="slider">
          <form className="form-1" onSubmit={handleSubmit}>
            <h2 className="head">Student Login</h2>

            <div className="field">
              <i className="fas fa-envelope"></i>
              <input
                type="email"
                name="email"
                placeholder="College Email"
                value={data.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="field">
              <i className="fas fa-id-card"></i>
              <input
                type="text"
                name="rollNo"
                placeholder="Roll Number"
                value={data.rollNo}
                onChange={handleChange}
                required
              />
            </div>

            <input type="submit" value="Login" className="btn" />
          </form>
          <form className="form-2" onSubmit={handleSubmit2}>
            <h2 className="head">Faculty Login</h2>

            <div className="field">
              <i className="fas fa-envelope"></i>
              <input
                type="email"
                name="email"
                placeholder="Faculty Email"
                value={data2.email}
                onChange={handleChange2}
                required
              />
            </div>

            <div className="field">
              <i className="fas fa-lock"></i>
              <input
                type="password"
                name="password"
                placeholder="Faculty Password"
                value={data2.password}
                onChange={handleChange2}
                required
              />
            </div>

            <input type="submit" value="Login" className="btn" />
          </form>

        </div>
      </div>

      <div className="panels">
        <div className="side left">
          <div className="text">
            <h3>Faculty ?</h3>
            <p>If you are an <strong>Faculty</strong>, click below.</p>
            <button className="btn trans" onClick={() => setIsAdmin(true)}>
              Click Here
            </button>
          </div>
          <img src={Student} className="img" alt="Student" />
        </div>

        <div className="side right">
          <div className="text">
            <h3>Student ?</h3>
            <p>If you are a <strong>Student</strong>, click below.</p>
            <button className="btn trans" onClick={() => setIsAdmin(false)}>
              Click Here
            </button>
          </div>
          <img src={Admin} className="img" alt="Admin" />
        </div>
      </div>
    </div>
  );
};

export default Login;
