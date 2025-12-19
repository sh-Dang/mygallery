import React, { useState } from 'react';
import './Login.css'; // Import the CSS file
import { parseAst } from 'vite';

const Login = () => {
  // 이메일 입력값을 관리
  const [email, setEmail] = useState('');
  // 비밀번호 입력값을 관리
  const [password, setPassword] = useState('');

  /**
   * 로그인 폼 제출 시 호출되는 핸들러
   * axios를 통한 로그인 요청을 비동기로 전송
   */
  const handleSubmit = async (e) => {
    e.preventDefault(); // form submit 시 브라우저 새로고침 방지
    console.log('Login submitted:', { email, password }); // 입력된 이메일과 비밀번호 로깅

    try {
      /**
       * 요청의 첫번째 인자 : POST /api/users/login
       * 요청의 두번째 인자 : 요청 바디 (JSON)
       * 요청의 세번쨰 인자 : axios 설정(haeders, credentials 등)
       */
      const response = await axios.post(
        'http://localhost:8888/api/users/login',
        {
          email: email,       // 로그인에 시도할 이메일
          password: password  // 로그인에 시도할 비밀번호
        },
        {
          headers: {
            'Content-Type': 'application/json', // JSON 요청 명시
          },
          withCredentials: true, // 쿠키/세션 기반 인증 사용 시 필수
        }
      );
    } catch(error){
      /**
       * error.response가 존재하면
       * 서버가 응답했으나 상태 코드가 4xx / 5xx 인 경우
       */
      if (error.response) {
        console.error('로그인 실패:', error.response.data);
      } else {
        // 네트워크 오류, 서버 다운 등 요청 자체가 실패한 경우
        console.error('요청 오류:', error.message);
      }
    }
  };

  return (
    // form submit 시 handleSubmit 실행
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        value={email}
        // 입력값 변경 시 email 상태 갱신
        onChange={(e) => setEmail(e.target.value)}
        placeholder="email"
      />
      <input
        type="password"
        value={password}
        // 입력값 변경 시 password 상태 갱신
        onChange={(e) => setPassword(e.target.value)}
        placeholder="password"
      />
      <button type="submit">Login</button>
    </form>
  );
};

export default Login;
