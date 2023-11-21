import React from "react";
import FollowingCard from "../components/userbook/FollowingCard";
import styled from "styled-components";
function FollowingPage(props) {
    // 현재 URL에서 경로 추출
    const currentPath = window.location.pathname;
    // 예시: 경로에서 마지막 부분 추출 (마지막 슬래시 이후의 부분)
    const lastSegment = currentPath.substring(currentPath.lastIndexOf('/') + 1); //유저 누르면 여기 값 넣어줘서 확인

  return (
    <Container>
      <FollowingCard lastSegment={lastSegment}/>
    </Container>
  );
}

export default FollowingPage;

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 6rem;
  height: 100%;
  width: 100%;
`;
