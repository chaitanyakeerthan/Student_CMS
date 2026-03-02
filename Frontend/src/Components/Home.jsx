import '../Components/Home.css';
import Icon from '../Images/icon.png';
import Side from '../Images/side.jpeg';
import { useNavigate } from 'react-router-dom';
import Footer from './Footer';

const Home = () => {
  const navigate = useNavigate();

  return (
    <>
      <header className="site-nav">
        <div className="brand">
          <img src={Icon} alt="logo" />
          <div className="name">Student HUB</div>
        </div>

        <nav className="top-links" aria-label="Primary">
          <ul>
            <li><a href="#home">🏠 Home</a></li>
            <li><a href="#about">ℹ️ About Us</a></li>
            <li><a href="#services">📚 Services</a></li>
            <li><a href="#contact">☎️ Contact Us</a></li>
          </ul>
        </nav>
      </header>
      <main>
        <section id="home" className="hero">
          <div className="hero-left">
            <h1 className="reveal">
              STUDENT 
              COURSE<br />
              MANAGEMENT<br />
              PORTAL
            </h1>

            <p className="reveal" style={{ transitionDelay: '.12s' }}>
              Our student portal goes beyond traditional course management by providing smart,
               interactive features that enhance the overall learning experience. 
               Students receive <b>instant notifications for important announcements, deadlines, and updates
               ensuring they never miss critical information. The platform supports online quizzes and assessments
                along with easy assignment submission and tracking. </b>
                With a secure system, intuitive interface, and responsive design, the portal delivers a seamless and engaging
                experience across all devices.
            </p>

            <div className="cta-row reveal" style={{ transitionDelay: '.24s' }}>
              <div className="wait-text">
               <b> What are you waiting for? Just click that button!</b>
              </div>
              <input
                type="button"
                className="btn cta-btn"
                value="Get Started"
                onClick={() => navigate('/login')}
              />
            </div>
          </div>

          <div className="hero-right reveal" style={{ transitionDelay: '.32s' }}>
            <img src={Side} alt="illustration" />
          </div>
        </section>
        <section
          id="about"
          className="about reveal"
          style={{ transitionDelay: '.08s' }}
        >
          <h2>About Us</h2>
          <p>
            We are a team of passionate developers dedicated to building the
            Student Course Management System (SCMS).
          </p>
        <div className="about-grid">
          <div className="about-card reveal" style={{ transitionDelay: '.16s' }}>
            <div className="icon-circle">👥</div>
            <h3>Community</h3>
            <p>Collaborate with peers & share resources.</p>
          </div>

          <div className="about-card reveal" style={{ transitionDelay: '.20s' }}>
            <div className="icon-circle">🎯</div>
            <h3>Focused Learning</h3>
            <p>Personalized quizzes and progress tracking.</p>
          </div>

          <div className="about-card reveal" style={{ transitionDelay: '.24s' }}>
            <div className="icon-circle">🏅</div>
            <h3>Achievement</h3>
            <p>Track your academic performance easily.</p>
          </div>
        </div>
        </section>
        <section id="services" className="services">
          <h2 className="reveal" style={{ transitionDelay: '.08s' }}>
            Our Services
          </h2>
          <p className="reveal" style={{ transitionDelay: '.12s' }}>
            SCMS offers all tools to access courses and track grades.
          </p>

          <div className="service-grid">
            <div className="service-card reveal" style={{ transitionDelay: '.18s' }}>
              <div className="icon-small">📘</div>
              <h4>Course Access</h4>
              <p>
                Secure, role-based login to access college-recommended courses,
                NPTEL modules, and personalized academic resources.
              </p>
            </div>

            <div className="service-card reveal" style={{ transitionDelay: '.22s' }}>
              <div className="icon-small">📝</div>
              <h4>Assignment Management</h4>
              <p>Submit work and view deadlines.</p>
            </div>

            <div className="service-card reveal" style={{ transitionDelay: '.30s' }}>
              <div className="icon-small">🔔</div>
              <h4>Smart Notifications</h4>
              <p>Get reminders for deadlines.</p>
            </div>
          </div>
        </section>
        <section
          id="contact"
          className="contact reveal"
          style={{ transitionDelay: '.08s' }}
        >
          <h2>Contact Us</h2>
          <p>Have questions? We're here to help.</p>

          <div className="contact-grid">
            <div className="contact-card reveal" style={{ transitionDelay: '.12s' }}>
              <div className="icon-circle">📞</div>
              <h3>Phone</h3>
              <p>+91-9876543210</p>
            </div>

            <div className="contact-card reveal" style={{ transitionDelay: '.16s' }}>
              <div className="icon-circle">✉️</div>
              <h3>Email</h3>
              <p>SCMS@college.in</p>
            </div>

            <div className="contact-card reveal" style={{ transitionDelay: '.20s' }}>
              <div className="icon-circle">📍</div>
              <h3>Address</h3>
              <p>Campus Main Building, Room 101</p>
            </div>
          </div>
        </section>
      </main>
      <Footer/>
    </>
  );
};

export default Home;
