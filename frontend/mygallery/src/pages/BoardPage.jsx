import React from 'react';
import axios from 'axios';
import { useEffect, useState } from 'react';

const BoardPage = () => {
  // 게시글 목록을 저장할 상태 변수
  const [boards, setBoards] = useState([]);

  // 컴포넌트가 마운트될 때 한 번만 실행
  useEffect(() => {
    // 서버로부터 게시글 목록 가져오기
    axios.get('/api/boards')
      .then(response => {
        // 성공 시 응답 데이터를 상태에 저장
        setBoards(response.data);
      })
      .catch(error => {
        // 실패 시 에러 로그 출력
        console.error('게시글 조회 실패', error);
      });
  }, []); // 빈 배열: 컴포넌트 마운트 시 한 번만 실행

return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>게시판</h1>

      {/* 게시글 목록을 표시하는 테이블 */}
      <table border="1" style={{ margin: '0 auto', width: '80%' }}>
        <thead>
          <tr>
            <th>게시물 번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회 수</th>
          </tr>
        </thead>
        <tbody>
          {/* 추후에 컨트롤러 매핑 후 주석 해제 */}
          {/* boards 배열을 순회하며 각 게시글을 테이블 행으로 렌더링 */}
          {/* {boards.map(board => (
            <tr key={board.id}>
              <td>{board.id}</td>
              <td>{board.title}</td>
              <td>{board.writer}</td>
              <td>{board.viewCount}</td>
            </tr>
          ))} */}
        </tbody>
      </table>
    </div>
  );
};

export default BoardPage;
