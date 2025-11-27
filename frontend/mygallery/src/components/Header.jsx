import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css'; // Import the CSS file

const Header = () => {
  return (
    <header className="header">
      <nav>
        <ul className="nav-list">
          <li className="nav-item"><Link to="/" className="nav-link">홈</Link></li>
          <li className="nav-item"><Link to="/board" className="nav-link">게시판</Link></li>
          <li className="nav-item"><Link to="/login" className="nav-link">로그인</Link></li>
          <li className="nav-item"><Link to="/signup" className="nav-link">회원가입</Link></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
