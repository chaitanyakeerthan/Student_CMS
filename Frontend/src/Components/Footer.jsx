import "../Components/Footer.css";

const Footer = () => {
  return (
    <footer className="site-footer">
      <div className="footer-content">
        &copy; {new Date().getFullYear()} Student HUB. All rights reserved.
      </div>
    </footer>
  );
};

export default Footer;