import React, { useState, useEffect } from 'react';
import './Signup.css'; // Import the CSS file

const Signup = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordError, setPasswordError] = useState('');

  useEffect(() => {
    if (password.length > 0 && password.length < 4) {
      setPasswordError('비밀번호는 4자 이상이어야 합니다.');
    } else if (password !== confirmPassword && confirmPassword.length > 0) {
      setPasswordError('비밀번호가 일치하지 않습니다.');
    } else {
      setPasswordError('');
    }
  }, [password, confirmPassword]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (passwordError) {
      alert(passwordError);
      return;
    }
    if (password.length < 4) {
        alert('비밀번호는 4자 이상이어야 합니다.');
        return;
    }
    if (password !== confirmPassword) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    console.log('Signup submitted:', { email, password });
    // Here you would typically send data to your backend
  };

  return (
    <div className="signup-container">
      <h2>회원가입</h2>
      <form onSubmit={handleSubmit} className="signup-form">
        <div className="signup-form-group">
          <label htmlFor="email" className="signup-label">이메일:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="signup-input"
          />
        </div>
        <div className="signup-form-group">
          <label htmlFor="password" className="signup-label">비밀번호:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="signup-input"
          />
        </div>
        <div className="signup-form-group">
          <label htmlFor="confirmPassword" className="signup-label">비밀번호 확인:</label>
          <input
            type="password"
            id="confirmPassword"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            className="signup-input"
          />
        </div>
        {passwordError && <p className="signup-error-message">{passwordError}</p>}
        <button type="submit" className="signup-button">회원가입</button>
      </form>
    </div>
  );
};

export default Signup;
